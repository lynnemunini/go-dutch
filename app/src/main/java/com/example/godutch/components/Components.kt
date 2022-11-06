package com.example.godutch.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InputField(
    // Make a modifier optional
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    labelId:String,
    enabled: Boolean,
    isSingleLine: Boolean,
    keyBoardType: KeyboardType = KeyboardType.Number,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        value = valueState.value,
        leadingIcon = { Icon(imageVector = Icons.Outlined.AttachMoney, contentDescription = "Money Icon") },
        singleLine = isSingleLine,
        textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colors.onBackground),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = keyBoardType, imeAction = imeAction),
        keyboardActions = onAction,
        modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
        label = {
            Text(text = labelId)
        } ,
        onValueChange = {
            valueState.value = it
        },
        placeholder = { Text(text = "Enter Bill") },
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
