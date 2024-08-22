package com.potatomeme.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import com.potatomeme.domain.usecase.login.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.annotation.concurrent.Immutable
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
) : ViewModel(),
    ContainerHost<SignUpState, SignUpSideEffect> {
    override val container: Container<SignUpState, SignUpSideEffect> = container(
        initialState = SignUpState(),
        buildSettings = {
            this.exceptionHandler = CoroutineExceptionHandler { _, throwable ->
                intent {
                    Log.d("TAG", "SignUpViewModel error ${throwable.message}")
                    postSideEffect(SignUpSideEffect.Toast(throwable.message.orEmpty()))
                }
            }
        }
    )

    fun onSignUpClick() = intent {
        val id: String = state.id
        val username: String = state.username
        val password: String = state.password
        val checkPassword: String = state.checkPassword

        if (password != checkPassword) {
            postSideEffect(SignUpSideEffect.Toast(message = "두 패스워드가 일치하지 않습니다."))
            return@intent
        }

        val isSuccessful = signUpUseCase(id, username, password).getOrThrow()

        if (isSuccessful) {
            postSideEffect(SignUpSideEffect.NavigateToLoginScreen)
            postSideEffect(SignUpSideEffect.Toast("회원가입에 성공하였습니다."))
        }
    }

    fun onIdChange(id: String) = intent {
        reduce {
            state.copy(id = id)
        }
    }

    fun onUsernameChange(username: String) = intent {
        reduce {
            state.copy(username = username)
        }
    }

    fun onPasswordChange(pw: String) = intent {
        reduce {
            state.copy(password = pw)
        }
    }

    fun onCheckPasswordChange(checkPassword: String) = intent {
        reduce {
            state.copy(checkPassword = checkPassword)
        }
    }

}

@Immutable
data class SignUpState(
    val id: String = "",
    val username: String = "",
    val password: String = "",
    val checkPassword: String = "",
)

sealed interface SignUpSideEffect {
    class Toast(val message: String) : SignUpSideEffect
    data object NavigateToLoginScreen : SignUpSideEffect
}