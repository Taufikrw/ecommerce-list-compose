package com.taufik.ecommercelist.ui.screen.wishlist

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
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
                WishlistContent(
                    productsItem = it.data,
                    modifier = modifier,
                    navigationToDetail = navigationToDetail,
                )
            }

            is State.Error -> {

            }
        }
    }
}

@Composable
fun WishlistContent(
    productsItem: List<Wishlist>,
    modifier: Modifier = Modifier,
    navigationToDetail: (Int) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .testTag("ProductList")
    ) {
        items(productsItem, key = { it.product.id }) { product ->
            ProductItem(
                title = product.product.title,
                image = product.product.thumbnail,
                desc = product.product.description,
                modifier = Modifier
                    .clickable {
                        navigationToDetail(product.product.id)
                    }
            )
        }
    }
}