package com.osp.tmplayground.service

import com.osp.tmplayground.data.Gender
import com.osp.tmplayground.data.PreferencesMatch
import com.osp.tmplayground.data.Profile
import kotlinx.coroutines.delay

const val DELAY = 1000L

object DatabaseService {
    suspend fun fetchProfile(uid: String): Profile {
        delay(DELAY)
        return profiles.find { it.uid == uid }!!
    }

    val profiles: List<Profile> = listOf(
        Profile(
            uid = "onlyName",
//            name = "Renana Rimon",
        ),
        Profile(
            uid = "fullProfile",
            name = "Jane Doe",
            age = 23,
            imageUrl = "https://example.com/janedoe.jpg",
            description = "Hello, I'm Jane Doe",
            height = 170,
//            gender = Gender.Male,
//            preferencesMatch = PreferencesMatch(
//                maxDistance = 100,
//                ageMin = 20,
//                ageMax = 30,
//                dateGender = Gender.Female
//            ),
        )
    )

}