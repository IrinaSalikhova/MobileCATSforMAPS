package com.example.mobilecatsformaps.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CategoryDao {
    @Insert
    suspend fun insertCategory(category: Category): Long

    @Query("SELECT * FROM categories")
    fun getAllCategories(): List<Category>

    @Query("SELECT * FROM categories WHERE parent_category_id = :parentId")
    fun getChildCategories(parentId: Long): List<Category>
}