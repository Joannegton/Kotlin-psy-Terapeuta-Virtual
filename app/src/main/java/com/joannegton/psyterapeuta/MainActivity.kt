package com.joannegton.psyterapeuta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.joannegton.psyterapeuta.activits.CadastroScreen
import com.joannegton.psyterapeuta.activits.ChatScreen
import com.joannegton.psyterapeuta.activits.LoginScreen
import com.joannegton.psyterapeuta.ui.components.TopBar
import com.joannegton.psyterapeuta.ui.theme.PsyTerapeutaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PsyTerapeutaTheme {
                val navController = rememberNavController()
                NavGraphSetup(navController = navController)


            }
        }
    }
}

@Composable
fun NavGraphSetup(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login") {
        composable("chat"){ ChatScreen()}
        composable("cadastro"){ CadastroScreen(navController) }
        composable("login"){ LoginScreen(navController) }
    }
}
