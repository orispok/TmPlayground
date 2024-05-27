package com.osp.tmplayground.service

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.osp.tmplayground.data.Gender
import com.osp.tmplayground.data.PreferencesMatch
import com.osp.tmplayground.data.Profile

@Composable
fun ProfilePage(modifier: Modifier = Modifier, profile: Profile) {
    Surface(
        modifier
            .padding(top = 20.dp, bottom = 20.dp)
            .fillMaxSize()
    ) {
        Column() {
            Text(text = "Hi ${profile.name}!")
            Text(text = "profile: ${profile.toString()}")
        }
    }
}

@Preview
@Composable
fun ProfilePagePreview() {
    val profile = Profile(
        uid = "fullProfile",
        name = "Jane Doe",
        age = 23,
        imageUrl = "https://example.com/janedoe.jpg",
        description = "Hello, I'm Jane Doe",
        height = 170,
        gender = Gender.Male,
        preferencesMatch = PreferencesMatch(
            maxDistance = 100,
            ageMin = 20,
            ageMax = 30,
        )
    )
    ProfilePage(profile = profile)
}