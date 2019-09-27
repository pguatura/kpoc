package com.pguatura.kpoc.api

import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.defaultSerializer
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.DEFAULT
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.Url
import io.ktor.http.contentType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JSON


expect fun platformName(): String

fun createApplicationScreenMessage() : String {
    return "Kotlin Rocks bla on ${platformName()}"
}

internal expect val ApplicationDispatcher: CoroutineDispatcher

expect fun encapsulate(function: () -> Unit)

class ApplicationApi {
    private val client = HttpClient{
        install(JsonFeature){
            serializer = KotlinxSerializer(JSON.nonstrict).apply {
                setMapper(Teste::class, Teste.serializer())
            }
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.HEADERS
        }
    }

    var address = Url("https://tools.ietf.org/rfc/rfc1866.txt")

    fun about(callback: (String) -> Unit) {
        GlobalScope.apply {
            launch(ApplicationDispatcher) {
                val result: String = client.get {
                    url(this@ApplicationApi.address.toString())
                }

                encapsulate {
                    callback(result)
                }
            }
        }

    }

    fun jobs(callback: (String) -> Unit) {
        GlobalScope.apply {
            launch(ApplicationDispatcher) {
                try{

                    val result: String = client.post {
                        url("https://api.graphql.jobs")
                        contentType(ContentType.Application.Json)
                        body = Teste("{\n  \n  jobs{\n    id,\n    title\n  }\n  \n}")
                    }

                    encapsulate {
                        callback(result)
                    }
                }catch(e: Exception){
                    encapsulate {
                        callback(e.toString())
                    }
                }
            }
        }

    }
}

@Serializable
data class Teste(val query: String)
