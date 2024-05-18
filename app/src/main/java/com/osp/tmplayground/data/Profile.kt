package com.osp.tmplayground.data

data class Profile(
    val uid: String="",
    val name: String="",
    val age: Int=0,
    val imageUrl: String="",
    val description: String="",
    val height: Int=0,
    val gender: Gender?=null,
)
