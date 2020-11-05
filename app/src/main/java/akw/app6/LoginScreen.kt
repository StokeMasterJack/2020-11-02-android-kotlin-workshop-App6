package akw.app6

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Card
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

data class Credentials(val userName: String = "", val password: String = "")

@Composable
fun LoginScreen() {

    val (credentials, setCredentials) = remember { mutableStateOf(Credentials()) }

    Card(backgroundColor = Color.White) {
//        Text(text = "LoginScreen")
        Column {
            TextField(
                label = { Text("Username") },
                value = credentials.userName,
                onValueChange = { setCredentials(credentials.copy(userName = it)) })

            TextField(
                label = { Text("Password") },
                value = credentials.password,
                onValueChange = { setCredentials(credentials.copy(password = it)) })
        }

    }
}