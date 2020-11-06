package akw.app6

import android.util.Log
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.state
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

data class Credentials(val userName: String = "", val password: String = "")

data class User(
    val userName: String = "",
    val firstName: String = "",
    val lastName: String = ""


)

//enum class UIState {
//    Waiting,
//    Success,
//    Error
//}

object Po {
    val x = 33
    val y = 44

    fun ff() {

    }
}

val users: List<User> = listOf(
    User("dford", "David", "Ford"),
    User("kford", "Kellie", "Ford"),
    User("jblow", "Joe", "Blow")
)


suspend fun onLoginSubmit(credentials: Credentials): User {
    Log.w("Login", "onLoginSubmit: ${credentials}")
    delay(3000)
    if (credentials.userName == "err") {
        throw RuntimeException("Network Error")
    }
    return User(userName = "dford", firstName = "Dave", lastName = "Ford")
}

/*

V = F(M)
 */
@Composable
fun LoginScreen() {

    val (uiState, setUIState) = remember { mutableStateOf<UIState<User>>(UIState.NotStarted) }
    val (credentials, setCredentials) = remember { mutableStateOf(Credentials()) }

    val (reqCount, setReqCount) = remember { mutableStateOf(0) }


    val all1 = "${credentials.userName} ${credentials.password}"
    val all2 = remember { "${credentials.userName} ${credentials.password}" }
    val all3 = remember(credentials.userName,credentials.password) { "${credentials.userName} ${credentials.password}" }

//    DisposableEffect(reqCount) {
//
//        //fasfasf
//        //fasfasf
//
//        onDispose {
//
//        }
//
//    }

    LaunchedTask(reqCount) {
        if (reqCount != 0 && uiState != UIState.Loading) {
            try {
                setUIState(UIState.Loading)
                val user = onLoginSubmit(credentials) // takes some time
                setUIState(UIState.Success(user))
            } catch (e: Exception) {
                setUIState(UIState.Error(e))
            }
        }
    }

    Card(
        backgroundColor = Color.Yellow,
        modifier = Modifier.fillMaxWidth().padding(top = 20.dp, start = 20.dp, end = 20.dp)
    ) {

        Column(modifier = Modifier.fillMaxWidth().padding(all = 20.dp)) {

            VSpace(20)
            Text("reqCount: $reqCount")
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

            Button(
                enabled = uiState != UIState.Loading,
                onClick = { setReqCount(reqCount + 1) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Login")
            }

            VSpace(40)

            TextButton(
                onClick = { },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Forgot Password?")
            }

            VSpace()

            Card(backgroundColor = Color.Cyan) {
                when (uiState) {
                    is UIState.NotStarted -> Text("NotStarted")
                    is UIState.Loading -> CircularProgressIndicator()
                    is UIState.Error -> Text(text = "Problem: ${uiState.error.message}")
                    is UIState.Success -> Text(text = "Success: ${uiState.data.firstName}")
                }
            }


        }

    }
}