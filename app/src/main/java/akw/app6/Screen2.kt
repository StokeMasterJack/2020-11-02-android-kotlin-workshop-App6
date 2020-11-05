package akw.app6

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable

@Composable
fun Screen2(onNav: (screen: String) -> Unit) {
    Column {
//        Head(onNav = onNav)
        Text(text = "Screen2")
    }
}