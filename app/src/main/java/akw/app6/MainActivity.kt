package akw.app6

import akw.app6.ui.IntuitTheme
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import androidx.ui.tooling.preview.Preview
import kotlinx.coroutines.flow.Flow
import ss.ssutil.VF


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val prefsRepo: PrefsRepo = PrefsRepo.getInstance(this)

        setContent {
            IntuitTheme {

                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colors.background,
                ) {
                    Main(prefsRepo.prefsFlow)
                }
            }
        }
    }
}


@Composable
fun Main(prefsFlow: Flow<Prefs>) {

    val prefsState: State<Prefs?> = prefsFlow.collectAsState(initial = Prefs())
    val prefs: Prefs? = prefsState.value

    if (prefs == null) {
        CircularProgressIndicator()
    } else {
        MainView(prefs)
    }

}

@Composable
fun MainView(prefs: Prefs) {

    val scaffoldState = rememberScaffoldState()
    val navController: NavHostController = rememberNavController()

    fun nav(route: Route) {
        navController.navigate(route.name)
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text("Intuit") },
                navigationIcon = {
                    IconButton(onClick = { scaffoldState.drawerState.open() }) { Icon(Icons.Filled.Menu) }
                },
                actions = {
                    IconButton(onClick = {}) { Icon(Icons.Default.AccountBox) }
                    IconButton(onClick = {}) { Icon(Icons.Default.Search) }
                }
            )
        },
        drawerContent = {
            DrawerContent(navController, prefs) { scaffoldState.drawerState.close() }
        },
        bottomBar = { BottomNav(navController) }
    ) {
        NavHost(navController = navController, startDestination = Route.S1.name) {
            composable(Route.S1.name) { Screen1() }
            composable(Route.S2.name) { Screen2() }
            composable(Route.Login.name) { LoginScreen() }
            composable(Route.Repos.name) { ReposScreen(onRowClick = { nav(Route.S2) }) }
            composable(Route.Repo.name) { RepoScreen() }
            composable(Route.Prefs.name) { PrefScreen() }
            composable(Route.Res.name) { ResScreen() }
            composable(Route.Blackjack.name) { BlackjackScreen() }
        }
    }
}


@Composable
fun BottomNav(controller: NavHostController) {
    BottomNavigation {
        Route.values().forEach {
            if (it.mainMenu) {
                BottomNavItem(controller, it)
            }
        }
    }
}

@Composable
fun BottomNavItem(controller: NavHostController, route: Route) {
    val current: CharSequence? = controller.currentDestination?.label
    val name = route.name
    val isCurrent = name == current
    BottomNavigationItem(
        label = { Text(name) },
        icon = route.icon,
        selected = isCurrent,
        onClick = {
            controller.popBackStack(controller.graph.startDestination, false)
            if (!isCurrent) {
                controller.navigate(name)
            }
        })
}

@Composable
fun DrawerContent(controller: NavHostController, prefs: Prefs, closeDrawer: VF) {
    Column(horizontalAlignment = Alignment.Start) {
        Route.values().forEach {
            if (it.mainMenu) {
                DrawerItem(controller, prefs, it, closeDrawer)
            }
        }
    }

}

@Composable
fun DrawerItem(controller: NavHostController, prefs: Prefs, route: Route, closeDrawer: VF) {
    val current: CharSequence? = controller.currentDestination?.label
    val name = route.name
    val isCurrent: Boolean = name == current
    val fontWeight: FontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal

    val onClick = {
        closeDrawer()
        if (!isCurrent) {
            controller.navigate(name)
        }
    }

    val skip: Boolean =
        (route == Route.S1 && !prefs.showScreen1) || (route == Route.S2 && !prefs.showScreen2)

    if (!skip) {
        Row {
            route.icon
            TextButton(onClick = onClick) {
                Text(name, fontWeight = fontWeight)
            }

        }
    }


}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    IntuitTheme {
        MainView(Prefs())
    }
}