package com.demo.tankly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.demo.tankly.ui.theme.TanklyTheme
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

data class Tank(
    val id: Int,
    val name: String,
    val country: String,
    val type: String,
    val year_mfg: Int
)

typealias ApiResponse = List<Tank>

interface ApiService {
    @GET("/api/tanks")
    suspend fun getData(): ApiResponse
}

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:5000/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}

class ApiViewModel : ViewModel() {
    private val _text = mutableStateOf("Press the button to load data")
    val text: State<String> = _text

    fun fetchData() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getData()
                _text.value = response.joinToString(", ") { it.name }
            } catch (e: Exception) {
                _text.value = "Error fetching data"
            }
        }
    }
}

@Composable
fun ApiScreen(apiViewModel: ApiViewModel = viewModel()) {
    val text by apiViewModel.text

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { apiViewModel.fetchData() }) {
            Text("Fetch Data")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text)
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TanklyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ApiScreen(
                        apiViewModel = viewModel(),
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TanklyTheme {
        ApiScreen(apiViewModel = viewModel())
    }
}
