package com.pinterest.android.pdk.data

import com.pinterest.android.pdk.data.Counts
import com.pinterest.android.pdk.data.Image
import com.squareup.moshi.Json

/**
 * A data class representing a User fetched from the server. Due to the server returning
 * partial responses, any of these fields may be null depending on how the request is
 * configured. While id's can be null, I have arbitrarily enforced a constraint
 * that we always fetch it.
 */
data class User(
        // Enforcing requirement even though API doesn't
        val id: String,
        @Json(name = "first_name") val firstName: String? = null,
        @Json(name = "last_name") val lastName: String? = null,

        val username: String? = null,
        val url: String? = null,
        val bio: String? = null,
        @Json(name = "created_at") val createdAt: String? = null, // TODO: iso
        val counts: Counts? = null,
        val image: Map<String, Image>? = null
)
