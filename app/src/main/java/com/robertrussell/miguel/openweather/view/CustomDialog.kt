package com.robertrussell.miguel.openweather.view

import android.R
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.robertrussell.miguel.openweather.view.navigation.Navigation
import com.robertrussell.miguel.openweather.view.navigation.Pages

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomDialog(
    title: String,
    setShowDialog: (Boolean) -> Unit,
    type: String,
    message: String
) {
    Dialog(
        onDismissRequest = {
            Log.d("TEST-Observer", "CustomDialog::onDismissRequest = $type")
        },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier.padding(5.dp),
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.background(Color(0xFFD7D7D7))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                if (type == "alert") Color.Red else Color(0xFF3FB41F)
                            )
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = title,
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight.Bold,
                            ),
                            color = if (type == "alert") Color.LightGray else Color.White
                        )

                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "",
                            tint = colorResource(R.color.white),
                            modifier = Modifier
                                .width(30.dp)
                                .height(30.dp)
                                .clickable {
                                    setShowDialog(false)
                                    if (type == "alert") {
                                        Log.d("TEST-Observer-type", "onDismissed: $type")
                                    } else {
                                        Log.d("TEST-Observer-type", "onDismissed: $type")
                                    }
                                }
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Column(
                        modifier = Modifier
                            .padding(20.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = message,
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewDialog() {
    val showDialog = remember { mutableStateOf(false) }
    val message =
        java.lang.StringBuilder("Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.")
    var onDialogDismissed = {

    }

    CustomDialog(title = "Test", setShowDialog = {
        showDialog.value = it
    }, type = "Message", message = message.toString())
}