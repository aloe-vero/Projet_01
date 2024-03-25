package com.example.projet_01

import android.graphics.drawable.Icon
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.projet_01.ui.theme.Projet_01Theme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
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
@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun DisplayWeather(modifier: Modifier = Modifier) {
    val currentDate: String = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date())
    Column(modifier = modifier
        .fillMaxSize()
        .background(Color.LightGray),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,) {
        Spacer(Modifier.height(20.dp))
        SearchBar()
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
        IconWeather()
        Spacer(Modifier.height(10.dp))
        Text(
            text = "peu nuageux",
            modifier = modifier
        )
    }
}
@Composable
fun IconWeather(modifier: Modifier = Modifier){
    Image(
        painter = painterResource(R.drawable.icon_peu_nuageux),
        contentDescription = "weather"
    )
}
@RequiresApi(Build.VERSION_CODES.M)
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
        leadingIcon = {
           Icon(
               imageVector = Icons.Filled.Search,
               contentDescription = "search"
           )
        },
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

@Composable
fun ListCity(modifier: Modifier = Modifier){
    val liste : List<String> = listOf("Montréal", "Laval", "Québec", "Position actuelle")

}

@RequiresApi(Build.VERSION_CODES.M)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Projet_01Theme {
        DisplayWeather()
    }
}