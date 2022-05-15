package com.akjaw.sqldelight.testing.domain

import akjaw.com.sqldelight.testing.db.TableQueries
import com.akjaw.sqldelight.testing.MockTimestampProvider
import com.akjaw.sqldelight.testing.coreModule
import com.akjaw.sqldelight.testing.data.database.createInMemorySqlDriver
import com.akjaw.sqldelight.testing.testModule
import com.squareup.sqldelight.db.SqlDriver
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import io.kotest.matchers.shouldBe
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class AddTimestampItemTest : KoinComponent {

    private val taskQueries: TableQueries by inject()
    private val mockTimestampProvider: MockTimestampProvider by inject()
    private val systemUnderTest: AddTimestampItem by inject()

    @BeforeTest
    fun setUp() {
        startKoin {
            modules(
                coreModule,
                testModule,
            )
        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `The added item uses the current timestamp as the name`() {
        mockTimestampProvider.timestamp = 123

        systemUnderTest.execute()

        val result = taskQueries.selectAll().executeAsList().first()
        result.name shouldBe "123"
    }
}