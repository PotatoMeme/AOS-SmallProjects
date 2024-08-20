package com.potatomeme.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.potatomeme.presentation.component.SampleButton
import com.potatomeme.presentation.component.SampleTextField
import com.potatomeme.presentation.theme.PresentationTheme


@Composable
fun LoginScreen(
    id: String,
    password: String,
    onIdChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,

    onLoginClick: () -> Unit,
    onSignUpClick: () -> Unit,
) {
    Surface {
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.padding(top = 48.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Connected",
                    style = MaterialTheme.typography.displaySmall,
                )
                Text(
                    text = "Your favorite social network",
                    style = MaterialTheme.typography.labelSmall
                )
            }
            Column(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 16.dp),
            ) {
                Text(
                    modifier = Modifier.padding(top = 36.dp),
                    text = "Login",
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    modifier = Modifier.padding(top = 36.dp),
                    text = "Id",
                    style = MaterialTheme.typography.labelLarge
                )
                SampleTextField(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth(),
                    value = id,
                    onValueChange = onIdChange
                )
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = "Password",
                    style = MaterialTheme.typography.labelLarge
                )
                SampleTextField(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth(),
                    value = password,
                    onValueChange = onPasswordChange
                )
                SampleButton(
                    modifier = Modifier
                        .padding(top = 32.dp)
                        .fillMaxWidth()
                        .height(48.dp),
                    onClick = onLoginClick,
                    text = "Login"
                )
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 24.dp)
                        .clickable(onClick = onSignUpClick)
                ) {
                    Text(text = "Don't have an account?")
                    Text(text = "Sign up", color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    PresentationTheme {
        LoginScreen(
            "testId",
            "testPw",
            {},
            {},
            {},
            {},
        )
    }
}