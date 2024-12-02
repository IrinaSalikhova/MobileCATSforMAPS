package com.example.mobilecatsformaps.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Asset::class, Category::class], version = 1, exportSchema = false)
abstract class AssetDatabase : RoomDatabase() {
    abstract fun assetDao(): AssetDao
    abstract fun categoryDao(): CategoryDao

    companion object {
        const val DATABASE_NAME = "asset_database"

        @Volatile
        private var INSTANCE: AssetDatabase? = null

        fun getInstance(context: Context): AssetDatabase {
            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AssetDatabase::class.java,
                    DATABASE_NAME
                )
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // Pre-populate the database with sample assets
                            val sampleAssets = SampleAssets()
                            CoroutineScope(Dispatchers.IO).launch {
                                val assetDao = INSTANCE?.assetDao()
                                for (asset in sampleAssets.getSampleAssets()) {
                                    assetDao?.insertAsset(asset)
                                    Log.d("AssetDatabase", "Pre-populated asset: ${asset.name}")
                                }                               
                            }
                            // Pre-populate the database with categories
                            CoroutineScope(Dispatchers.IO).launch {
                                val categoryDao = INSTANCE?.categoryDao()
                                val categorySeeder = CategorySeeder(categoryDao!!)
                                categorySeeder.seedCategories()
                            }

                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}


