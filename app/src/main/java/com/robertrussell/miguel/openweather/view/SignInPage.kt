package com.robertrussell.miguel.openweather.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.robertrussell.miguel.openweather.R
import com.robertrussell.miguel.openweather.model.SignInUIEvents
import com.robertrussell.miguel.openweather.model.api.Response
import com.robertrussell.miguel.openweather.utils.Constants
import com.robertrussell.miguel.openweather.view.navigation.Navigation
import com.robertrussell.miguel.openweather.view.navigation.Pages
import com.robertrussell.miguel.openweather.viewmodel.SignViewModel

@Composable
fun SignInPage(viewModel: SignViewModel) {

    val context = LocalContext.current

    val result = viewModel.result.collectAsState()
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
                    "Sign in success",
                    Toast.LENGTH_LONG
                ).show()
                Navigation.navigateTo(Pages.HomeScreen)
            }
            viewModel.clearSignInValues()
        }

        is Response.Failure -> {
            Toast.makeText(
                context,
                "Invalid username and/or password!",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxHeight(1f)
            .fillMaxWidth(1f)
    ) {
        Column(
            modifier = with(Modifier) {
                fillMaxSize()
                    .paint(
                        painterResource(id = R.drawable.bg_main_new),
                        contentScale = ContentScale.FillHeight
                    )
            }, verticalArrangement = Arrangement.Bottom
        ) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(28.dp, 28.dp, 0.dp, 0.dp))
                    .fillMaxWidth()
                    .fillMaxSize(0.55f)
                    .background(Color.White.copy(alpha = 0.65f))
                    .padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                BasicTextView(text = "Welcome!", modifier = Modifier)
                HeaderTextView(text = "Login to continue", modifier = Modifier)

                Spacer(modifier = Modifier.height(24.dp))
                BasicEditText(Constants.SIGNIN, labelValue = "Username", viewModel)

                Spacer(modifier = Modifier.height(8.dp))
                PasswordEditText(Constants.SIGNIN, labelValue = "Password", viewModel)

                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        viewModel.onSignInEvent(SignInUIEvents.SignInButtonClicked)
                    }, shape = RoundedCornerShape(16.dp), modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight(), colors = ButtonDefaults.buttonColors(
                        Color(0xFF333333)
                    )
                ) {
                    Text(
                        text = "Sign In",
                        color = Color(0xFFD7D7D7),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                Header2TextView(text = "OR", modifier = Modifier)

                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        Navigation.navigateTo(Pages.SignUpScreen)
                    }, shape = RoundedCornerShape(16.dp), modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight(), colors = ButtonDefaults.buttonColors(
                        Color(0xFF333333)
                    )
                ) {
                    Text(
                        text = "Create an Account",
                        color = Color(0xFFD7D7D7),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }
    }
}
