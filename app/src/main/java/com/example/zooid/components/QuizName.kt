package com.example.zooid.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.ui.platform.LocalDensity

@Composable
fun QuizName(
    imageResId: Int,
    correctAnswer: String,
    onAnswerCorrect: () -> Unit,
    onNext: () -> Unit
) {
    var text by remember { mutableStateOf("") }
    var isAnswered by remember { mutableStateOf(false) }
    var isCorrect by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val animatedLabelSize by animateFloatAsState(
        targetValue = if (text.isNotEmpty() || isFocused) 12f else 16f,
        animationSpec = tween(150)
    )
    val animatedLabelOffset by animateFloatAsState(
        targetValue = if (text.isNotEmpty() || isFocused) -20f else 0f,
        animationSpec = tween(150)
    )

    val imeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0

    // Fator de escala para imagem, animado de 1f para 0.5f
    val scale by animateFloatAsState(
        targetValue = if (imeVisible) 0.5f else 1f,
        animationSpec = tween(durationMillis = 300)
    )

    // Define tamanhos base para largura e altura
    val baseWidth = 320.dp
    val baseHeight = 220.dp

    // Calcula largura e altura animadas aplicando o scale
    val animatedWidth = with(LocalDensity.current) { (baseWidth.toPx() * scale).toDp() }
    val animatedHeight = with(LocalDensity.current) { (baseHeight.toPx() * scale).toDp() }

    val scrollState = rememberScrollState()
    LaunchedEffect(Unit) {
        scrollState.scrollTo(150)
    }

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
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = "Imagem para quiz",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(animatedWidth)
                    .height(animatedHeight)
                    .clip(RoundedCornerShape(16.dp))
            )

            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .shadow(8.dp, RoundedCornerShape(12.dp))
                    .background(
                        color = when {
                            isAnswered && isCorrect -> Color(0xFF4CAF50)
                            isAnswered && !isCorrect -> Color(0xFFF44336)
                            else -> Color.White
                        },
                        shape = RoundedCornerShape(12.dp)
                    )
            ) {
                Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
                    Text(
                        text = "Nome científico",
                        color = Color.LightGray,
                        fontSize = animatedLabelSize.sp,
                        modifier = Modifier
                            .graphicsLayer { translationY = animatedLabelOffset }
                            .background(Color.Transparent)
                            .padding(horizontal = 4.dp)
                    )

                    BasicTextField(
                        value = text,
                        onValueChange = { if (!isAnswered) text = it },
                        enabled = !isAnswered,
                        textStyle = LocalTextStyle.current.copy(
                            color = Color.Black,
                            fontSize = 16.sp
                        ),
                        singleLine = true,
                        interactionSource = interactionSource,
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = {
                            if (!isAnswered) {
                                isAnswered = true
                                isCorrect = text.trim().equals(correctAnswer.trim(), ignoreCase = true)
                                focusManager.clearFocus()
                                if (isCorrect) onAnswerCorrect()
                            }
                        }),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp)
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
            if (isAnswered) {
                Text(
                    text = if (isCorrect) "Resposta correta!" else "Resposta incorreta. O correto é: $correctAnswer",
                    color = if (isCorrect) Color(0xFF4CAF50) else Color(0xFFF44336),
                    modifier = Modifier.padding(bottom = 8.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Button(
                onClick = {
                    if (!isAnswered) {
                        isAnswered = true
                        isCorrect = text.trim().equals(correctAnswer.trim(), ignoreCase = true)
                        focusManager.clearFocus()
                        if (isCorrect) onAnswerCorrect()
                    } else {
                        text = ""
                        isAnswered = false
                        onNext()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
            ) {
                Text(if (!isAnswered) "ENVIAR" else "PRÓXIMO")
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}



