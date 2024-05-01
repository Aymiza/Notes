package com.example.notes

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.notes.detail.DetailScreen
import com.example.notes.detail.DetailViewModel
import com.example.notes.home.Home
import com.example.notes.home.HomeViewModel
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
    loginViewModel: LoginViewModel,
    detailViewModel: DetailViewModel,
    homeViewModel: HomeViewModel

) {
   NavHost(navController = navController,
       startDestination = NestedRoutes.Main.name
   ) {
       authGraph(navController, loginViewModel)
       homeGraph(navController = navController,
           detailViewModel,
           homeViewModel)
    }



}


fun NavGraphBuilder.authGraph(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
){
    navigation(
        startDestination = LoginRoutes.Signin.name,
        route = NestedRoutes.Login.name
    ){
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
}

fun NavGraphBuilder.homeGraph(
    navController: NavHostController,
    detailViewModel: DetailViewModel,
    homeViewModel: HomeViewModel
){
    navigation(startDestination = HomeRoutes.Home.name,
        route = NestedRoutes.Main.name,
        ){

        composable(HomeRoutes.Home.name)
        {
            Home(homeViewModel =homeViewModel ,
                onNoteClick = { noteId->
                    navController.navigate(
                        HomeRoutes.Detail.name + "?id=$noteId"
                     ){
                         launchSingleTop = true
                     }
                } ,
                navToDetailPage = {
                    navController.navigate(HomeRoutes.Detail.name)
                }
            ) {
                navController.navigate(NestedRoutes.Login.name){
                    launchSingleTop=true
                    popUpTo(0){
                        inclusive = true
                    }
                }
            }
        }

        composable(
            route = HomeRoutes.Detail.name + "?id={id}",
            arguments = listOf(navArgument("id"){
                type= NavType.StringType
                defaultValue=""

            })

        ){
            entry ->
            DetailScreen(
                detailViewModel = detailViewModel,
                noteId = entry.arguments?.getString("id") as String,
            ) {
                navController.navigateUp()
            }
        }
    }

}
