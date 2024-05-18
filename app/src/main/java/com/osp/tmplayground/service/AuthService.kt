package com.osp.tmplayground.service

import kotlinx.coroutines.delay

object AuthService {
    fun getUid(): String {
        return uIds.random()
    }

    private val uIds = DatabaseService.profiles.map { it.uid }

}