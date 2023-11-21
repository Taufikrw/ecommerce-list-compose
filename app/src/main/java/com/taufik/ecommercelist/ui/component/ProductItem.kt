package com.taufik.ecommercelist.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.taufik.ecommercelist.R
import com.taufik.ecommercelist.ui.theme.ECommerceListTheme

@Composable
fun ProductItem(
    title: String,
    image: String,
    price: Int,
    rating: Any,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
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
            modifier = Modifier.padding(
                horizontal = 12.dp,
                vertical = 16.dp
            )
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Light
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = stringResource(id = R.string.price_tag, price),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold
                )
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
                    text = stringResource(id = R.string.rating_tag, rating.toString()),
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.ExtraLight
                    )
                )
            }
        }
    }
}

@Composable
@Preview()
fun ProductItemPreview() {
    ECommerceListTheme {
        ProductItem(
            "IPhone X",
            "https://picsum.photos/300/200",
            120,
            4.8
        )
    }
}