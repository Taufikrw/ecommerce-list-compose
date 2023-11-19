package com.taufik.ecommercelist.ui.screen.profile

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.taufik.ecommercelist.ui.ViewModelFactory
import com.taufik.ecommercelist.ui.common.State
import com.taufik.ecommercelist.ui.screen.detail.DetailContent

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    )
) {
    viewModel.uiState.collectAsState(initial = State.Loading).value.let {
        when (it) {
            is State.Loading -> {
                LaunchedEffect(Unit) {
                    viewModel.getProfile()
                }
            }

            is State.Success -> {
                val data = it.data
                ProfileContent(
                    photo = data.photo,
                    name = data.name,
                    email = data.email
                )
            }

            is State.Error -> {

            }
        }
    }
}

@Composable
fun ProfileContent(
    @DrawableRes photo: Int,
    name: String,
    email: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Image(
            painter = painterResource(id = photo),
            contentDescription = null,
            modifier = Modifier.padding(16.dp)
        )
        Text(text = name)
        Text(text = email)
    }
}