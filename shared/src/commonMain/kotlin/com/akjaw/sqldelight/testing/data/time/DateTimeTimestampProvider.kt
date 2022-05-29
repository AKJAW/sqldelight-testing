package com.akjaw.sqldelight.testing.data.time

import kotlinx.datetime.Clock

internal class DateTimeTimestampProvider : TimestampProvider {

    override fun getTimestampMilliseconds(): Long = Clock.System.now().toEpochMilliseconds()
}
