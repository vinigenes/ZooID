package com.example.zooid.quiz

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding

@Composable
fun QuizMultiple(
    imageResId: Int,
    options: List<String>,
    correctIndex: Int,
    onAnswerCorrect: () -> Unit,
    onNext: () -> Unit
) {
    var selectedIndex by remember { mutableStateOf<Int?>(null) }
    var isAnswered by remember { mutableStateOf(false) }
    // Verifica se o índice selecionado corresponde ao índice correto
    val isCorrect = selectedIndex == correctIndex

    val imeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    val scale by animateFloatAsState(
        targetValue = if (imeVisible) 0.5f else 1f,
        animationSpec = tween(300)
    )

    val baseWidth = 320.dp
    val baseHeight = 220.dp
    val animatedWidth = with(LocalDensity.current) { (baseWidth.toPx() * scale).toDp() }
    val animatedHeight = with(LocalDensity.current) { (baseHeight.toPx() * scale).toDp() }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Imagem da pergunta
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = "Imagem relacionada à pergunta",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(animatedWidth)
                    .height(animatedHeight)
                    .clip(RoundedCornerShape(16.dp))
            )

            Spacer(modifier = Modifier.height(24.dp))


            // Lista de opções
            options.forEachIndexed { index, option ->
                val isSelected = selectedIndex == index
                val optionLetter = ('a' + index) + ")"

                val backgroundColor = when {
                    isAnswered && index == correctIndex -> Color(0xFF4CAF50) // Verde para correta
                    isAnswered && isSelected && !isCorrect -> Color(0xFFF44336) // Vermelho para erro
                    isSelected -> Color(0xFFE0E0E0) // Cinza claro para seleção
                    else -> Color.White
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(vertical = 4.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(backgroundColor)
                        .clickable(enabled = !isAnswered) {
                            selectedIndex = index
                        }
                        .padding(12.dp)
                ) {
                    Text(
                        text = "$optionLetter $option",
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isAnswered && selectedIndex != null) {
                Text(
                    text = if (isCorrect)
                        "Resposta correta!"
                    else
                        "Resposta incorreta. A correta é: ${('a' + correctIndex)}) ${options.getOrNull(correctIndex) ?: "N/A"}",
                    color = if (isCorrect) Color(0xFF4CAF50) else Color(0xFFF44336),
                    modifier = Modifier.padding(bottom = 8.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Button(
                onClick = {
                    if (!isAnswered && selectedIndex != null) {
                        if (isCorrect) onAnswerCorrect()
                        isAnswered = true
                    } else if (isAnswered) {
                        selectedIndex = null
                        isAnswered = false
                        onNext()
                    }
                },
                enabled = selectedIndex != null,
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text(if (!isAnswered) "ENVIAR" else "PRÓXIMO")
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
