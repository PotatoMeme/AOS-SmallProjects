package com.potatomeme.hilt_architecture.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.potatomeme.hilt_architecture.data.source.UserLocalDataSource
import com.potatomeme.hilt_architecture.ui.theme.SampleComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class UserInfoActivity : ComponentActivity() {

    @Inject
    lateinit var userLocalDataSource : UserLocalDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SampleComposeTheme {
                Surface {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            var token by remember { mutableStateOf("") }
                            LaunchedEffect(Unit){
                                launch {
                                    token = userLocalDataSource.getToken().orEmpty()
                                }
                            }
                            Text(text = "$token")
                            Spacer(modifier = Modifier.height(20.dp))
                            TextButton(onClick = {
                                lifecycleScope.launch {
                                    userLocalDataSource.clear()
                                    startActivity(Intent(this@UserInfoActivity, LoginActivity::class.java))
                                    finish()
                                }
                            }) {
                                Text(text = "Logout")
                            }
                        }

                    }
                }
            }
        }
    }
}