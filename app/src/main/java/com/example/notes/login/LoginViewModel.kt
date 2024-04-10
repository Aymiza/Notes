package com.example.notes.login

import com.example.notes.repository.AuthRepository
import androidx.lifecycle.ViewModel

class LoginViewModel:ViewModel(
    private val repository: Au
): ViewModel(){
    val currentUser = repository.currentUser

    valhasUser: Boolean
        get() = repository.hasUser()

    var loginUiState by mutableStateOf(LoginUiState())
    private set

            fun onUserNameChange (userName: String){
                loginUiState = loginUiState.copy(userName = userName)
            }
    fun onPasswordNameChange (password: String){
        loginUiState = loginUiState.copy(password = userName)
    }
    fun onUserNameChangeSignup (userNameSignup: String){
        loginUiState = loginUiState.copy(userNameSignup = userName)
    }
    fun onPasswordChangeSignup (passwordSignup: String){
        loginUiState = loginUiState.copy(passwordSignup = userName)
    }
    fun onConfirmPasswordChange (password: String){
        loginUiState = loginUiState.copy(comfirmpasswordSignup = userName)
    }

    private fun validateLoginForm() =
        loginUiState.userName.isBlank() &&
                loginUiState.password.isNotBlank()
                loginUiState.confirmpasswordSignup.isNotBlank()


    fun createUser(context: Context) = viewModelScope.launch { this: CoroutineScope
        try {
            if (validateSignupForm()){
                throw IllegalArgumentException("email and password can not be empty")
            }
            loginUiState = loginUiState.copy(isloading = true)
            if (loginUiState.passwordSignuo !=
                loginUiState.comfirmPasswordSignUp){
                throw IllegalArgumentException(
                    "Password do not match"
                )
            }

            loginUiState = loginUiState.copy(signUpError = null)
            repository.createUser(
                loginUiState.userNameSignUp,
                loginUiState.passwordSignUp
            ){
                isSuccesful ->
                if (isSuccesful){
                    Toast.makeText(context, text"success Login", Toast.LENGHT_SHORT).show()
                    loginUiState = loginUiState.copy(isSucceesLogin = true)
                }else{
                    Toast.makeText(context, text"Failed Login", Toast.LENGHT_SHORT).show()
                    loginUiState = loginUiState.copy(isSucceesLogin = false)
                }
            }
                catch (e:Exception){
                    loginUiState = loginUiState.copy(signUpError = e.localizedMessage)
                    e.printStackTrace()
                }finally {
                    loginUiState =  loginUiState.copy(isLoading = false)
                }
        }
    }
    fun loginUser(context: Context) = viewModelScope.launch { this: CoroutineScope
        try {
            if (!validateLoginForm()){
                throw IllegalArgumentException("email and password can not be empty")
            }
            loginUiState = loginUiState.copy(isloading = true)
            loginUiState = loginUiState.copy(loginError = null)
            repository.login(
                loginUiState.userName,
                loginUiState.password
            ){
                    isSuccesful ->
                if (isSuccesful){
                    Toast.makeText(context, text"success Login", Toast.LENGHT_SHORT).show()
                    loginUiState = loginUiState.copy(isSucceesLogin = true)
                }else{
                    Toast.makeText(context, text"Failed Login", Toast.LENGHT_SHORT).show()
                    loginUiState = loginUiState.copy(isSucceesLogin = false)
                }

            }
            catch (e:Exception){
                loginUiState = loginUiState.copy(loginError = e.localizedMessage)
                e.printStackTrace()
            }finally {
                loginUiState =  loginUiState.copy(isLoading = false)
            }
        }
    }
}
}
data class LoginUiState(
    val userName:String = "",
    val password:String ="",
    val userNameSignup:String = "",
    val passwordSignup:String ="",
    val comfirmpasswordSignup:String ="",
    val isLoading : Boolean = false,
    val isSuccessLogin:Boolean = false,
    val signUpError:String? = null,
    val loginError:String? = null,

)