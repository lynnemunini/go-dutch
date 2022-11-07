package com.example.godutch

import android.graphics.Paint.Align
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material.icons.outlined.Create

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.godutch.components.InputField
import com.example.godutch.ui.theme.GoDutchTheme
import com.example.godutch.widgets.RoundIconButton

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
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
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
fun TopHeader(totalPerPerson: Double = 133.33){
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
                    colors = listOf(
                        Color(0xFFF94892), Color(0xFFFF7F3F), Color(0xFFFBDF07), Color(0xFF89CFFD)
                    ),
                    start = Offset(0f, 0f), // top left corner
                    end = Offset(boxSize, boxSize) // bottom right corner
                ),
                shape = RoundedCornerShape(10)
            ), contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            val total = "133.57"
            Text("Total Per Person", style = TextStyle(fontWeight = FontWeight.SemiBold), fontSize = 25.sp, color = Color.White)
            Text("$${total}", style = TextStyle(fontWeight = FontWeight.ExtraBold, fontSize = 45.sp, color = Color.White))
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MainContent() {
    BillForm(){ billAmt ->
        Log.d("AMT", "MainContent: ${billAmt.toInt() * 100}")
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BillForm(modifier: Modifier = Modifier, onValChange: (String) -> Unit = {}){
    val totalBillState = remember {
        mutableStateOf("")
    }
    val validState = remember(totalBillState.value){
        totalBillState.value.trim().isNotEmpty()
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    androidx.compose.material.Surface(modifier = Modifier
        .fillMaxWidth()
        .height(400.dp)
        .padding(20.dp), border = BorderStroke(0.05.dp, Color.LightGray),
        elevation = 0.5.dp,
        shape = RoundedCornerShape(2)
    ) {
        Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.Start) {
            InputField(valueState = totalBillState, labelId = "Enter Bill", enabled = true, isSingleLine = true, onAction = KeyboardActions{
                if (!validState) return@KeyboardActions
                //Todo - onValueChanged
                onValChange(totalBillState.value.trim())
                keyboardController?.hide()
            })
            if (validState) {
                Row(modifier = Modifier.padding(top = 20.dp, bottom = 3.dp, start = 3.dp, end = 3.dp),
                    horizontalArrangement = Arrangement.Start) {
                    Text("Split", modifier = Modifier.align(alignment = Alignment.CenterVertically))
                    Spacer(modifier = Modifier.width(120.dp))
                    Row(modifier = Modifier.padding(horizontal = 3.dp), horizontalArrangement = Arrangement.End) {
                        RoundIconButton(imageVector = Icons.Default.Remove,
                            onClick = { Log.d("Icon", "BillForm: Removed") })
                        Text(text = "2", modifier = Modifier.align(Alignment.CenterVertically).padding(start = 15.dp, end = 15.dp))
                        //Spacer(modifier = Modifier.width(30.dp))
                        RoundIconButton(imageVector = Icons.Default.Add, onClick = {
                            Log.d("Icon", "BillForm: Add")
                        })

                        
                    }

                }
            }
            else {
                Box() {

                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp {
        TopHeader()
        MainContent()
    }
}