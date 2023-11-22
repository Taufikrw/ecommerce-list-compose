package com.taufik.ecommercelist.ui.screen.wishlist

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.taufik.ecommercelist.R
import com.taufik.ecommercelist.data.local.Wishlist
import com.taufik.ecommercelist.ui.ViewModelFactory
import com.taufik.ecommercelist.ui.common.State
import com.taufik.ecommercelist.ui.component.CustomSearchBar
import com.taufik.ecommercelist.ui.component.ProductItem
import com.taufik.ecommercelist.ui.screen.home.HomeViewModel
import com.taufik.ecommercelist.ui.screen.home.ProductContent

@Composable
fun WishlistScreen(
    modifier: Modifier = Modifier,
    viewModel: WishlistViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
    navigationToDetail: (Int) -> Unit
) {
    viewModel.uiState.collectAsState(initial = State.Loading).value.let {
        when (it) {
            is State.Loading -> {
                LaunchedEffect(Unit) {
                    viewModel.getWishlistProduct()
                }
            }

            is State.Success -> {
                if (it.data.isEmpty()) {
                    EmptyWishlist()
                } else {
                    WishlistContent(
                        productsItem = it.data,
                        modifier = modifier,
                        navigationToDetail = navigationToDetail,
                    )
                }
            }

            is State.Error -> {

            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WishlistContent(
    productsItem: List<Wishlist>,
    modifier: Modifier = Modifier,
    navigationToDetail: (Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(160.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .testTag("Wishlist")
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

@Composable
fun EmptyWishlist(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = R.string.empty_wishlist)
        )
    }
}