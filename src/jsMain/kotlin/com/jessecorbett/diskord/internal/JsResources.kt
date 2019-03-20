package com.jessecorbett.diskord.internal

import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.js.Js

internal actual fun websocketClient(): HttpClientEngineFactory<HttpClientEngineConfig> = Js
internal actual fun httpClient(): HttpClientEngineFactory<HttpClientEngineConfig> = Js
