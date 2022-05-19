package com.akjaw.sqldelight.testing.domain

import akjaw.com.sqldelight.testing.db.TableQueries
import com.akjaw.sqldelight.testing.data.time.TimestampProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class UpdateTimestampItem(
    private val tableQueries: TableQueries,
    private val timestampProvider: TimestampProvider,
) {

    suspend fun execute(itemId: Long) = withContext(Dispatchers.Default) {
        tableQueries.updateTimestamp(
            time = timestampProvider.getTimestampMilliseconds(),
            id = itemId
        )
    }
}