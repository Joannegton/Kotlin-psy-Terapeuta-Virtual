package com.joannegton.psyterapeuta.activits

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joannegton.psyterapeuta.Interation
import com.joannegton.psyterapeuta.Message
import com.joannegton.psyterapeuta.formatarHoraParaBrasileiro
import com.joannegton.psyterapeuta.ui.components.EntradaTexto
import com.joannegton.psyterapeuta.ui.components.SaidaTexto

@Composable
fun ChatScreen(modifier: Modifier = Modifier) {
    val messages = remember { mutableStateListOf<Message>() }
    var isLoading by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()


    val id_usuario = 9
    val id_terapeuta = "psy172551805015296"

    LaunchedEffect(Unit) {
        isLoading = true
        Interation.getMessages(id_usuario, id_terapeuta) {
            messages.addAll(it)
            isLoading = false
        }
    }

    LaunchedEffect(messages.size) {
        listState.animateScrollToItem(messages.size)
    }

    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()

    ) {
        LazyColumn(modifier = modifier.weight(1f), state = listState) {
            items(messages){message ->
                val paddingModifier = if (message.tipo == "Usuario") Modifier.padding(start = 50.dp) else Modifier.padding(end = 50.dp)

                Column(
                    horizontalAlignment = if (message.tipo == "Usuario") Alignment.Start else Alignment.End,
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .then(paddingModifier)
                ) {
                    val data_hora = try {
                        formatarHoraParaBrasileiro(message.data_hora)
                    } catch (e: Exception) {
                        Log.e("ChatScreen", "Error formatting date", e)
                        message.data_hora // fallback to original date
                    }
                    SaidaTexto(
                        mensagem = message.mensagem,
                        horario = data_hora,
                        isEnviadaUsuario = message.tipo == "Usuario"
                    )
                }
            }
        }

        EntradaTexto(onMessageSend = { message ->
            messages.add(Message(
                id = "temp", // Id Temporario
                id_usuario = id_usuario,
                id_terapeuta = id_terapeuta,
                data_hora = "now", // hora temporaria
                mensagem = message,
                tipo = "Usuario"
            ))

            Interation.postRequest(message, id_usuario, id_terapeuta) {response ->
                if (!response.startsWith("Erro")) {
                    // Re-fetch messages from the database
                    Interation.getMessages(id_usuario, id_terapeuta) {
                        messages.clear()
                        messages.addAll(it)
                    }
                } else {
                    // Handle error (e.g., show a toast or log the error)
                }
            }

        }
        )
    }
}

@Preview
@Composable
private fun View() {
    ChatScreen()
}