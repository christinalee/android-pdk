package com.pinterest.android.pdk.base

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.io.IOException
import java.lang.reflect.Type

/*
 * Copyright (C) 2017 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

internal object Unwrap {

    class EnvelopeJsonAdapter private constructor(private val delegate: JsonAdapter<Envelope<*>>) : JsonAdapter<Any>() {

        @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
        @JsonQualifier
        annotation class Enveloped

        private class Envelope<out T> internal constructor(internal val data: T)

        @Throws(IOException::class)
        override fun fromJson(reader: JsonReader): Any? {
            return delegate.fromJson(reader)!!.data
        }

        @Throws(IOException::class)
        override fun toJson(writer: JsonWriter, value: Any?) {
            delegate.toJson(writer, Envelope(value))
        }

        companion object {
            val FACTORY: Factory = object : Factory {

                override fun create(
                        type: Type, annotations: MutableSet<out kotlin.Annotation>, moshi: Moshi): JsonAdapter<*>? {
                    val delegateAnnotations = Types.nextAnnotations(annotations, Enveloped::class.java) ?: return null
                    val envelope = Types.newParameterizedTypeWithOwner(EnvelopeJsonAdapter::class.java, Envelope::class.java, type)
                    val delegate = moshi.nextAdapter<Envelope<*>>(this, envelope, delegateAnnotations)
                    return EnvelopeJsonAdapter(delegate)
                }
            }
        }
    }
}
