package com.pinterest.android.pdk.data

import com.squareup.moshi.Json

data class Board(
        // Enforcing requirement even though API doesn't
        val id: String,

        val name: String? = null,
        val url: String? = null,
        val description: String? = null,
        val creator: Map<String, String>? = null,
        @Json(name = "created_at") val createdAt: String? = null,
        val counts: Counts? = null,
        val image: Map<String, Image>? = null,
        val privacy: String? = null,
        val reason: String? = null
)
