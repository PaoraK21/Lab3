package com.example.laboratorio3paolaquinones

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Label
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.laboratorio3paolaquinones.ui.theme.Laboratorio3PaolaQuinonesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Laboratorio3PaolaQuinonesTheme {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Greeting(
                        name = "Paola Quiñones"
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        banner()
        Spacer(modifier = Modifier.height(30.dp))

        Image(
            painter = painterResource(id = R.drawable.c88e86b56288d6b4a7958ba6e3c4a01a),
            contentDescription = "Imagen",
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = "$name",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(15.dp))

        JuegoAdivinarNumero()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    Laboratorio3PaolaQuinonesTheme {
        Greeting("Tiene 3 Intentos para adivinar")
    }
}

@Composable
fun banner(){
    Row(
        verticalAlignment = Alignment.CenterVertically)
    {
        Spacer(modifier = Modifier.width(30.dp))
        Text("Juego de adivinar numero")

        Image(
            painter = painterResource(id = R.drawable.images),
            contentDescription = "Imagen",
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun JuegoAdivinarNumero() {
    val contexto = LocalContext.current
    val numeroAleatorio = remember { (1..10).random() }
    var intentosRestantes by remember { mutableStateOf(3) }
    var inputUsuario by remember { mutableStateOf("") }
    var juegoFinalizado by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = inputUsuario,
            onValueChange = { inputUsuario = it },
            label = { Text("Numero a adivinar") },
            enabled = !juegoFinalizado
        )

        Spacer(modifier = Modifier.height(15.dp))

        Button(
            onClick = {
                if (juegoFinalizado) return@Button

                val intento = inputUsuario.toIntOrNull()
                if (intento == null || intento !in 1..100) {
                    Toast.makeText(contexto, "Ingresa un numero valido del 1 al 100", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                if (intento == numeroAleatorio) {
                    Toast.makeText(contexto, "Adivinaste!", Toast.LENGTH_LONG).show()
                    juegoFinalizado = true
                } else {
                    intentosRestantes--
                    if (intentosRestantes == 0) {
                        Toast.makeText(contexto, "Lo siento. Era $numeroAleatorio", Toast.LENGTH_LONG).show()
                        juegoFinalizado = true
                    } else {
                        Toast.makeText(contexto, "Incorrecto. Quedan $intentosRestantes intentos", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            enabled = !juegoFinalizado
        ) {
            Text("Adivinar")
        }

        if (juegoFinalizado) {
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                inputUsuario = ""
                intentosRestantes = 3
                juegoFinalizado = false
            }) {
                Text("Jugar de nuevo")
            }
        }
    }
}
