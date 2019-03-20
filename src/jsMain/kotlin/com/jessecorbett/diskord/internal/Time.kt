package com.jessecorbett.diskord.internal

import kotlin.js.Date

internal actual fun epochSecondNow(): Long = epochMillisNow() / 1000

internal actual fun epochMillisNow(): Long = Date.now().toLong()

internal actual fun parseRfc1123Seconds(timestamp: String): Long = Date.parse(timestamp).toLong() / 1000
