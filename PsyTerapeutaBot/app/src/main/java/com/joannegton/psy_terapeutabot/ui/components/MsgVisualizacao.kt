package com.joannegton.psy_terapeutabot.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MsgVisualizacao(
    mensagem: String,
    horario: String,
    isEnviadaUsuario: Boolean,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isEnviadaUsuario) Color(0xFFA5B69B) else Color(0xFFDCF8C6)
    val alignment = if (isEnviadaUsuario) Alignment.End else Alignment.Start

    Column(
        horizontalAlignment = alignment,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .background(backgroundColor, shape = RoundedCornerShape(16.dp))
                .padding(10.dp)
        ) {
            Text(text = mensagem, fontSize = 16.sp)
        }
        Text(
            text = horario,
            style = TextStyle(fontSize = 12.sp, color = Color.Gray),
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MsgVisualizacaoPreview() {
    MsgVisualizacao(
        mensagem = "Olá, como você está?",
        horario = "12:00",
        isEnviadaUsuario = false
    )
    MsgVisualizacao(
        mensagem = "Estou bem, obrigado!",
        horario = "12:01",
        isEnviadaUsuario = true
    )
}

