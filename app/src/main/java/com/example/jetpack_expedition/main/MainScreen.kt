package com.example.jetpack_expedition.main

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.jetpack_expedition.main.composable.currentRoute
import com.example.jetpack_expedition.main.composable.navigateSingleTopTo
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun MainScreen2(
    navController: NavHostController,
    bottomSheetNavigator: BottomSheetNavigator,
    mainViewModel: MainViewModel = hiltViewModel(),
    updateTabScreen: @Composable (Int) -> Unit,
) {
    val mainTabState = mainViewModel.mainTabState.collectAsStateWithLifecycle()
    // TODO main tab 화면일 때만 탭바 보이도록 조건 설정 필요
//    val showBars = MainRouteItem.values().map { it.route }.contains(
//        navController
//            .currentBackStackEntryAsState().value?.destination?.route
//    )

    Scaffold(
        topBar = {
            if (true)
                Text(
                    modifier = Modifier
                        .padding(top = 10.dp, bottom = 10.dp, start = 20.dp),
                    text = MainRouteItem.values()[mainTabState.value].title,
                    style = MaterialTheme.typography.h3
                )
        },
        bottomBar = {
            Log.d("navigation route", "${currentRoute(navController)}")
            if (true) {
                BottomNavigation {
                    val currentRoute = currentRoute(navController)
                    MainRouteItem.values().toList().forEachIndexed { index, screen ->
                        BottomNavigationItem(
                            icon = { Icon(screen.icon, contentDescription = null) },
                            label = { Text(screen.route) },
                            selected = currentRoute == screen.route,
                            onClick = {
                                if (currentRoute != screen.route) {
                                    mainViewModel.tapped(index)
                                }
                            },
                        )
                    }
                }
            }
        },
    ) { padding ->
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp))
                .background(MaterialTheme.colors.background)
                .padding(paddingValues = padding),
        ) {
            ModalBottomSheetLayout(bottomSheetNavigator) {
                updateTabScreen(mainTabState.value)
            }
        }
    }
}
