package com.tuan.assignment.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tuan.assignment.detail.DetailScreen
import com.tuan.assignment.home.HomeScreen
import java.net.URLDecoder
import java.net.URLEncoder

const val HOME_ROUTE = "home"
const val RESTAURANT_ID_ARG = "restaurantId"
const val RESTAURANT_DETAIL = "detail/{$RESTAURANT_ID_ARG}"

@Composable
fun AssignmentNavHost(
    modifier: Modifier = Modifier.fillMaxSize(),
    startDestination: String = "home"
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        homeScreen(onRestaurantClick = {
            restaurantId -> navController.navigateToDetail(restaurantId)
        })
        detailScreen(onNavigateBack = {
            navController.popBackStack()
        })
    }
}

fun NavGraphBuilder.homeScreen(onRestaurantClick: (String) -> Unit) {
    composable(
        route = HOME_ROUTE,
        enterTransition = {
            EnterTransition.None
        },
        exitTransition = {
            ExitTransition.None
        }
    ) {
        HomeScreen(onRestaurantClick)
    }
}

fun NavGraphBuilder.detailScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    onNavigateBack: () -> Unit
) {
    composable(
        route = RESTAURANT_DETAIL,
        arguments = listOf(
            navArgument(RESTAURANT_ID_ARG) {
                type = NavType.StringType
            },
        ),
        enterTransition = {
            slideIntoContainer(
                animationSpec = tween(300, easing = EaseIn),
                towards = AnimatedContentTransitionScope.SlideDirection.Start
            )
        },
        exitTransition = {
            slideOutOfContainer(
                animationSpec = tween(600, easing = EaseOut),
                towards = AnimatedContentTransitionScope.SlideDirection.Down
            )
        }
    ) {
        DetailScreen(onNavigateBack = onNavigateBack)
    }
}

fun NavController.navigateToDetail(restaurantId: String, navOptions: NavOptions? = null) {
    val route = createDetailRoute(restaurantId)
    navigate(route, navOptions)
}

internal class RestaurantArgs(val id: String) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(URLDecoder.decode(checkNotNull(savedStateHandle[RESTAURANT_ID_ARG]), Charsets.UTF_8.name()))
}

fun createDetailRoute(id: String): String {
    val encodedId = URLEncoder.encode(id, Charsets.UTF_8.name())
    return "detail/$encodedId"
}