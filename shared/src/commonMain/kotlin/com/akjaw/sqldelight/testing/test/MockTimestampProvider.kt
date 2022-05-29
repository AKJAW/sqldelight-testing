package com.akjaw.sqldelight.testing.test

import com.akjaw.sqldelight.testing.data.time.TimestampProvider

class MockTimestampProvider : TimestampProvider {
    var timestamp: Long = 0

    override fun getTimestampMilliseconds(): Long = timestamp
}
