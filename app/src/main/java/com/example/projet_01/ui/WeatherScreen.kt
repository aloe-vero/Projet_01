package com.example.projet_01.ui


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
import androidx.compose.material.icons.filled.Info
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

import java.text.SimpleDateFormat

import java.util.Date
import java.util.Locale


@Composable
fun WeatherScreen(){
    OnloadCity()
    ImageDeFond()
    Column(modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,){
        Spacer(Modifier.height(20.dp))
        BarreDeRecherche()
        Spacer(Modifier.height(30.dp))
        ListeDeroulante()
        Spacer(Modifier.height(30.dp))
        AffichageWeather()


    }


}

@Composable
fun BarreDeRecherche(weatherViewModel: WeatherViewModel = viewModel()){
    Column {
        TextField(
            modifier = Modifier.width(350.dp),
            value = weatherViewModel.getCity(),
            onValueChange = {
                weatherViewModel.updateCity(it)
                weatherViewModel.updateError(false)
            },
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
                        Log.i("data: ",weatherViewModel.getCity())
                        weatherViewModel.weather( weatherViewModel.getCity())
                        weatherViewModel.resetSelectedCity()

                    }
                )
            },
            trailingIcon={
                if(weatherViewModel.getError()){
                    Icon(Icons.Filled.Info, "Error", tint = MaterialTheme.colorScheme.error)
                }else{
                    Icon(
                        Icons.Default.Clear,
                        contentDescription = "clear text",
                        modifier = Modifier.clickable {
                            weatherViewModel.resetCity()
                        }
                    )
                }
            }
        )
        if(weatherViewModel.getError()){
            Text(
                text = "Ville introuvable",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }

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
                        weatherViewModel.resetCity()
                        weatherViewModel.updateError(false)
                    }
                )
            }
        }
    }


}
@Composable
fun AffichageWeather(weatherViewModel: WeatherViewModel = viewModel()){
    val weatherUiState = weatherViewModel.uiState.collectAsState()
    val iconValue = weatherUiState.value.weather[0].icon
   Row(modifier = Modifier
       .width(350.dp)
       .padding(25.dp)) {
       Column(
           verticalArrangement = Arrangement.Top,
           horizontalAlignment = Alignment.Start,) {
           Text(
               text = weatherUiState.value.name,
               style = MaterialTheme.typography.displayMedium,
               modifier = Modifier.width(190.dp)
           )
           Text(
               text = SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH).format(Date()),
               style = MaterialTheme.typography.displayMedium,
           )

       }
       Spacer(Modifier.width(6.dp))
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
                   textAlign = TextAlign.Center,
               )
           }

       }
   }
    Image(painter = painterResource(weatherViewModel.getIconCode(iconValue)), contentDescription ="meteo image",Modifier.size(200.dp) )
    Spacer(Modifier.height(35.dp))
    Text(
        text = weatherUiState.value.weather[0].description,
        style = MaterialTheme.typography.displayMedium,

        )
}
@Composable
fun ImageDeFond(weatherViewModel: WeatherViewModel = viewModel()){
    val weatherUiState = weatherViewModel.uiState.collectAsState()
    val iconValue = weatherUiState.value.weather[0].icon
    Box(
        modifier = with (Modifier){
            fillMaxSize()
                .paint(

                    painterResource(weatherViewModel.getBackground(iconValue)),
                    contentScale = ContentScale.FillBounds)

        })
}



@Composable
fun OnloadCity(weatherViewModel: WeatherViewModel = viewModel()){
    weatherViewModel.weather( "Montréal")
    weatherViewModel.updateSelectedCity("Montréal")
}

