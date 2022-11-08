package com.example.godutch

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

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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
            MyApp {

            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    GoDutchTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Column() {
                //TopHeader()
                MainContent()
            }
        }
    }
}

@Preview
@Composable
fun TopHeader(totalPerPerson: Double = 0.00) {
    // convert the box size to pixels
    val boxSize = with(LocalDensity.current) { 300.dp.toPx() }
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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val total = String.format("%.2f", totalPerPerson)
            Text(
                "Total Per Person",
                style = TextStyle(fontWeight = FontWeight.SemiBold),
                fontSize = 25.sp,
                color = Color.White
            )
            Text(
                total,
                style = TextStyle(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 45.sp,
                    color = Color.White
                )
            )
        }
    }
}

@Composable
fun MainContent() {
    BillForm() { billAmt ->
        Log.d("AMT", "MainContent: ${billAmt.toInt() * 100}")
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BillForm(modifier: Modifier = Modifier, onValChange: (String) -> Unit = {}) {
    val totalBillState = remember {
        mutableStateOf("0.0")
    }
    val validState = remember(totalBillState.value) {
        totalBillState.value.trim().isNotEmpty()
    }
    val splitCount = remember {
        mutableStateOf(1)
    }
    if (splitCount.value <= 1) {
        splitCount.value = 1
    }
    val sliderPositionState = remember {
        mutableStateOf(0f)
    }
    val tipAmountState = remember {
        mutableStateOf(0.0)
    }
    val amountPerPerson = remember {
        mutableStateOf(0.0)
    }
    val tipPercentage: Int = (sliderPositionState.value * 100).toInt()

    TopHeader(amountPerPerson.value)
    val keyboardController = LocalSoftwareKeyboardController.current
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .padding(20.dp), border = BorderStroke(0.05.dp, Color.LightGray),
        elevation = 0.5.dp,
        shape = RoundedCornerShape(2)
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            InputField(
                valueState = totalBillState,
                labelId = "Enter Bill",
                enabled = true,
                isSingleLine = true,
                onAction = KeyboardActions {
                    if (!validState) return@KeyboardActions
                    onValChange(totalBillState.value.trim())
                    keyboardController?.hide()
                })

                Row(
                    modifier = Modifier.padding(
                        top = 20.dp,
                        bottom = 3.dp,
                        start = 3.dp,
                        end = 3.dp
                    ),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text("Split", modifier = Modifier.align(alignment = Alignment.CenterVertically))
                    Spacer(modifier = Modifier.width(120.dp))
                    Row(
                        modifier = Modifier.padding(horizontal = 3.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        RoundIconButton(imageVector = Icons.Default.Remove,
                            onClick = {
                                if (splitCount.value != 1) {
                                    splitCount.value = splitCount.value - 1
                                }
                                else {
                                    splitCount.value = 1
                                }
                                amountPerPerson.value = calculateTotalPerPerson(
                                    billAmt = totalBillState.value.toDouble(),
                                    tipPercentage = tipPercentage,
                                    splitCount = splitCount.value
                                )
                            })
                        Text(
                            text = splitCount.value.toString(),
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(start = 15.dp, end = 15.dp)
                        )
                        //Spacer(modifier = Modifier.width(30.dp))
                        RoundIconButton(imageVector = Icons.Default.Add, onClick = {
                            splitCount.value = splitCount.value + 1
                            amountPerPerson.value = calculateTotalPerPerson(
                                billAmt = totalBillState.value.toDouble(),
                                tipPercentage = tipPercentage,
                                splitCount = splitCount.value
                            )
                        })
                    }
                }
            // Tip Row
            Row(
                modifier = Modifier.padding(
                    horizontal = 3.dp,
                    vertical = 30.dp
                ),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(text = "Tip", modifier = Modifier.align(alignment = Alignment.CenterVertically))
                Spacer(modifier = Modifier.width(200.dp))
                Text(text = "$${String.format("%.2f", tipAmountState.value)}", modifier = Modifier.align(alignment = Alignment.CenterVertically))

            }
            Column(verticalArrangement = Arrangement.Center) {
                Text(text = "${tipPercentage}%", modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
                Spacer(modifier = Modifier.height(14.
                dp))
                //Slider
                Slider(value = sliderPositionState.value,
                    onValueChange = {newVal ->
                        sliderPositionState.value = newVal
                        tipAmountState.value = calculateTotalTip(totalBillState.value.toDouble(), tipPercentage)
                        amountPerPerson.value = calculateTotalPerPerson(
                            billAmt = totalBillState.value.toDouble(),
                            tipPercentage = tipPercentage,
                            splitCount = splitCount.value
                        )
                        Log.d("Slider","BillForm: $newVal")
                    },
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                    steps = 5,
                    onValueChangeFinished = {
                      //Log.d(TAG, "Finished...")
                    },
                    colors = SliderDefaults.colors(thumbColor = Color(0xFFFF7F3F), activeTrackColor = Color(0xFFFBDF07)))

            }
        }
    }

}

fun calculateTotalPerPerson(billAmt: Double, tipPercentage: Int, splitCount: Int): Double {
    val total = billAmt + (billAmt * tipPercentage / 100)
    return total / splitCount
}

fun calculateTotalTip(billAmt: Double, tipPercentage: Int): Double {
    return billAmt * tipPercentage / 100
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp {
        TopHeader()
        MainContent()
    }
}