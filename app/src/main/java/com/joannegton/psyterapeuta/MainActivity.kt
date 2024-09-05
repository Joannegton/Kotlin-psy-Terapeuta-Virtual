package com.joannegton.psyterapeuta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joannegton.psyterapeuta.activits.ChatScreen
import com.joannegton.psyterapeuta.ui.theme.PsyTerapeutaTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PsyTerapeutaTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .imePadding(), //ajusta o teclado quando aparece na tela
                    topBar = {
                        TopAppBar(
                            title = {
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Image(painter = painterResource(
                                        id = R.drawable.psy_pequeno),
                                        contentDescription = "psy icon",
                                        modifier = Modifier.size(70.dp)
                                    )
                                    Text(text = "Psy: Seu Terapeuta Virtual", fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.Bold)
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                        )
                    },
                    containerColor = MaterialTheme.colorScheme.background
                ) { innerPadding ->
                        Box(modifier = Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                            Image(
                                painter = painterResource(id = R.drawable.psy_grande),
                                contentDescription = "Background Image",
                                modifier = Modifier
                                    .size(500.dp)
                                    .graphicsLayer(alpha = 0.5f), // Define a opacidade
                            )
                            ChatScreen()
                        }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PsyTerapeutaTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Image(painter = painterResource(
                                id = R.drawable.psy_pequeno),
                                contentDescription = "psy icon",
                                modifier = Modifier.size(70.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = "Psy: Seu Terapeuta Virtual",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
                )
            },
            containerColor = MaterialTheme.colorScheme.background
        ) { innerPadding ->
            ChatScreen(modifier = Modifier.padding(innerPadding))
        }
    }
}