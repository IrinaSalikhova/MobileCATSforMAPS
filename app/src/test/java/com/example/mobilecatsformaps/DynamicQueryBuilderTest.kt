package com.example.mobilecatsformaps

import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.mobilecatsformaps.database.buildDynamicQuery
import org.junit.Assert.assertEquals
import org.junit.Test

class DynamicQueryBuilderTest {

    @Test
    fun `test buildDynamicQuery with no categories or search words`() {
        val result = buildDynamicQuery(emptyList(), null)
        assertEquals("SELECT * FROM assets", result.sql)
    }

    @Test
    fun `test buildDynamicQuery with categories only`() {
        val categories = listOf("Health", "Education")
        val result = buildDynamicQuery(categories)

        val expectedQuery = "SELECT * FROM assets WHERE (category LIKE '%' || ? || '%' OR category LIKE '%' || ? || '%')"
        assertEquals(expectedQuery, result.sql)
    }

    @Test
    fun `test buildDynamicQuery with search words only`() {
        val searchWords = listOf("yoga", "class")
        val result = buildDynamicQuery(emptyList(), searchWords)

        val expectedQuery = """
            SELECT * FROM assets WHERE 
            (name LIKE ? OR category LIKE ? OR address LIKE ? OR description LIKE ? OR schedule_recurrence LIKE ? OR 
            name LIKE ? OR category LIKE ? OR address LIKE ? OR description LIKE ? OR schedule_recurrence LIKE ?)
        """.trimIndent().replace("\n", "")

        val expectedArgs = listOf(
            "%yoga%", "%yoga%", "%yoga%", "%yoga%", "%yoga%",
            "%class%", "%class%", "%class%", "%class%", "%class%"
        )

        assertEquals(expectedQuery, result.sql)
    }

    @Test
    fun `test buildDynamicQuery with categories and search words`() {
        val categories = listOf("Health", "Education")
        val searchWords = listOf("yoga")
        val result = buildDynamicQuery(categories, searchWords)

        val expectedQuery = """
            SELECT * FROM assets WHERE 
            (category LIKE '%' || ? || '%' OR category LIKE '%' || ? || '%') AND 
            (name LIKE ? OR category LIKE ? OR address LIKE ? OR description LIKE ? OR schedule_recurrence LIKE ?)
        """.trimIndent().replace("\n", "")

        val expectedArgs = listOf(
            "Health", "Education",
            "%yoga%", "%yoga%", "%yoga%", "%yoga%", "%yoga%"
        )

        assertEquals(expectedQuery, result.sql)
    }
}