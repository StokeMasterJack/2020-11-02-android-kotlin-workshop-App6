package akw.app6

import akw.app6.ui.IntuitTheme
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable

@Composable
fun Screen2() {
    Column {

        val user: User = AmbientUser.current

//
//        val c = IntuitTheme.colors
//        val t = IntuitTheme.typography

        val (c, t) = IntuitTheme

        Text(text = "Screen2", style = t.h1)
        Text(text = "user: ${user.userName}", color = c.primaryVariant)
    }
}