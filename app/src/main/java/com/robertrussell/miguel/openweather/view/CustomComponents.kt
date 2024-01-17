package com.robertrussell.miguel.openweather.view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.robertrussell.miguel.openweather.R

@Composable
fun HeaderTextView(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        modifier = modifier,
        style = TextStyle(color = Color(0xFF302D2D), fontSize = 24.sp, fontWeight = FontWeight.Bold)
    )
}

@Composable
fun BasicTextView(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        modifier = modifier,
        style = TextStyle(
            color = Color(0xFF302D2D),
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal
        )
    )
}

@Composable
fun Header2TextView(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        modifier = modifier,
        style = TextStyle(
            color = Color(0xFF302D2D),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
    )
}

@Composable
fun BasicEditText(labelValue: String) {
    val textValue = remember { mutableStateOf("") }

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
            .border(1.dp, Color.LightGray, shape = RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        singleLine = true,
        maxLines = 1,
        label = { Text(text = labelValue) },
        value = textValue.value,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next
        ),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedTextColor = Color.DarkGray
        ),
        onValueChange = {
            textValue.value = it
        })
}

@Composable
fun PasswordEditText(labelValue: String) {
    val localFocusManager = LocalFocusManager.current

    val passwordValue = remember {
        mutableStateOf("")
    }

    val passwordVisible = remember {
        mutableStateOf(false)
    }

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
            .border(1.dp, Color.LightGray, shape = RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        singleLine = true,
        maxLines = 1,
        label = { Text(text = labelValue) },
        value = passwordValue.value,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedTextColor = Color.DarkGray
        ),
        keyboardActions = KeyboardActions {
            localFocusManager.clearFocus()
        },
        onValueChange = {
            passwordValue.value = it
        },
        trailingIcon = {

            val iconImage = if (passwordVisible.value) {
                ImageVector.vectorResource(id = R.drawable.ic_visible)
            } else {
                ImageVector.vectorResource(id = R.drawable.ic_visible_off)
            }

            val description = if (passwordVisible.value) {
                "Hide password"
            } else {
                "Show password"
            }

            IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                Icon(imageVector = iconImage, contentDescription = description)
            }

        },
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation()
    )
}

@Composable
fun BasicButton(labelValue: String, onButtonClicked: () -> Unit) {
    Button(
        onClick = {
            /*TODO*/
        }, shape = RoundedCornerShape(16.dp), modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight(), colors = ButtonDefaults.buttonColors(
            Color(0xFF333333)
        )
    ) {
        Text(
            text = labelValue,
            color = Color(0xFFD7D7D7),
            fontSize = 18.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}
