package akw.app6

import akw.app6.ui.App6Theme
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Text
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import androidx.ui.tooling.preview.Preview

enum class Screen {
    Screen1,
    Screen2,
    Screen3,
    Login,
    Users
}

sealed class Route {
    object Screen1 : Route()
    object Screen2 : Route()
    object Screen3 : Route()
    object Login : Route()
    object Users : Route()
    data class User(val userId: Int) : Route()
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App6Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colors.background,
                ) {
                    MainNav()
                }
            }
        }
    }
}


@Composable
fun MainNav() {

    val scaffoldState = rememberScaffoldState()
    val nav: NavHostController = rememberNavController()

    val onNav: (route: String) -> Unit = {
        nav.navigate(it)
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text("App 6") },
                navigationIcon = {
                    IconButton(onClick = { scaffoldState.drawerState.open() }) {
                        Icon(
                            Icons.Filled.Menu
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {}) { Icon(Icons.Default.Favorite) }
                    IconButton(onClick = {}) { Icon(Icons.Default.Build) }
                    IconButton(onClick = {}) { Icon(Icons.Default.AccountBox) }
                }
            )
        },
        drawerContent = {
            TextButton(onClick = { nav.navigate("Screen1"); scaffoldState.drawerState.close() }) {
                Text(
                    "Screen1"
                )
            }
            TextButton(onClick = { nav.navigate("Screen2"); scaffoldState.drawerState.close() }) {
                Text(
                    "Screen2"
                )
            }
            TextButton(onClick = { nav.navigate("Screen3"); scaffoldState.drawerState.close() }) {
                Text(
                    "Screen3"
                )
            }
            TextButton(onClick = { nav.navigate("Login"); scaffoldState.drawerState.close() }) {
                Text(
                    "Login"
                )
            }
            TextButton(onClick = { nav.navigate(Screen.Users.name); scaffoldState.drawerState.close() }) {
                Text(
                    "Users"
                )
            }
        },
        bottomBar = {
            BottomNavigation {
                BottomNavigationItem(
                    label = { Text("S1") },
                    icon = { Icon(Icons.Filled.Favorite) },
                    selected = false,
                    onClick = { nav.navigate("Screen1") })
                BottomNavigationItem(
                    label = { Text("S2") },
                    icon = { Icon(Icons.Filled.Build) },
                    selected = false,
                    onClick = { nav.navigate("Screen2") })
                BottomNavigationItem(
                    label = { Text("S3") },
                    icon = { Icon(Icons.Filled.AccountBox) },
                    selected = false,
                    onClick = { nav.navigate("Screen3") })
            }
        }
    ) {
        NavHost(navController = nav, startDestination = Screen.Users.name) {
            composable(Screen.Screen1.name) { Screen1(onNav) }
            composable(Screen.Screen2.name) { Screen2(onNav) }
            composable(Screen.Screen3.name) { Screen3(onNav) }
            composable(Screen.Login.name) { LoginScreen() }
            composable(Screen.Users.name) { UsersScreen() }
//            composable(Screen.User.name) { UserScreen(userId = 432) }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    App6Theme {
        MainNav()
    }
}