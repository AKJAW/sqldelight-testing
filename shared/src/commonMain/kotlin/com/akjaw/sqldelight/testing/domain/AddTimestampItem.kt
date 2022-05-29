package com.akjaw.sqldelight.testing.domain

import akjaw.com.sqldelight.testing.db.TableQueries
import com.akjaw.sqldelight.testing.data.time.TimestampProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class AddTimestampItem(
    private val tableQueries: TableQueries,
    private val timestampProvider: TimestampProvider,
) {

    suspend fun execute() = withContext(Dispatchers.Default) {
        tableQueries.insertItem(timestampProvider.getTimestampMilliseconds())
    }
}
