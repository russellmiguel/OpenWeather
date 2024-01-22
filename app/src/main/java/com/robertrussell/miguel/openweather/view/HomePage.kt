package com.robertrussell.miguel.openweather.view

import android.location.Location
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.robertrussell.miguel.openweather.model.TabPages
import com.robertrussell.miguel.openweather.view.navigation.Navigation
import com.robertrussell.miguel.openweather.view.navigation.Pages
import com.robertrussell.miguel.openweather.viewmodel.OpenWeatherViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomePage(owViewModel: OpenWeatherViewModel) {

    val tabItems = listOf(TabPages.CurrentInfo, TabPages.HistoryInfo)
    val pagerState = rememberPagerState(initialPage = 0)

    Column(modifier = Modifier.fillMaxSize()) {
        Tabs(tabList = tabItems, pagerState = pagerState)
        TabPages(
            owViewModel = owViewModel,
            tabList = tabItems,
            pagerState = pagerState,
        )
    }

    BackHandler {
        Navigation.navigateTo(Pages.SignInScreen)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tabs(tabList: List<TabPages>, pagerState: PagerState) {
    val scope = rememberCoroutineScope()

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = MaterialTheme.colors.primary,
        indicator = { tabPosition ->
            Modifier.pagerTabIndicatorOffset(pagerState = pagerState, tabPositions = tabPosition)
        }) {
        tabList.forEachIndexed { index, tabPages ->
            LeadingIconTab(
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                text = { Text(text = tabPages.title) },
                icon = { Icon(imageVector = tabPages.icons, contentDescription = null) },
                selectedContentColor = Color.LightGray,
                unselectedContentColor = Color.White
            )
        }

    }
}

@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
@Composable
fun TabPages(
    owViewModel: OpenWeatherViewModel,
    tabList: List<TabPages>,
    pagerState: PagerState,
) {
    HorizontalPager(
        count = tabList.size,
        state = pagerState,
    ) { page ->
        /**
         * Temporary solution!
         * Composable item not working as intended, will find solution
         */
        if (tabList[page].title == "Current Information") {
            CurrentInfoPage(owViewModel)
        } else {
            HistoryInfoPage(owViewModel)
        }
    }
}

@Preview
@Composable
fun HomePagePreview() {
    val owViewModel: OpenWeatherViewModel = viewModel()
    HomePage(owViewModel)
}