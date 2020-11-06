package akw.app6

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.vectorResource

enum class Route(val mainMenu: Boolean = true, val icon: @Composable () -> Unit) {
    S1(icon = { Icon(Icons.Outlined.Favorite) }),
    S2(icon = { Icon(Icons.Outlined.Search) }),
    Login(icon = { Icon(Icons.Outlined.AccountBox) }),
    Repos(icon = { Icon(vectorResource(R.drawable.ic_github)) }),
    Repo(mainMenu = false, icon = { Icon(vectorResource(R.drawable.ic_github)) }),
    Prefs(mainMenu = false, icon = { Icon(Icons.Outlined.Settings) }),
    Res(mainMenu = false, icon = { Icon(Icons.Outlined.Place) }),
    Blackjack(mainMenu = true, icon = { Icon(Icons.Outlined.Add) }),
}