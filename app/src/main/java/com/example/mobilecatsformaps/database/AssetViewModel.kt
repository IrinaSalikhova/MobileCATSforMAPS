package com.example.mobilecatsformaps.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.launch

class AssetViewModel(val assetDao: AssetDao) : ViewModel() {
    fun submitAsset(asset: Asset) {
        viewModelScope.launch {
            val id = assetDao.insertAsset(asset)
        }
    }
}