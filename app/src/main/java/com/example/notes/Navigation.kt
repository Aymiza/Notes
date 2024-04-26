package com.example.notes

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.notes.home.Home
import com.example.notes.login.LoginScreen
import com.example.notes.login.LoginViewModel
import com.example.notes.login.SignUpScreen

enum class LoginRoutes{
    Signup,
    Signin
}

enum class HomeRoutes{
    Home,
    Detail
}
enum class  NestedRoutes{
    Main,
    Login
}
@Composable
fun Navigation(
navController: NavHostController = rememberNavController(),
loginViewModel: LoginViewModel

) {
   NavHost(navController = navController,
       startDestination = LoginRoutes.Signin.name
   ) {







        composable(route = LoginRoutes.Signin.name){
            LoginScreen(onNavToHomePage = {
                navController.navigate(NestedRoutes.Main.name){
                    launchSingleTop = true
                    popUpTo(route = LoginRoutes.Signin.name){

                        inclusive = true
                    }

                }

            }, loginViewModel = loginViewModel
            ) {
                    navController.navigate(LoginRoutes.Signup.name){
                        launchSingleTop = true
                        popUpTo(LoginRoutes.Signin.name){
                            inclusive = true
                        }
                    }
            }
        }

        composable(route = LoginRoutes.Signup.name){
            SignUpScreen(onNavToHomePage = {
                navController.navigate(NestedRoutes.Main.name){
                popUpTo(LoginRoutes.Signup.name){
                    inclusive = true
                }
            }

            }, loginViewModel = loginViewModel
            )

            {

                navController.navigate(LoginRoutes.Signin.name)
            }
        }


    }

    fun navigation(startDestination: String, route: String) {
        TODO("Not yet implemented")
    }

    fun NavGraphBuilder.authGraph
                (navController: NavHostController,
                 loginViewModel: LoginViewModel,
    ) {
        navigation(
            startDestination = LoginRoutes.Signin.name,
            route = NestedRoutes.Login.name
        ){

        }
    }


}


