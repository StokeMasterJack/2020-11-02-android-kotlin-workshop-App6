package akw.app6

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

data class Credentials(val userName: String = "", val password: String = "")

@Composable
fun LoginScreen() {

    val (credentials, setCredentials) = remember { mutableStateOf(Credentials()) }

    Card(backgroundColor = Color.Yellow, modifier = Modifier.fillMaxSize().padding(all = 20.dp)) {
//        Text(text = "LoginScreen")
        Column(modifier = Modifier.fillMaxWidth().padding(all = 20.dp)) {
//            VSpace(20)
            TextField(
                label = { Text("Username") },
                value = credentials.userName,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { setCredentials(credentials.copy(userName = it)) })

            VSpace(20)

            TextField(
                visualTransformation = PasswordVisualTransformation(),
                label = { Text("Password") },
                value = credentials.password,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { setCredentials(credentials.copy(password = it)) })

            VSpace(20)

            Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Login")
            }

            VSpace(40)

            TextButton(onClick = {}, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(text = "Forgot Password?")
            }
        }

    }
}