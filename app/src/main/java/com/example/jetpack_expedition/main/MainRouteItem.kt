package com.example.jetpack_expedition.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

enum class MainRouteItem(val route: String, val icon: ImageVector, val title: String) {
    Recent("recent", Icons.Default.List, "최근기록"),
    Record("record", Icons.Default.Call, "녹음"),
    Settings("settings", Icons.Default.Settings, "설정"),
    ;
}

enum class MainRoutePushItem(val route: String) {
    AdditionalFunctionsPage("additionalFunctionsPage"),
    ContactBottomSheet("contactBottomSheet")
}

//enum class MainTabState(val screen: @Composable () -> Unit) {
//    Recent(screen = {
//        .RecentScreen(
//            pullToRefreshUIViewModel = androidx.hilt.navigation.compose.hiltViewModel(parentEntry),
//            onBottomSheetCall = {
//                navController.navigate(com.example.jetpack_expedition.main.MainRoutePushItem.ContactBottomSheet.route)
//            }
//        )
//    })
//}
