package com.example.mobilecatsformaps.database

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryViewModel(val categoryDao: CategoryDao) : ViewModel() {
    val _categories = mutableStateListOf<Category>()
    val categories: List<Category> get() = _categories

    fun fetchCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val fetchedCategories = categoryDao.getAllCategories()
                _categories.clear()
                _categories.addAll(fetchedCategories)
            } catch (e: Exception) {
                Log.e("CategoryViewModel", "Error fetching categories", e)
            }
        }
    }

    fun fetchSubcategories(parentId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val fetchedSubcategories = categoryDao.getChildCategories(parentId)
                _categories.clear()
                _categories.addAll(fetchedSubcategories)
                // Handle the fetched subcategories as needed
            } catch (e: Exception) {
                Log.e("CategoryViewModel", "Error fetching subcategories", e)
            }
        }
    }
}