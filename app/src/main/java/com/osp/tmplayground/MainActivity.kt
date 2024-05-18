package com.osp.tmplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.osp.tmplayground.ui.theme.TmPlaygroundTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TmPlaygroundTheme {
                TmPlaygroundTheme {
                    // TODO: 1. Welcome user with connect button.
                    // TODO: 2. get the `uid` by calling `AuthService.getUid()`.
                    // TODO: 3. Then, fetch the profile using `DatabaseService.fetchProfile(uid)`.
                    // TODO: 4. get missing profile data by register screens flow.
                    // TODO: 5. Finally, show user profile.
                }
            }
        }
    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        TmPlaygroundTheme {
            Greeting("Android")
        }
    }
}