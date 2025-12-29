package com.app.jikanapi.presentation.app

import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.app.jikanapi.domain.routes
import com.app.jikanapi.presentation.animdetail.AnimDetailScreen
import com.app.jikanapi.presentation.main.MainScreen
import com.app.jikanapi.presentation.ui.theme.JikanApiTheme


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun App() {

    val navController = rememberNavController()

    JikanApiTheme() {

        SharedTransitionLayout() {
            NavHost(navController = navController, startDestination = routes.MAIN_SCREEN) {
                composable<routes.MAIN_SCREEN> { backStackEntry ->
                    MainScreen(
                        navController,
                        animatedVisibilityScope = this@composable
                    )
                }

                composable(
                    routes.ANIM_DETAIL_SCREEN.route,
                    arguments = listOf(
                        navArgument("animId") { type = NavType.StringType })
                ) { backStackEntry ->
                    BackHandler {
                        navController.popBackStack()
                    }

                    backStackEntry.arguments?.let {
                        val id = backStackEntry.arguments?.getString("animId")
//                        val index = backStackEntry.arguments?.getString("Index") ?: ""
//                        val data =
//                            Gson().fromJson(dataJson, AnimDataDTO::class.java) // Decode recipe JSON

                        id?.let {
                            AnimDetailScreen(
                                navController,
                                animatedVisibilityScope = this@composable,
                                id
                            )
                        }

                    }

                }

            }

        }

    }

}