package com.akjaw.sqldelight.testing.domain

import akjaw.com.sqldelight.testing.db.TableQueries
import akjaw.com.sqldelight.testing.db.TimestampItemEntity
import app.cash.turbine.test
import com.akjaw.sqldelight.testing.MockTimestampProvider
import com.akjaw.sqldelight.testing.startTestKoin
import com.akjaw.sqldelight.testing.stopTestKoin
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class GetTimestampItemsTest : KoinComponent {

    private val tableQueries: TableQueries by inject()
    private val systemUnderTest: GetTimestampItems by inject()

    @BeforeTest
    fun setUp() {
        startTestKoin()
    }

    @AfterTest
    fun tearDown() {
        stopTestKoin()
    }

    @Test
    fun `Items are correctly returned`() = runTest {
        tableQueries.insertItem(1652980264000)
        tableQueries.insertItem(1652980224000)

        val result = systemUnderTest.execute().first()

        assertSoftly {
            result shouldHaveSize 2
            result[0].name shouldBe "2022-05-19T17:11:04"
            result[1].name shouldBe "2022-05-19T17:10:24"
        }
    }
}
