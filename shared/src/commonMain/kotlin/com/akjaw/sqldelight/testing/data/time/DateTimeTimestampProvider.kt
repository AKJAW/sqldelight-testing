package com.akjaw.sqldelight.testing.data.time

import kotlinx.datetime.Clock

class DateTimeTimestampProvider : TimestampProvider {

    override fun getTimestampSeconds(): Long = Clock.System.now().epochSeconds
}