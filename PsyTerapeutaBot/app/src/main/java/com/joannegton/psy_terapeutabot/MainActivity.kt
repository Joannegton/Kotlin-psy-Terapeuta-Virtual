package com.joannegton.psy_terapeutabot

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import com.joannegton.psy_terapeutabot.ui.Interation
import com.joannegton.psy_terapeutabot.ui.components.EntradaTexto
import com.joannegton.psy_terapeutabot.ui.components.VisualizacaoMsgs
import com.joannegton.psy_terapeutabot.ui.theme.PsyTerapeutaBotTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PsyTerapeutaBotTheme {
                PsyScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PsyScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Psy: Terapeuta Virtual") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF2875F8),
                        titleContentColor = Color.White
                    )
                )
            }
        ) { paddingValues ->
            var chatLog by remember { mutableStateOf(listOf<Pair<String, Boolean>>()) }
            var isLoading by remember { mutableStateOf(false) }
            val listState = rememberLazyListState()

            val id_usuario = 2
            val id_terapeuta = "psy172508057914245"

            // Carregar mensagens iniciais ao iniciar a tela
            LaunchedEffect(Unit) {
                isLoading = true
                Interation.getMessages(id_usuario, id_terapeuta) { messages ->
                    chatLog = messages.map { it.mensagem to (it.tipo == "Usuario") }
                    isLoading = false
                }
            }

            LaunchedEffect(chatLog.size) {
                if (chatLog.isNotEmpty()) {
                    listState.animateScrollToItem(chatLog.size - 1)
                }
            }

            ConstraintLayout(
                ConstraintSet {
                    val visualizacaoMsgs = createRefFor("visualizacaoMsgs")
                    val loadingIndicator = createRefFor("loadingIndicator")
                    val entradaTexto = createRefFor("entradaTexto")

                    constrain(visualizacaoMsgs) {
                        top.linkTo(parent.top)
                    }
                    constrain(loadingIndicator) {
                        bottom.linkTo(entradaTexto.top)
                    }
                    constrain(entradaTexto) {
                        bottom.linkTo(parent.bottom)
                    }
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                VisualizacaoMsgs(
                    chatLog = chatLog,
                    listState = listState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .layoutId("visualizacaoMsgs")
                )

                if (isLoading) {
                    Text(
                        "Carregando...",
                        modifier = Modifier
                            .layoutId("loadingIndicator")
                    )
                }

                EntradaTexto(
                    onMessageSend = { message ->
                        chatLog = chatLog + (message to true)
                        isLoading = true
                        Interation.postRequest(message, id_usuario, id_terapeuta) { response ->
                            chatLog = chatLog + (response to false)
                            isLoading = false
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .layoutId("entradaTexto")
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PsyTerapeutaBotTheme {
        PsyScreen()
    }
}
