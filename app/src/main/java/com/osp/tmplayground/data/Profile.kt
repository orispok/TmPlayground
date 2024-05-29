package com.osp.tmplayground.data

data class Profile(
    val uid: String="",
    val name: String="",
    val age: Int=0,
    val imageUrl: String="",
    val description: String="",
    val height: Int=0,
    val gender: Gender?=null,
    val preferencesMatch: PreferencesMatch = PreferencesMatch(),
)

data class PreferencesMatch (
    var maxDistance: Int? = null,
    var ageMin: Int = 0,
    var ageMax: Int = 0,
    var dateGender: Gender? = null,
)


