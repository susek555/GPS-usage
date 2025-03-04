package com.gps_usage

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gps_usage.showCoordinates.CoordinatesActivity
import com.gps_usage.showCoordinates.ShowCoordinates
import com.gps_usage.ui.theme.GPSusageTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GPSusageTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
                Scaffold(modifier = Modifier.fillMaxSize()) {  innerPadding ->
                    StartShowCoordinatesScreen { openCoordinatesActivity() }
                }
            }
        }
    }

    private fun openCoordinatesActivity() {
        val intent = Intent(this, CoordinatesActivity::class.java)
        startActivity(intent)
    }
}


@Composable
fun StartShowCoordinatesScreen(openCoordinatesActivity: () -> Unit){
    ShowCoordinates(openCoordinatesActivity).RunApp()
}




//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    GPSusageTheme {
//        Greeting("Android")
//    }
//}