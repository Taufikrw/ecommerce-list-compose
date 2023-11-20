package com.taufik.ecommercelist.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.taufik.ecommercelist.R
import com.taufik.ecommercelist.data.remote.response.ProductsItem
import com.taufik.ecommercelist.ui.ViewModelFactory
import com.taufik.ecommercelist.ui.common.State
import com.taufik.ecommercelist.ui.theme.ECommerceListTheme
import retrofit2.http.Query

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

@Composable
fun ProductContent(
    productsItem: List<ProductsItem>,
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
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier
                .testTag("ProductList")
        ) {
            items(productsItem, key = { it.id }) { product ->
                ProductItem(
                    title = product.title,
                    image = product.thumbnail,
                    desc = product.description,
                    modifier = Modifier.clickable {
                        navigationToDetail(product.id)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSearchBar(
    query: String,
    onChangeQuery: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    SearchBar(
        query = query,
        onQueryChange = onChangeQuery,
        onSearch = {},
        active = false,
        onActiveChange = {},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        placeholder = {
            Text(text = stringResource(id = R.string.search_product))
        },
        shape = MaterialTheme.shapes.large,
        modifier = modifier
            .padding(
                start = 16.dp,
                bottom = 16.dp,
                end = 16.dp
            )
            .fillMaxWidth()
            .heightIn(min = 48.dp)
    ) {

    }
}

@Composable
fun ProductItem(
    title: String,
    image: String,
    desc: String,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        shape = MaterialTheme.shapes.large
    ) {
        AsyncImage(
            model = image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(MaterialTheme.shapes.large)
                .fillMaxWidth()
                .aspectRatio(3f / 2f)
        )
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = desc,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
@Preview()
fun ProductItemPreview() {
    ECommerceListTheme {
        ProductItem(
            "IPhone X",
            "https://i.dummyjson.com/data/products/1/thumbnail.jpg",
            "An apple mobile which is nothing like apple"
        )
    }
}