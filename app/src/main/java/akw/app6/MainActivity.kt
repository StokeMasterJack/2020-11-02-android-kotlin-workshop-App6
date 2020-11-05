package akw.app6

import akw.app6.ui.App6Theme
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import androidx.ui.tooling.preview.Preview

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App6Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colors.background,
                    modifier = Modifier.padding(20.dp)
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
    val xx = remember { mutableStateOf(1) }

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
        NavHost(navController = nav, startDestination = "Screen1") {
            composable("Screen1") { Screen1(onNav) }
            composable("Screen2") { Screen2(onNav) }
            composable("Screen3") { Screen3(onNav) }
            composable("Login") { LoginScreen() }
        }
    }
}

@Composable
fun VSpace(preferredHeight: Int = 10) {
    Spacer(modifier = Modifier.preferredHeight(preferredHeight.dp))
}

@Composable
fun HSpace(preferredWidth: Int = 10) {
    Spacer(modifier = Modifier.preferredWidth(preferredWidth.dp))
}

@Composable
fun Head(onNav: (screen: String) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Button(onClick = { onNav("Screen1") }) {
            Text(
                text = "Screen1"
            )
        }
        HSpace()
        Button(onClick = { onNav("Screen2") }) {
            Text(
                text = "Screen2"
            )
        }
        HSpace()
        Button(onClick = { onNav("Screen3") }) {
            Text(
                text = "Screen3"
            )
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