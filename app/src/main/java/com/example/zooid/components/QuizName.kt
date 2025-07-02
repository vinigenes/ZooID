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

    // Animações suaves
    val animatedLabelSize by animateFloatAsState(
        targetValue = if (text.isNotEmpty() || isFocused) 12f else 16f,
        animationSpec = tween(150)
    )
    val animatedLabelOffset by animateFloatAsState(
        targetValue = if (text.isNotEmpty() || isFocused) -20f else 0f,
        animationSpec = tween(150)
    )

    Box(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        // Conteúdo principal com scroll (imagem + input)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .fillMaxSize()
                .navigationBarsPadding()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = "Imagem para quiz",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(16.dp))
            )

            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .shadow(8.dp, RoundedCornerShape(12.dp))
                    .background(
                        color = when {
                            isAnswered && isCorrect -> Color(0xFF4CAF50)
                            isAnswered && !isCorrect -> Color(0xFFF44336)
                            else -> Color(0xFFFFFFFF)
                        },
                        shape = RoundedCornerShape(12.dp)
                    )
            ) {
                Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
                    // Label flutuante
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
                        onValueChange = { if (!isAnswered) text = it },  // evita alterar após responder
                        enabled = !isAnswered,  // desabilita o campo depois de enviar
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

        // Texto de feedback e botão fixos na parte inferior, sobem com teclado
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .imePadding()
                .padding(horizontal = 8.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isAnswered) {
                Text(
                    text = if (isCorrect) "Resposta correta!"
                    else "Resposta incorreta. O correto é: $correctAnswer",
                    color = if (isCorrect) Color(0xFF4CAF50) else Color(0xFFF44336),
                    modifier = Modifier.padding(bottom = 8.dp)
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
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text(if (!isAnswered) "ENVIAR" else "PRÓXIMO")
            }

            Spacer(modifier = Modifier.height(8.dp)) // Espaço extra para evitar colisão com borda
        }}}
