package com.example.projet_01.ui.theme

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.projet_01.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(weatherViewModel: WeatherViewModel = viewModel()){
    val weatherUiState = weatherViewModel.uiState.collectAsState()
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.LightGray),
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
                    weatherViewModel.Weather( weatherViewModel.getCity())
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
            Modifier.background(color = Color.White)
        ) {
            weatherViewModel.getCities().forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(text = selectionOption) },
                    onClick = {
                        weatherViewModel.updateSelectedCity(selectionOption)
                        weatherViewModel.updateExpanded(expanded = false)
                        weatherViewModel.Weather( weatherViewModel.getSelectedCity())
                    }
                )
            }
        }
    }


}
@Composable
fun AffichageWeather(weatherViewModel: WeatherViewModel = viewModel()){
    val weatherUiState = weatherViewModel.uiState.collectAsState()

   Row {
       Column(modifier = Modifier
           .fillMaxSize(),
           verticalArrangement = Arrangement.Top,
           horizontalAlignment = Alignment.CenterHorizontally,) {
           Text(
               text = SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH).format(Date()),
               style = MaterialTheme.typography.displayMedium,
           )
           Spacer(Modifier.height(10.dp))
           Text(
               text = weatherUiState.value.main.temp.toString(),
               style = MaterialTheme.typography.displayMedium,
           )
           Spacer(Modifier.height(5.dp))
           Text(
               text = weatherUiState.value.name,
               style = MaterialTheme.typography.displayMedium,
           )
           Spacer(Modifier.height(20.dp))
           AffImage();
           Spacer(Modifier.height(10.dp))
           Text(
               text = weatherUiState.value.weather[0].description,
               style = MaterialTheme.typography.displaySmall,

               )
       }
   }

}

@Composable
fun AffImage(weatherViewModel: WeatherViewModel = viewModel()) {
    val weatherUiState = weatherViewModel.uiState.collectAsState()
    AsyncImage(
        model = "https://openweathermap.org/img/wn/"+weatherUiState.value.weather[0].icon+".png",
        contentDescription = null,
    )
}