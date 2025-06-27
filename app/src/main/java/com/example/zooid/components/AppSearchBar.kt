package com.example.zooid.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zooid.R
import com.example.zooid.ui.theme.ZooIdTheme

@Composable
fun AppSearchBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Pesquisar pacotes"
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp, vertical = 12.dp)
            .shadow(elevation = 6.dp, shape = RoundedCornerShape(50))
            .background(color = Color(0xFFF9F9FF), shape = RoundedCornerShape(50))
            .height(56.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 12.dp, end = 20.dp)
        ) {
            // Ícone da corujinha à esquerda
            Box(
                modifier = Modifier
                    .size(36.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_coruja), // seu ícone da corujinha
                    contentDescription = "Coruja",
                    tint = Color.Unspecified, // deixa a cor original do ícone
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(0.dp))

            Box(
                modifier = Modifier
                    .width(1.dp)
                    .height(30.dp)
                    .background(Color(0xFF000000)) // cor da linha
            )

            Spacer(modifier = Modifier.width(0.dp))

            // TextField ocupa o máximo possível
            TextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                placeholder = {
                    Text(
                        text = placeholder,
                        color = Color(0xFF888888),
                        fontSize = 16.sp
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    cursorColor = Color(0xFF888888)
                ),
                modifier = Modifier.weight(1f),
                singleLine = true
            )

            Spacer(modifier = Modifier.width(0.dp))

            // Ícone da lupa à direita
            Box(
                modifier = Modifier
                    .size(36.dp)
                    ,
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Buscar",
                    tint = Color(0xFF000000),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppSearchBarPreview() {
    ZooIdTheme {
        AppSearchBar(
            searchQuery = "",
            onSearchQueryChange = {}
        )
    }
}
