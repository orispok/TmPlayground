package com.osp.tmplayground.service

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.layout.size

import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.osp.tmplayground.data.Profile
import kotlinx.coroutines.coroutineScope

import kotlinx.coroutines.launch


@Composable
fun Welcome(
    modifier: Modifier = Modifier,
    onScreenChange: (String, String?) -> Unit,
    onFetchedProfile: (Profile) -> Unit
) {
//    val uid = remember { mutableStateOf("no uid") }
    val profile = remember { mutableStateOf<Profile?>(null) }
    val registerStep = remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    Surface(
        modifier
            .padding(top = 20.dp, bottom = 20.dp)
            .fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "TrueMatch")
            ElevatedButton(
                onClick = {
                    coroutineScope.launch {
//                        uid.value = AuthService.getUid()
                        profile.value = DatabaseService.fetchProfile(AuthService.getUid())
                        onFetchedProfile(profile.value!!)
                        registerStep.value = getFirstEmptyField(profile.value!!)
                        if (registerStep.value == null) { // full profile
                            onScreenChange("Profile", null)
                        } else {
                            onScreenChange("Register", registerStep.value)
                        }
                    }
                },
                modifier = modifier.size(120.dp, 45.dp)
            ) {
                Text(text = "Connect")
            }

        }
    }
}



fun getFirstEmptyField(profile: Profile): String? {
    return when {
        profile.uid.isBlank() -> "uid"
        profile.name.isBlank() -> "name"
        profile.age == 0 -> "age"
        profile.imageUrl.isBlank() -> "imageUrl"
        profile.description.isBlank() -> "description"
        profile.height == 0 -> "height"
        profile.gender == null -> "gender"
        profile.preferencesMatch.maxDistance == null -> "preferencesMatch"
        profile.preferencesMatch.ageMin == 0 -> "preferencesMatch"
        profile.preferencesMatch.ageMax == 0 -> "preferencesMatch"
        profile.preferencesMatch.dateGender == null -> "preferencesMatch"
        else -> null // All fields have values
    }
}


@Preview(widthDp = 220, heightDp = 420)
@Composable
fun WelcomePreview() {
//    Welcome(onChange = {}, onFetchedProfile = {})
}