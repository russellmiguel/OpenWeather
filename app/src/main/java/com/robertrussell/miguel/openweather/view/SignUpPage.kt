package com.robertrussell.miguel.openweather.view

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.robertrussell.miguel.openweather.R
import com.robertrussell.miguel.openweather.model.SignUpUIEvent
import com.robertrussell.miguel.openweather.utils.Constants
import com.robertrussell.miguel.openweather.view.navigation.Navigation
import com.robertrussell.miguel.openweather.view.navigation.Pages
import com.robertrussell.miguel.openweather.viewmodel.SignViewModel
import io.reactivex.Observer


@Composable
fun SignUpPage(viewModel: SignViewModel) {

    val context = LocalContext.current
    val showDialog = remember { mutableStateOf(false) }
    var dialogMessage = remember {
        mutableStateOf("")
    }

    Surface(
        modifier = Modifier
            .fillMaxHeight(1f)
            .fillMaxWidth(1f)
    ) {
        var signUpOnClick = {
            viewModel.onSignUpEvent(SignUpUIEvent.SignUpButtonClicked)
        }

        if (showDialog.value) {
            CustomDialog(
                title = "Sign Up!",
                setShowDialog = {
                    showDialog.value = it
                },
                type = if (dialogMessage.value == "") {
                    "sign_up"
                } else {
                    "alert"
                },
                message = if (dialogMessage.value == "") "Successfully create a new account." else dialogMessage.value
            )
        }

        val status = viewModel.status.observeAsState()
        if (status.value == true) {
            Toast.makeText(context, "Account successfully created.", Toast.LENGTH_LONG).show()
            Navigation.navigateTo(Pages.SignInScreen)
        }
//        } else {
//            Toast.makeText(context, "Failed to create account, check credentials.", Toast.LENGTH_LONG).show()
//        }

//        val newUser by viewModel.newUserData.observeAsState()
//        Log.d("TEST-Observer", newUser.toString())
//        Log.d("TEST-Observer", "dialog: " + showDialog.value.toString())
//        Log.d("TEST-Observer", "errorMessage: " + newUser?.errorMessage.isNullOrEmpty().toString())
//
//        if (newUser?.userName?.equals("") == true) {
//            if (newUser?.errorMessage.isNullOrEmpty()) {
//                showDialog.value = true
//                dialogMessage.value = ""
//            } else {
//                Log.d("TEST-Observer", "ELSE: ${newUser?.errorMessage}")
//            }
//        }

        Column(
            modifier = with(Modifier) {
                fillMaxSize()
                    .paint(
                        painterResource(id = R.drawable.bg_main),
                        contentScale = ContentScale.FillHeight
                    )
            }, verticalArrangement = Arrangement.Bottom
        ) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(28.dp, 28.dp, 0.dp, 0.dp))
                    .fillMaxWidth()
                    .fillMaxSize(0.75f)
                    .background(Color.White.copy(alpha = 0.65f))
                    .padding(28.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                BasicTextView(text = "Hey there,", modifier = Modifier)
                HeaderTextView(text = "Create an Account", modifier = Modifier)

                Spacer(modifier = Modifier.height(24.dp))
                BasicEditText(Constants.SIGNUP, labelValue = "Name", viewModel)
                Spacer(modifier = Modifier.height(8.dp))
                BasicEditText(Constants.SIGNUP, labelValue = "Email", viewModel)
                Spacer(modifier = Modifier.height(8.dp))
                BasicEditText(Constants.SIGNUP, labelValue = "Username", viewModel)
                Spacer(modifier = Modifier.height(8.dp))
                PasswordEditText(Constants.SIGNUP, labelValue = "Password", viewModel)


                Spacer(modifier = Modifier.height(16.dp))
                BasicButton(labelValue = "Sign Up", signUpOnClick)
            }
        }
    }

    BackHandler {
        viewModel.clearSignUpValues()
        Navigation.navigateTo(Pages.SignInScreen)
        showDialog.value = false
    }
}
