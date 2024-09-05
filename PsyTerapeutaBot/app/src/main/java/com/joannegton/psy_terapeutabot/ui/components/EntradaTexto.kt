package com.joannegton.psy_terapeutabot.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EntradaTexto(
    onMessageSend: (String) -> Unit, // Callback para enviar mensagem
    modifier: Modifier = Modifier
) {
    var userMessage by remember { mutableStateOf("") }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        BasicTextField(
            value = userMessage,
            onValueChange = { userMessage = it },
            textStyle = TextStyle(fontSize = 18.sp, color = Color.Black),
            modifier = Modifier
                .weight(1f)
                .background(Color.LightGray)
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Button(
            onClick = {
                if (userMessage.isNotBlank()) {
                    onMessageSend(userMessage) // Chama o callback para enviar a mensagem
                    userMessage = ""}
            }
        ) {
            Text("Enviar")
        }
    }
}

