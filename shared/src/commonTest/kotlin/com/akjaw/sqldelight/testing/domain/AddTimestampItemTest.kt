package com.akjaw.sqldelight.testing.domain

import akjaw.com.sqldelight.testing.db.TableQueries
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

class AddTimestampItemTest : KoinComponent {

    private val tableQueries: TableQueries by inject()
    private val mockTimestampProvider: MockTimestampProvider by inject()
    private val systemUnderTest: AddTimestampItem by inject()

    @BeforeTest
    fun setUp() {
        startTestKoin()
    }

    @AfterTest
    fun tearDown() {
        stopTestKoin()
    }

    @Test
    fun `Added item uses the current timestamp as the name`() = runTest {
        mockTimestampProvider.timestamp = 123

        systemUnderTest.execute()

        val result = tableQueries.selectAll().executeAsList().first()
        result.time shouldBe 123
    }

    @Test
    fun `Added item has a version equal to 1`() = runTest {
        systemUnderTest.execute()

        val result = tableQueries.selectAll().executeAsList().first()
        result.version shouldBe 1
    }
}
