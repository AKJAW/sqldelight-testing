package com.akjaw.sqldelight.testing.domain

import akjaw.com.sqldelight.testing.db.TableQueries
import com.akjaw.sqldelight.testing.data.time.TimestampProvider

class UpdateTimestampItem(
    private val tableQueries: TableQueries,
    private val timestampProvider: TimestampProvider,
) {

    fun execute(itemId: Long) {
        tableQueries.updateTimestamp(
            name = timestampProvider.getTimestampSeconds().toString(),
            id = itemId
        )
    }
}