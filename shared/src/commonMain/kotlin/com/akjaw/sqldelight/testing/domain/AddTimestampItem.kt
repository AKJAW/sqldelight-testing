package com.akjaw.sqldelight.testing.domain

import akjaw.com.sqldelight.testing.db.TableQueries
import com.akjaw.sqldelight.testing.data.time.TimestampProvider

class AddTimestampItem(
    private val tableQueries: TableQueries,
    private val timestampProvider: TimestampProvider,
) {

    fun execute() {
        tableQueries.insertItem(timestampProvider.getTimestampSeconds().toString())
    }
}