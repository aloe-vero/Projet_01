package com.example.projet_01.ui.theme

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import com.example.projet_01.R

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
        Spacer(Modifier.height(30.dp))
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
                Modifier.background(color = Color.Blue)
            ) {
                weatherViewModel.getCities().forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(text = selectionOption) },
                        onClick = {
                            weatherViewModel.updateSelectedCity(selectionOption)
                            weatherViewModel.updateExpanded(expanded = false)
                        }
                    )
                }
            }
        }
        Spacer(Modifier.height(60.dp))
        Text(
            text = weatherUiState.value.currentDate,
            style = MaterialTheme.typography.displayMedium,
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = weatherUiState.value.temperature,
            style = MaterialTheme.typography.displayMedium,
        )
        Spacer(Modifier.height(5.dp))
        Text(
            text = weatherUiState.value.city,
            style = MaterialTheme.typography.displayMedium,
        )
        Spacer(Modifier.height(20.dp))
        Image(
            painter = painterResource(weatherUiState.value.icon),
            contentDescription = "weather"
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = weatherUiState.value.weather,
            style = MaterialTheme.typography.displaySmall,

            )
    }

}