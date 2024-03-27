package com.example.projet_01

import android.content.ContentValues.TAG
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
        Spacer(Modifier.height(30.dp))
        ListCity()
        Spacer(Modifier.height(60.dp))

Row() {
    Column() {
            Text(
                    text = currentDate,
                    modifier = modifier,
                    style = MaterialTheme.typography.displayMedium,
                )
                Text(
                    text = "Montréal",
                    modifier = modifier,
                    style = MaterialTheme.typography.displayMedium,
                )
        }
    Spacer(Modifier.height(20.dp))
    Column (){

        Text(
            text = "-10°C",
            modifier = modifier.padding(10.dp),
            style = MaterialTheme.typography.displayMedium,
        )
    }

        }
        Spacer(Modifier.height(20.dp))
        IconWeather()
        Spacer(Modifier.height(10.dp))
        Text(
            text = "peu nuageux",
            modifier = modifier,
            style = MaterialTheme.typography.displaySmall,

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
    var ville = rememberSaveable {mutableStateOf("")}
    TextField(
        modifier = Modifier.width(350.dp),
        value = ville.value,
        onValueChange = {ville.value = it},
        label = { Text(text = "Ville: ",
            style = MaterialTheme.typography.bodyMedium,)},
        shape = RoundedCornerShape(20.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        leadingIcon = {
           Icon(
               imageVector = Icons.Filled.Search,
               contentDescription = "search",
               modifier = Modifier.clickable {
                   Log.d(TAG, "button clicked")
                   Log.i("data: ", ville.value)
               }
           )
        },
        trailingIcon={
            Icon(
                Icons.Default.Clear,
                contentDescription = "clear text",
                modifier = Modifier.clickable {
                    ville.value = ""
                }
            )
        }

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListCity(modifier: Modifier = Modifier) {
    val liste: List<String> = listOf("Montréal", "Laval", "Québec", "Position actuelle")
    var ouvert = rememberSaveable { mutableStateOf(false) }
    var selectedOptionText = rememberSaveable { mutableStateOf(liste[0]) }

    ExposedDropdownMenuBox(
        expanded = ouvert.value,
        onExpandedChange = {
            ouvert.value = !ouvert.value
        }) {
        TextField(
            value = selectedOptionText.value,
            onValueChange = {},
            modifier = Modifier
                .width(350.dp)
                .menuAnchor(),
            readOnly = true,
            label = { Text(text = "Ville: ")},
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = ouvert.value)
            }
        )
        ExposedDropdownMenu(
            expanded = ouvert.value,
            onDismissRequest = { ouvert.value = false
            },
            //modifier couleur
            Modifier.background(color = Color.Blue)
        ) {
            liste.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(text = selectionOption) },
                    onClick = {
                        selectedOptionText.value = selectionOption
                        ouvert.value = false
                    },)
            }
        }
    }

}

@RequiresApi(Build.VERSION_CODES.M)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Projet_01Theme {
        DisplayWeather()
    }
}