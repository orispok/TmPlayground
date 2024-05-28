package com.osp.tmplayground


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.osp.tmplayground.ui.theme.TmPlaygroundTheme
import com.osp.tmplayground.ui.theme.colors

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import com.osp.tmplayground.data.Profile
import com.osp.tmplayground.service.AuthService
import com.osp.tmplayground.service.ProfileInputScreen
import com.osp.tmplayground.service.ProfilePage
import com.osp.tmplayground.service.RegisterPage
import com.osp.tmplayground.service.Welcome


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TmPlaygroundTheme {
                val screen = remember { mutableStateOf("welcome") }
                val profile = remember { mutableStateOf<Profile?>(null) }
                val registerStep = remember { mutableStateOf<String?>(null) }
                // TODO: 1. Welcome user with connect button.
                // TODO: 2. get the `uid` by calling `AuthService.getUid()`.
                // TODO: 3. Then, fetch the profile using `DatabaseService.fetchProfile(uid)`.
                // TODO: 4. get missing profile data by register screens flow.
                // TODO: 5. Finally, show user profile.


                when (screen.value) { // switch screen
                    "welcome" -> Welcome(
                        onScreenChange = { screenName, step ->
                            screen.value = screenName
                            registerStep.value = step
                        },
                        onFetchedProfile = { p -> profile.value = p }
                    )

                    "Profile" -> profile.value?.let { ProfilePage(profile = it) }
                    "Register" -> profile.value?.let {
                        registerStep.value?.let { it1 ->
                            RegisterPage(
                                modifier = Modifier
                                    .padding(top = 20.dp, bottom = 20.dp)
                                    .fillMaxSize(),
                                profile = it,
                                registerStep = it1,

                                onScreenChange = { screenName, step ->
                                    screen.value = screenName
                                    registerStep.value = step
                                },
                            )
                        }
                    }

                    "ProfileInputScreen" -> profile.value?.let {
                        ProfileInputScreen(
                            modifier = Modifier
                                .padding(top = 20.dp, bottom = 20.dp)
                                .fillMaxSize(),
                            profile = it,
                            registerStep = registerStep.value,
                            onScreenChange = { screenName, step ->
                                screen.value = screenName
                                registerStep.value = step
                            },
                            onProfileChange = { newProfile -> profile.value = newProfile}
                        )
                    }

                }

            }
        }
    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        val uid = remember {
            mutableStateOf("no uid")
        }
        uid.value = AuthService.getUid()
        Text(
            text = "Hello ${uid.value}!",
            modifier = modifier,
            color = colors.purple80,

            )

    }


    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        TmPlaygroundTheme {
            Box(modifier = Modifier.fillMaxSize()) {
                Greeting("Androidddd")
            }
        }
    }
}