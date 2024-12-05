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

// Helper to build query dynamically
fun buildDynamicCategoryQuery(selectedCategories: List<String>): SupportSQLiteQuery {
    if (selectedCategories.isEmpty()) {
        return SimpleSQLiteQuery("SELECT * FROM assets") // Return all assets if no category is selected
    }
    val query = buildString {
        append("SELECT * FROM assets WHERE ")
        append(
            selectedCategories.joinToString(" OR ") {
                "category LIKE '%' || ? || '%'"
            }
        )
    }
    Log.d("DynamicQuery", query)
    return SimpleSQLiteQuery(query, selectedCategories.toTypedArray())
}