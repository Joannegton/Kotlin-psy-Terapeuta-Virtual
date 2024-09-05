package com.joannegton.psyterapeuta

import android.util.Log
import com.google.gson.Gson
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.gson.gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

object Interation {
    private const val API_URL = " https://bc0c-201-55-46-78.ngrok-free.app/api/v1/interacoes/"

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            gson()
        }
    }

    fun postRequest(prompt: String, id_usuario: Int, id_terapeuta: String, callback: (String) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response: HttpResponse = client.post(API_URL + "send") {
                    contentType(ContentType.Application.Json)
                    setBody(
                        mapOf(
                            "id_usuario" to id_usuario,
                            "id_terapeuta" to id_terapeuta,
                            "mensagem" to prompt,
                            "tipo" to "Usuario"
                        )
                    )
                }

                if (response.status.value in 200..299) {
                    val responseBody = response.body<String>()
                    callback(responseBody)
                } else {
                    callback("Erro: ${response.status.description}")
                }
            } catch (e: Exception) {
                callback("Erro ao processar resposta: ${e.message}")
                Log.e("TAG", "Erro ao processar resposta: ${e.message}")
            }
        }
    }

    fun getMessages(id_usuario: Int, id_terapeuta: String, callback: (List<Message>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response: HttpResponse = client.get(API_URL + "list?id_usuario=$id_usuario&id_terapeuta=$id_terapeuta")
                if (response.status.value in 200..299) {
                    val messages: String = response.body()
                    Log.d("TAG", "Mensagens recebidas: $messages")
                    val gson = Gson()
                    val messageList = gson.fromJson(messages, Array<Message>::class.java).toList()
                    callback(messageList)
                } else {
                    callback(emptyList())
                    Log.e("TAG", "Erro: ${response.status.description}")
                }
            } catch (e: Exception) {
                callback(emptyList())
                Log.e("TAG", "Erro ao processar resposta: ${e.message}")
            }
        }
    }
}

data class Message(
    val id: String,
    val id_usuario: Int,
    val id_terapeuta: String,
    val data_hora: String,
    val mensagem: String,
    val tipo: String,

)

fun formatarHoraParaBrasileiro(dataHora: String): String {
    val formatoOriginal = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val formatoBrasileiro = SimpleDateFormat("dd/MM HH:mm", Locale("pt", "BR"))
    val data = formatoOriginal.parse(dataHora)
    return formatoBrasileiro.format(data)
}