package com.example.zooid.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizName(
    imageResId: Int,
    correctAnswer: String,
    onAnswerCorrect: () -> Unit,
    onNext: () -> Unit,
    answeredCorrectly: Boolean
) {
    var userAnswer by remember { mutableStateOf("") }
    var isAnswered by remember { mutableStateOf(false) }
    var isCorrect by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = "Imagem da ave",
            contentScale = ContentScale.Crop, // Ajusta a imagem cortando para preencher o espaço
            modifier = Modifier
                .fillMaxWidth()           // imagem ocupa toda a largura disponível
                .height(250.dp)           // altura fixa (pode ajustar)
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(16.dp),
                    clip = false
                )
                .clip(RoundedCornerShape(16.dp)) // bordas arredondadas
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = userAnswer,
            onValueChange = { userAnswer = it },
            label = { Text("Digite o nome científico") },
            singleLine = true,
            isError = isAnswered && !isCorrect,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.DarkGray,
                disabledTextColor = Color.LightGray,
                errorTextColor = Color.Red,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                errorContainerColor = Color.White,
                cursorColor = if (isAnswered && !isCorrect) Color.Red else MaterialTheme.colorScheme.primary,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent,
                errorBorderColor = Color.Transparent,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.DarkGray,
                disabledLabelColor = Color.LightGray,

                // Removi os parâmetros de background de label que não existem
            ),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                if (!isAnswered) {
                    isAnswered = true
                    isCorrect = userAnswer.trim().equals(correctAnswer.trim(), ignoreCase = true)
                }
            }),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(16.dp),
                    clip = false
                )
                .clip(RoundedCornerShape(16.dp))
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (!isAnswered) {
            Button(
                onClick = {
                    isAnswered = true
                    isCorrect = userAnswer.trim().equals(correctAnswer.trim(), ignoreCase = true)
                },
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text("Enviar")
            }
        }

        if (isAnswered) {
            Text(
                text = if (isCorrect) "Resposta correta!" else "Resposta incorreta. Tente novamente.",
                color = if (isCorrect) Color(0xFF4CAF50) else Color(0xFFF44336),
                modifier = Modifier.padding(top = 8.dp)
            )

            Button(
                onClick = {
                    if (isCorrect) {
                        onAnswerCorrect()
                    }
                    userAnswer = ""
                    isAnswered = false
                    isCorrect = false
                    onNext()
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(top = 8.dp)
            ) {
                Text("Próximo")
            }
        }
    }
}

