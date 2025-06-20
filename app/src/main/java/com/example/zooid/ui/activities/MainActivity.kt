package com.example.zooid.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.zooid.ui.theme.ZooIDTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZooIDTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Column(modifier = modifier.padding(16.dp)) {
        Text("Bem-vindo ao ZooID!")
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            context.startActivity(Intent(context, QuizActivity::class.java))
        }) {
            Text("Iniciar Quiz")
        }
    }
}
