package com.taufik.ecommercelist.ui.screen.home

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.taufik.ecommercelist.data.local.Wishlist
import com.taufik.ecommercelist.ui.ViewModelFactory
import com.taufik.ecommercelist.ui.common.State
import com.taufik.ecommercelist.ui.component.CustomSearchBar
import com.taufik.ecommercelist.ui.component.ProductItem

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

    Column {
        CustomSearchBar(
            query = query,
            onChangeQuery = viewModel::search
        )
        LazyVerticalGrid(
            columns = GridCells.Adaptive(160.dp),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier
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