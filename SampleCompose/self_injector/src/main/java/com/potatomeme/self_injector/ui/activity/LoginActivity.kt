package com.potatomeme.self_injector.ui.activity

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
import com.potatomeme.self_injector.App
import com.potatomeme.self_injector.LoginContainer
import com.potatomeme.self_injector.ui.screen.LoginScreen
import com.potatomeme.self_injector.ui.state.UserState
import com.potatomeme.self_injector.ui.theme.SampleComposeTheme
import com.potatomeme.self_injector.ui.viewmodel.LoginViewModel
import kotlinx.coroutines.launch

class LoginActivity : ComponentActivity() {
    private val container by lazy { (this.application as App).appContainer }

    private val viewModel: LoginViewModel by viewModels {
        container.loginContainer!!.createLoginViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        container.loginContainer = LoginContainer(container)
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

    override fun onDestroy() {
        container.loginContainer = null
        super.onDestroy()
    }
}