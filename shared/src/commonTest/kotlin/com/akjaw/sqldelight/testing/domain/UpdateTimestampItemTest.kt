package com.akjaw.sqldelight.testing.domain

import akjaw.com.sqldelight.testing.db.TableQueries
import akjaw.com.sqldelight.testing.db.TimestampItemEntity
import com.akjaw.sqldelight.testing.test.MockTimestampProvider
import com.akjaw.sqldelight.testing.test.startTestKoin
import com.akjaw.sqldelight.testing.test.stopTestKoin
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class UpdateTimestampItemTest : KoinComponent {

    private val tableQueries: TableQueries by inject()
    private val mockTimestampProvider: MockTimestampProvider by inject()
    private val systemUnderTest: UpdateTimestampItem by inject()

    @BeforeTest
    fun setUp() {
        startTestKoin()
    }

    @AfterTest
    fun tearDown() {
        stopTestKoin()
    }

    @Test
    fun `Updated task name is equal to the current timestamp`() = runTest {
        val insertedItem = insertItem(0)
        mockTimestampProvider.timestamp = 456

        systemUnderTest.execute(insertedItem.id)

        val result = tableQueries.selectAll().executeAsList().first()
        result.time shouldBe 456
    }

    @Test
    fun `Updated task version increases`() = runTest {
        val insertedItem = insertItem(0)
        mockTimestampProvider.timestamp = 456

        systemUnderTest.execute(insertedItem.id)

        val result = tableQueries.selectAll().executeAsList().first()
        result.version shouldBe 2
    }

    private fun insertItem(time: Long): TimestampItemEntity {
        tableQueries.insertItem(time)
        return tableQueries.getItemWithName(time)
    }

    private fun TableQueries.getItemWithName(time: Long): TimestampItemEntity =
        selectAll().executeAsList().first { it.time == time }
}
