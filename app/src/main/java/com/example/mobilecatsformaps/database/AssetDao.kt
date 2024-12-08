package com.example.mobilecatsformaps.database

import android.util.Log
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import androidx.room.RawQuery
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import kotlinx.coroutines.flow.Flow

@Dao
interface AssetDao {
    @Insert
    suspend fun insertAsset(asset: Asset)

    @Update
    suspend fun updateAsset(asset: Asset)

    @Delete
    suspend fun deleteAsset(asset: Asset)

    @Query("SELECT * FROM assets WHERE approval_status = 1")
    fun getApprovedAssets(): Flow<List<Asset>>

    @Query("SELECT * FROM assets WHERE category LIKE :tag")
    fun getAssetsByCategory(tag: String): Flow<List<Asset>>

    @Query("SELECT * FROM assets WHERE address LIKE :address")
    fun getAssetsByLocation(address: String): Flow<List<Asset>>

    @Query("SELECT * FROM assets")
    fun getAllAssets(): List<Asset>

    @Query("SELECT * FROM assets WHERE " +
            "category LIKE '%' || :category || '%'")
    fun getAssetsBySingleCategory(category: String): Flow<List<Asset>>

    @RawQuery(observedEntities = [Asset::class])
    fun getAssetsByDynamicQuery(query: SupportSQLiteQuery): Flow<List<Asset>>


}


fun buildDynamicQuery(selectedCategories: List<String>, searchWords: List<String>? = null): SupportSQLiteQuery {
    if (selectedCategories.isEmpty() && searchWords.isNullOrEmpty()) {
        return SimpleSQLiteQuery("SELECT * FROM assets")
    }
    val args = mutableListOf<Any>()
    val query = buildString {
        append("SELECT * FROM assets WHERE ")
        Log.d("DynamicQuery", selectedCategories.toString())
        if (selectedCategories.isNotEmpty()) {
            append("(")
            append(
                selectedCategories.joinToString(" OR ") {
                "category LIKE '%' || ? || '%'"
                }
            )
            append(")")
            args.addAll(selectedCategories)
        }

        if (!searchWords.isNullOrEmpty()) {
            if (selectedCategories.isNotEmpty()) {
                append(" AND ")
            }
            val likeClauses = searchWords.flatMap { word ->
                listOf(
                    "name LIKE ?",
                    "category LIKE ?",
                    "address LIKE ?",
                    "description LIKE ?",
                    "schedule_recurrence LIKE ?"
                )
            }.joinToString(" OR ")
            append("(" + likeClauses + ")")

            args.addAll(searchWords.flatMap { word ->
                List(5) { "%$word%" }
            })
        }
    }
    Log.d("DynamicQuery", query)
    Log.d("DynamicQuery", args.toString())
    return SimpleSQLiteQuery(query, args.toTypedArray())
}
