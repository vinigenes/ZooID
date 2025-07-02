package com.example.zooid.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.zooid.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar() {
    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo App",
                    modifier = Modifier.size(150.dp),
                    contentScale = ContentScale.Fit
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Black
        ),
        navigationIcon = {},
        actions = {}
    )
}
