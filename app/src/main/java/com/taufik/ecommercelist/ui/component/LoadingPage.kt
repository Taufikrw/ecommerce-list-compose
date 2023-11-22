package com.taufik.ecommercelist.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.taufik.ecommercelist.ui.theme.ECommerceListTheme

@Composable
fun LoadingPage(isLoading: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(50.dp)
                    .padding(8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingPagePreview() {
    ECommerceListTheme {
        LoadingPage(true)
    }
}