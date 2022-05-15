package com.akjaw.sqldelight.testing.data.time

import kotlinx.datetime.Clock

internal class DateTimeTimestampProvider : TimestampProvider {

    override fun getTimestampSeconds(): Long = Clock.System.now().epochSeconds
}