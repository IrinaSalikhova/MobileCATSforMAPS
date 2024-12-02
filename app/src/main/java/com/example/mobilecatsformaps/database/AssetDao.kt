package com.example.mobilecatsformaps.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
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
}