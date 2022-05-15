package com.akjaw.sqldelight.testing.domain

import akjaw.com.sqldelight.testing.db.TableQueries
import akjaw.com.sqldelight.testing.db.TimestampItemEntity
import com.akjaw.sqldelight.testing.MockTimestampProvider
import com.akjaw.sqldelight.testing.startTestKoin
import com.akjaw.sqldelight.testing.stopTestKoin
import io.kotest.matchers.shouldBe
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
    fun `Updated task name is equal to the current timestamp`() {
        val insertedItem = insertItem("0")
        mockTimestampProvider.timestamp = 456

        systemUnderTest.execute(insertedItem.id)

        val result = tableQueries.selectAll().executeAsList().first()
        result.name shouldBe "456"
    }

    @Test
    fun `Updated task version increases`() {
        val insertedItem = insertItem("0")
        mockTimestampProvider.timestamp = 456

        systemUnderTest.execute(insertedItem.id)

        val result = tableQueries.selectAll().executeAsList().first()
        result.version shouldBe 2
    }

    private fun insertItem(name: String): TimestampItemEntity {
        tableQueries.insertItem(name)
        return tableQueries.getItemWithName(name)
    }

    private fun TableQueries.getItemWithName(name: String): TimestampItemEntity =
        selectAll().executeAsList().first { it.name == name }
}
