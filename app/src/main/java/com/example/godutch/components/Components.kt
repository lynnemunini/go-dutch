package com.example.godutch.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Create
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun InputField(
    // Make a modifier optional
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    labelId:String,
    enabled: Boolean,
    isSingleLine: Boolean,
    keyBoardType: KeyboardType = KeyboardType.Ascii,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        value = valueState.value,
        leadingIcon = { Icon(imageVector = Icons.Outlined.Create, contentDescription = "Money Icon") },
        modifier = Modifier.fillMaxWidth(),
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
