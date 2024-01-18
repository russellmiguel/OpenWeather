package com.robertrussell.miguel.openweather.view

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.robertrussell.miguel.openweather.R
import com.robertrussell.miguel.openweather.utils.Constants
import com.robertrussell.miguel.openweather.view.navigation.Navigation
import com.robertrussell.miguel.openweather.view.navigation.Pages
import com.robertrussell.miguel.openweather.viewmodel.SignViewModel


@Composable
fun SignUpPage(viewModel: SignViewModel = viewModel()) {
    Surface(
        modifier = Modifier
            .fillMaxHeight(1f)
            .fillMaxWidth(1f)
    ) {
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
                BasicButton(labelValue = "Sign Up") { }
            }
        }
    }

    BackHandler {
        Navigation.navigateTo(Pages.SignInScreen)
    }
}


@Preview
@Composable
fun SignUpScreenPreview() {
    SignUpPage()
}