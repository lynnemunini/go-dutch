package com.example.godutch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Create

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
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
            color = MaterialTheme.colors.background,
            modifier = Modifier.fillMaxWidth().fillMaxHeight()
        ) {
            Column() {
                TopHeader()
                MainContent()
            }
        }
    }

}
@Preview
@Composable
fun TopHeader(totalPerPerson: Double = 0.0){
    // convert the box size to pixels
    val boxSize = with(LocalDensity.current) { 300.dp.toPx()}
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp)
            .padding(15.dp)
            .height(200.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFFF94892), Color(0xFFFF7F3F), Color(0xFFFBDF07), Color(0xFF89CFFD)
                    ),
                    start = Offset(0f, 0f), // top left corner
                    end = Offset(boxSize, boxSize) // bottom right corner
                ),
                shape = RoundedCornerShape(10)
            ), contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            val total = ".2f".format(totalPerPerson)
            Text("Total Per Person", style = TextStyle(fontWeight = FontWeight.SemiBold), fontSize = 25.sp, color = Color.White)
            Text("$${total}", style = TextStyle(fontWeight = FontWeight.ExtraBold, fontSize = 45.sp, color = Color.White))
        }
    }
}

@Composable
fun MainContent() {
    androidx.compose.material.Surface(modifier = Modifier
        .fillMaxWidth()
        .height(400.dp)
        .padding(20.dp), border = BorderStroke(0.05.dp, Color.LightGray),
        elevation = 0.5.dp,
        shape = RoundedCornerShape(2)
    ) {
            Column(modifier = Modifier.padding(10.dp)) {
                OutLineTextField()
            }
    }

}

@Composable
fun OutLineTextField(){
    var text by remember { mutableStateOf(TextFieldValue(""))}
        OutlinedTextField(
            value = text,
            leadingIcon = { Icon(Icons.Outlined.Create, contentDescription = "Account Icon") },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "Amount")
            } ,
            onValueChange = {
                text = it
            },
            placeholder = { Text(text = "Enter Bill")},
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFFBDF07),
                unfocusedBorderColor = Color(0xFFFF7F3F),
                leadingIconColor = Color(0xFFFF7F3F),
                placeholderColor = Color.LightGray,
                cursorColor = Color(0xFFFBDF07),
                focusedLabelColor = Color(0xFFFF7F3F),
            )
        )
    }


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp {
        TopHeader()
        MainContent()
    }
}