package com.example.mobilecatsformaps.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mobilecatsformaps.HomeScreen
import com.example.mobilecatsformaps.AddAssetScreen
import com.example.mobilecatsformaps.AssetDetailsScreen
import com.example.mobilecatsformaps.AssetListScreen
import com.example.mobilecatsformaps.LoginScreen
import com.example.mobilecatsformaps.SearchScreen
import com.example.mobilecatsformaps.database.AssetDatabase
import com.example.mobilecatsformaps.database.AssetViewModel
import com.example.mobilecatsformaps.database.CategoryViewModel


@Composable
fun AppNavigation(startDestination: String = "searchScreen", context: Context) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable("searchScreen?userId={userId}") { navBackStackEntry ->
            val userId = navBackStackEntry.arguments?.getString("userId")
            SearchScreen(navController = navController, userId = userId)
        }
        composable("assetDetailsScreen/{assetId}?userId={userId}") { navBackStackEntry ->
            val assetId = navBackStackEntry.arguments?.getString("assetId")
            val userId = navBackStackEntry.arguments?.getString("userId")
            AssetDetailsScreen(navController = navController, assetId = assetId, userId = userId)
        }
        composable("addAssetScreen?assetId={assetId}&userId={userId}") { navBackStackEntry ->
            val assetId = navBackStackEntry.arguments?.getString("assetId")
            val userId = navBackStackEntry.arguments?.getString("userId")
            val assetviewModel = AssetViewModel(assetDao = AssetDatabase.getInstance(context).assetDao())
            val categoryViewModel = CategoryViewModel(categoryDao = AssetDatabase.getInstance(context).categoryDao())
            AddAssetScreen(
                navController = navController, assetId = assetId, userId = userId,
                assetviewModel, categoryViewModel,
            )
        }
        composable("loginScreen") { LoginScreen(navController = navController) }

        composable("homeScreen/{userId}") { navBackStackEntry ->
            val userId = navBackStackEntry.arguments?.getString("userId")
            HomeScreen(navController = navController, userId = userId)
        }
        composable("assetListScreen") { AssetListScreen(navController = navController) }
    }
}










