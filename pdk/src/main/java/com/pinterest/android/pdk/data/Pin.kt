package com.pinterest.android.pdk.data

import com.squareup.moshi.Json

/**
 * A data class representing a Pin. Due to the server returning partial responses, any
 * of these fields may be null depending on how the request is configured. While id's can be
 * null, I have arbitrarily enforced a constraint that we always fetch it.
 */
data class Pin(
        // Enforcing requirement even though the API doesn't
        val id: String,

        val link: String? = null,
        @Json(name = "original_link") val originalLink: String? = null,
        val url: String? = null,
        val creator: User? = null, // TODO
        val board: Board? = null,
        @Json(name = "created_at") val createdAt: String? = null, //TODO iso
        val note: String? = null,
        val color: String? = null, // TODO: hex
        val counts: Counts? = null, //TODO
        val media: Map<String, String>? = null, //TODO
        val attribution: Map<String, String>? = null, //TODO
        val image: Map<String, Image>? = null, //TODO
        val metadata: Map<String, Any>? = null //TODO
)
