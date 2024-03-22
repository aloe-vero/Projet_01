package com.example.projet_01

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.projet_01.modele.Meteo
import com.example.projet_01.ui.theme.Projet_01Theme
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Projet_01Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DisplayWeather()
                }
            }
        }
    }
}

//Englobe tout l'affichage de l'application
@Composable
fun DisplayWeather(modifier: Modifier = Modifier) {
    val currentDate: String = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date())
    Column(modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.height(20.dp))
        SearchBar();
        Spacer(Modifier.height(70.dp))
        Text(
            text = currentDate,
            modifier = modifier
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = "-10°C",
            modifier = modifier
        )
        Spacer(Modifier.height(5.dp))
        Text(
            text = "Montréal",
            modifier = modifier
        )
        Spacer(Modifier.height(20.dp))
        Icon()
        Spacer(Modifier.height(10.dp))
        Text(
            text = "peu nuageux",
            modifier = modifier
        )
    }
}
@Composable
fun Icon(modifier: Modifier = Modifier){
    Image(
        painter = painterResource(R.drawable.icon_peu_nuageux),
        contentDescription = "weather"
    )
}
@Composable
fun SearchBar(modifier: Modifier = Modifier){
    TextField(
        value = "",
        onValueChange = {/*TODO*/},
        label = { Text(text = "Ville: ")},
        shape = RoundedCornerShape(20.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        trailingIcon={
            androidx.compose.material3.Icon(
                Icons.Default.Clear,
                contentDescription = "clear text",
                modifier = Modifier.clickable {
                    /*TODO*/
                }
            )
        }

    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Projet_01Theme {
        DisplayWeather()
    }
}