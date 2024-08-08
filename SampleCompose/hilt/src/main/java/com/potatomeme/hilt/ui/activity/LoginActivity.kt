package com.potatomeme.hilt.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.potatomeme.hilt.ui.screen.LoginScreen
import com.potatomeme.hilt.ui.state.UserState
import com.potatomeme.hilt.ui.theme.SampleComposeTheme
import com.potatomeme.hilt.ui.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it.userState) {
                        UserState.NONE -> {
                            // nothing to do
                        }

                        UserState.FAILED -> {
                            Toast.makeText(this@LoginActivity, "로그인에 실패하였습니다", Toast.LENGTH_SHORT)
                                .show()
                        }

                        UserState.LOGGED_IN -> {
                            startActivity(Intent(this@LoginActivity, UserInfoActivity::class.java))
                            finish()
                        }
                    }
                }
            }
        }

        setContent {
            SampleComposeTheme {
                val uiState = viewModel.uiState.collectAsState().value
                LoginScreen(
                    id = uiState.id,
                    pw = uiState.pw,
                    onIdChange = viewModel::onIdChange,
                    onPwChange = viewModel::onPwChange,
                    onLoginClick = viewModel::login
                )
            }
        }
    }
}