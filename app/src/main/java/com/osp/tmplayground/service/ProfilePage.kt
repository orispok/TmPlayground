package com.osp.tmplayground.service

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.osp.tmplayground.data.Gender
import com.osp.tmplayground.data.PreferencesMatch
import com.osp.tmplayground.data.Profile

@Composable
fun ProfilePage(modifier: Modifier = Modifier, profile: Profile, onScreenChange: (String, String?)->Unit) {
    Surface(
        modifier
            .padding(top = 20.dp, bottom = 20.dp)
            .fillMaxSize()
    ) {
        Column(
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Hi ${profile.name}!")
            Image(
                painter = rememberAsyncImagePainter(model = profile.imageUrl),
                contentDescription = null,
                modifier = Modifier.size(200.dp),
                contentScale = ContentScale.Crop
            )
            Text(text = "age: ${profile.age}")
            Text(text = "description: ${profile.description}")
            Text(text = "height: ${profile.height}")
            Text(text = "gender: ${profile.gender}")
            Text(text = "\npreferencesMatch: \n   max distance: ${profile.preferencesMatch.maxDistance}")
            Text(text = "   min age: ${profile.preferencesMatch.ageMin}")
            Text(text = "   max age: ${profile.preferencesMatch.ageMax}")
            Text(text = "   date gender: ${profile.preferencesMatch.dateGender}")


            Spacer(modifier = modifier.padding(16.dp))
            ElevatedButton(onClick = { onScreenChange("welcome", null) }) {
                Text(text = "Sign Out")
            }
        }
    }
}

@Preview(widthDp = 220, heightDp = 500)
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
    ProfilePage(profile = profile, onScreenChange = {a, b -> a+b})
}