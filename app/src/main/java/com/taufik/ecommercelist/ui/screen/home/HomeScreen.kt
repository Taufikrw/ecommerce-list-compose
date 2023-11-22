package com.taufik.ecommercelist.ui.screen.home

import android.annotation.SuppressLint
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.taufik.ecommercelist.data.local.Wishlist
import com.taufik.ecommercelist.ui.ViewModelFactory
import com.taufik.ecommercelist.ui.common.State
import com.taufik.ecommercelist.ui.component.CustomSearchBar
import com.taufik.ecommercelist.ui.component.LoadingPage
import com.taufik.ecommercelist.ui.component.ProductItem
import com.taufik.ecommercelist.ui.theme.ECommerceListTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
    navigationToDetail: (Int) -> Unit
) {
    viewModel.uiState.collectAsState(initial = State.Loading).value.let {
        when (it) {
            is State.Loading -> {
                LoadingPage(isLoading = true)
                LaunchedEffect(Unit) {
                    viewModel.getProducts()
                }
            }

            is State.Success -> {
                ProductContent(
                    productsItem = it.data,
                    modifier = modifier,
                    viewModel = viewModel,
                    navigationToDetail = navigationToDetail,
                )
            }

            is State.Error -> {

            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductContent(
    productsItem: List<Wishlist>,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    navigationToDetail: (Int) -> Unit
) {
    val query = viewModel.query.value

    Scaffold(
        topBar = {
            CustomSearchBar(
                query = query,
                onChangeQuery = viewModel::search
            )
        }
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(160.dp),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier
                .padding(it)
                .testTag("ProductList")
        ) {
            items(productsItem, key = { it.product.id }) { product ->
                ProductItem(
                    title = product.product.title,
                    image = product.product.thumbnail,
                    price = product.product.price,
                    rating = product.product.rating,
                    modifier = Modifier
                        .animateItemPlacement(tween(durationMillis = 100))
                        .clickable {
                            navigationToDetail(product.product.id)
                        }
                )
            }
        }
    }
}