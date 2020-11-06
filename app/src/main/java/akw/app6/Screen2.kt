package akw.app6

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable

@Composable
fun Screen2() {
    Column {

        val user: User = AmbientUser.current

        Text(text = "Screen2")
        Text(text = "user: ${user.userName}")
    }
}