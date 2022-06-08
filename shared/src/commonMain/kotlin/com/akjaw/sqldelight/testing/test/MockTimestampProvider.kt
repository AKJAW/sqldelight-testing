package com.akjaw.sqldelight.testing.test

import com.akjaw.sqldelight.testing.data.time.TimestampProvider

class MockTimestampProvider : TimestampProvider {
    private var timestampValues: MutableList<Long> = mutableListOf()
    private var lastValue: Long = 0

    fun setNextTimestamp(value: Long) {
        timestampValues.add(value)
    }

    override fun getTimestampMilliseconds(): Long {
        lastValue = timestampValues.firstOrNull() ?: lastValue
        timestampValues.removeFirstOrNull()
        return lastValue
    }
}
