package com.joannegton.psy_terapeutabot.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun VisualizacaoMsgs(
    chatLog: List<Pair<String, Boolean>>,
    listState: LazyListState,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        state = listState,
        modifier = modifier
    ) {
        items(chatLog.size) { index ->
            val (message, isUser) = chatLog[index]
            MsgVisualizacao(mensagem = message, horario = "12:00", isEnviadaUsuario = isUser)
        }
    }
}