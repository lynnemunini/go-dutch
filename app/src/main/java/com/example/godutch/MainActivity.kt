package com.example.godutch

import android.graphics.Paint.Align
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.godutch.ui.theme.GoDutchTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp{

            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit){
    GoDutchTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            color = MaterialTheme.colors.background
        ) {
            TopHeader()
        }
    }

}
@Preview
@Composable
fun TopHeader(){
    androidx.compose.material.Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp).height(120.dp),
        shape = RoundedCornerShape(10),
        color = Color(0xFFDEF5E5)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Text("Total Per Person", textAlign = TextAlign.Center, style = TextStyle(fontWeight = FontWeight.SemiBold))
            Text("$133.00", textAlign = TextAlign.Center, style = TextStyle(fontWeight = FontWeight.ExtraBold, fontSize = 22.sp))
        }

    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp {
        TopHeader()

    }
}