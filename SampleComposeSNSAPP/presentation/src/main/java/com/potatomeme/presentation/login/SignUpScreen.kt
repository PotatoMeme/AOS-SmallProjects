package com.potatomeme.presentation.login

import androidx.compose.foundation.background
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
fun SignUpScreen(
    id: String,
    username: String,
    password: String,
    checkPassword: String,

    onIdChange: (String) -> Unit,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onCheckPasswordChange: (String) -> Unit,

    onSignUpClick: () -> Unit,
) {
    Surface {
        Column(
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
                    text = "Create an account",
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
                    text = "Username",
                    style = MaterialTheme.typography.labelLarge
                )
                SampleTextField(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth(),
                    value = username,
                    onValueChange = onUsernameChange
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
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = "Repeat password",
                    style = MaterialTheme.typography.labelLarge
                )
                SampleTextField(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth(),
                    value = checkPassword,
                    onValueChange = onCheckPasswordChange
                )
                SampleButton(
                    modifier = Modifier
                        .padding(top = 32.dp)
                        .fillMaxWidth()
                        .height(48.dp),
                    onClick = onSignUpClick,
                    text = "SignUp"
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Preview
@Composable
fun SignUpScreenPreview() {
    PresentationTheme {
        SignUpScreen(
            "sampleId",
            "sampleUserName",
            "samplePassword",
            "sampleCheckPassword",
            {},
            {},
            {},
            {},
            {},
        )
    }
}