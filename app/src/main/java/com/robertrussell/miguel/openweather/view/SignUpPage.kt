package com.robertrussell.miguel.openweather.view

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
import androidx.compose.runtime.collectAsState
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
import com.robertrussell.miguel.openweather.model.api.Response
import com.robertrussell.miguel.openweather.utils.Constants
import com.robertrussell.miguel.openweather.view.navigation.Navigation
import com.robertrussell.miguel.openweather.view.navigation.Pages
import com.robertrussell.miguel.openweather.viewmodel.SignViewModel

@Composable
fun SignUpPage(viewModel: SignViewModel) {

    val context = LocalContext.current

    val result = viewModel.resultSignUp.collectAsState()
    when (result.value) {
        is Response.Loading -> {
            LoadingAnimation(
                modifier = Modifier
                    .padding(30.dp),
                circleSize = 25.dp
            )
        }

        is Response.Success -> {
            val _result = result.value as Response.Success
            if (_result.data != null) {
                Toast.makeText(
                    context,
                    "Account has been successfully created.",
                    Toast.LENGTH_LONG
                ).show()
                Navigation.navigateTo(Pages.SignInScreen)
            }
            viewModel.clearSignUpValues()
        }

        is Response.Failure -> {
            val _result = result.value as Response.Failure
            Toast.makeText(
                context,
                _result.e?.message,
                Toast.LENGTH_LONG
            ).show()

        }
    }

    Surface(
        modifier = Modifier
            .fillMaxHeight(1f)
            .fillMaxWidth(1f)
    ) {
        var signUpOnClick = {
            viewModel.onSignUpEvent(SignUpUIEvent.SignUpButtonClicked)
        }

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
    }
}
