package com.example.projet_01.ui

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.projet_01.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun WeatherScreen(weatherViewModel: WeatherViewModel = viewModel()){
    val weatherUiState = weatherViewModel.uiState.collectAsState()
    OnloadCity()
    ImageDeFond()
    Column(modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,){
        Spacer(Modifier.height(20.dp))
        BarDeRecherche()
        Spacer(Modifier.height(30.dp))
        ListeDeroulante()
        Spacer(Modifier.height(30.dp))
        AffichageWeather()


    }


}

@Composable
fun BarDeRecherche(weatherViewModel: WeatherViewModel = viewModel()){
    TextField(
        modifier = Modifier.width(350.dp),
        value = weatherViewModel.getCity(),
        onValueChange = {weatherViewModel.updateCity(it)},
        label = { Text(text = "Ville: ",
            style = MaterialTheme.typography.bodyMedium,)
        },
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
                    Log.d(ContentValues.TAG, "button clicked")
                    Log.i("data: ",weatherViewModel.getCity())
                    weatherViewModel.weather( weatherViewModel.getCity())

                }
            )
        },
        trailingIcon={
            Icon(
                Icons.Default.Clear,
                contentDescription = "clear text",
                modifier = Modifier.clickable {
                    weatherViewModel.resetCity()
                }
            )
        }

    )
}
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ListeDeroulante(weatherViewModel: WeatherViewModel = viewModel()){
    ExposedDropdownMenuBox(
        expanded = weatherViewModel.getExpanded(),
        onExpandedChange = {
            weatherViewModel.updateExpanded(!weatherViewModel.getExpanded())
        }) {
        TextField(
            value = weatherViewModel.getSelectedCity(),
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
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = weatherViewModel.getExpanded())
            }
        )
        ExposedDropdownMenu(
            expanded = weatherViewModel.getExpanded(),
            onDismissRequest = { weatherViewModel.updateExpanded(expanded = false)
            },
            //modifier couleur
            Modifier.background(color = Color.LightGray)
        ) {
            weatherViewModel.getCities().forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(text = selectionOption) },
                    onClick = {
                        weatherViewModel.updateSelectedCity(selectionOption)
                        weatherViewModel.updateExpanded(expanded = false)
                        weatherViewModel.weather( weatherViewModel.getSelectedCity())
                    }
                )
            }
        }
    }


}
@Composable
fun AffichageWeather(weatherViewModel: WeatherViewModel = viewModel()){
    val weatherUiState = weatherViewModel.uiState.collectAsState()
    val icon =when(weatherUiState.value.weather[0].icon){
        "01d"-> R.drawable._01d
        "01n" -> R.drawable._01n
        "02d"-> R.drawable._02d
        "02n"-> R.drawable._02n
        "03d"-> R.drawable._03d
        "03n"-> R.drawable._03n
        "04d"-> R.drawable._04d
        "04n"-> R.drawable._04n
        "09d"-> R.drawable._09d
        "09n"-> R.drawable._09n
        "10d"-> R.drawable._10d
        "10n"-> R.drawable._10n
        "11d"-> R.drawable._11d
        "11n"-> R.drawable._11n
        "13d"-> R.drawable._13d
        "13n"-> R.drawable._13n
        "50d"-> R.drawable._50d
        "50n"-> R.drawable._50n
        else -> R.drawable.unknown
    }
   Row(modifier = Modifier
       .width(350.dp)
       .padding(25.dp)) {
       Column(
           verticalArrangement = Arrangement.Top,
           horizontalAlignment = Alignment.Start,) {
           Text(
               text = weatherUiState.value.name,
               style = MaterialTheme.typography.displayMedium,
           )
           Text(
               text = SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH).format(Date()),
               style = MaterialTheme.typography.displayMedium,
           )

       }
       Column(modifier = Modifier.weight(1f),verticalArrangement = Arrangement.Center,
           horizontalAlignment = Alignment.End,) {
           Card(modifier = Modifier
               .size(width = 125.dp, height = 50.dp),elevation = CardDefaults.cardElevation(
               defaultElevation = 6.dp
           )) {
               Text(
                   modifier = Modifier
                       .padding(10.dp)
                       .fillMaxWidth(),
                   text = weatherUiState.value.main.temp.toString() + "°C",
                   style = MaterialTheme.typography.displayMedium,
                   textAlign = TextAlign.Justify,
               )
           }

       }
   }
    Image(painter = painterResource(icon), contentDescription ="meteo image",Modifier.size(200.dp) )
    Text(
        text = weatherUiState.value.weather[0].description,
        style = MaterialTheme.typography.displaySmall,

        )
}
@Composable
fun ImageDeFond(weatherViewModel: WeatherViewModel = viewModel()){
    val weatherUiState = weatherViewModel.uiState.collectAsState()
    val backgroundImage =when(weatherUiState.value.weather[0].icon){
        "01d"-> R.drawable._jour
        "01n" -> R.drawable._nuit
        "02d"-> R.drawable._jour
        "02n"-> R.drawable._jour
        "03d"-> R.drawable._jour
        "03n"-> R.drawable._nuit
        "04d"-> R.drawable._pluie
        "04n"-> R.drawable._nuit
        "09d"-> R.drawable._pluie
        "09n"-> R.drawable._nuit
        "10d"-> R.drawable._orage
        "10n"-> R.drawable._orage
        "11d"-> R.drawable._orage
        "11n"-> R.drawable._orage
        "13d"-> R.drawable._neige
        "13n"-> R.drawable._nuit
        "50d"-> R.drawable._neige
        "50n"-> R.drawable._nuit
        else -> R.drawable.ic_launcher_background
    }
    Box(
        modifier = with (Modifier){
            fillMaxSize()
                .paint(
                    // Replace with your image id
                    painterResource(backgroundImage),
                    contentScale = ContentScale.FillBounds)

        })
}
@Composable
fun OnloadCity(weatherViewModel: WeatherViewModel = viewModel()){
    weatherViewModel.onloadCity()
    weatherViewModel.weather( weatherViewModel.getSelectedCity());
}
@Composable
fun AffImage(weatherViewModel: WeatherViewModel = viewModel()) {
    val weatherUiState = weatherViewModel.uiState.collectAsState()
    AsyncImage(
        model = "https://openweathermap.org/img/wn/"+weatherUiState.value.weather[0].icon+".png",
        contentDescription = null,
    )
}