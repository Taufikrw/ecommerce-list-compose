package com.taufik.ecommercelist.ui.screen.detail

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.taufik.ecommercelist.R
import com.taufik.ecommercelist.data.local.Wishlist
import com.taufik.ecommercelist.data.remote.response.ProductsItem
import com.taufik.ecommercelist.ui.ViewModelFactory
import com.taufik.ecommercelist.ui.common.State
import com.taufik.ecommercelist.ui.component.LoadingPage

@Composable
fun DetailScreen(
    id: Int,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
    navigateBack: () -> Unit,
    navigateToWishlist: () -> Unit
) {
    viewModel.uiState.collectAsState(initial = State.Loading).value.let {
        when (it) {
            is State.Loading -> {
                LoadingPage(isLoading = true)
                viewModel.getDetailProduct(id)
            }

            is State.Success -> {
                val data = it.data
                DetailContent(
                    item = data,
                    onBackClick = navigateBack,
                    toggleWishlist = {
                        val newWishlistStatus = !data.isWishlist
                        viewModel.updateWishlist(data.product, newWishlistStatus)
                        navigateToWishlist()
                    }
                )
            }

            is State.Error -> {

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailContent(
    item: Wishlist,
    onBackClick: () -> Unit,
    toggleWishlist: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = stringResource(id = R.string.back),
                tint = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier
                    .padding(16.dp)
                    .clickable { onBackClick() }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = toggleWishlist,
                elevation = FloatingActionButtonDefaults.elevation(0.dp),
                containerColor = MaterialTheme.colorScheme.primary,
            ) {
                Icon(
                    imageVector = if (item.isWishlist) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = null
                )
            }
        }
    ) {
        LazyColumn {
            item {
                Box {
                    AsyncImage(
                        model = item.product.thumbnail,
                        contentDescription = null,
                        modifier = modifier
                            .fillMaxWidth()
                            .heightIn(max = 300.dp)
                    )
                }
            }
            item {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = item.product.category,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = Color.White,
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.small)
                            .background(MaterialTheme.colorScheme.secondary)
                            .padding(
                                horizontal = 8.dp,
                                vertical = 4.dp
                            )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = item.product.title,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Light
                        ),
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Star,
                            contentDescription = null,
                            tint = Color.Yellow,
                            modifier = Modifier
                                .size(20.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.rating_tag, item.product.rating.toString()),
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.ExtraLight
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.small)
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.primary)
                            .padding(
                                horizontal = 16.dp,
                                vertical = 8.dp
                            )
                    ) {
                        Text(
                            text = stringResource(R.string.price),
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = Color.White
                        )
                        Text(
                            text = stringResource(id = R.string.price_tag, item.product.price),
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(id = R.string.desc),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = item.product.description,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.brand),
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Light
                            )
                        )
                        Spacer(modifier = Modifier.width(100.dp))
                        Text(
                            text = item.product.brand,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Light
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.stock),
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Light
                            )
                        )
                        Spacer(modifier = Modifier.width(100.dp))
                        Text(
                            text = item.product.stock.toString(),
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Light
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(id = R.string.detail_image),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    modifier = modifier
                ) {
                    items(item.product.images, key = { it }) {
                        AsyncImage(
                            model = it,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = modifier
                                .height(150.dp)
                                .shadow(
                                    elevation = 8.dp,
                                    shape = MaterialTheme.shapes.large
                                )
                        )
                    }
                }
            }
        }
    }
}