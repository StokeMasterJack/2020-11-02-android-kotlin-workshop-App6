package akw.app6

import android.util.Log
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedTask
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import kotlinx.coroutines.delay

data class Credentials(val userName: String = "", val password: String = "")

data class User(
    val userName: String = "",
    val firstName: String = "",
    val lastName: String = ""


) {
    val fullName: String get() = "$firstName $lastName"

    override fun toString() = fullName
}

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

    Surface(color = Color.LightGray) {
        Card(
            backgroundColor = Color.White,
            modifier = Modifier.fillMaxSize().padding(all = 20.dp)
        ) {

            Column(modifier = Modifier.fillMaxWidth().padding(all = 20.dp)) {
                VSpace(40)
                Text(
                    text = "Intuit",
                    fontSize = 10.em,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                VSpace(40)
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

                VSpace(40)

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

                Box(
                    modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)
                ) {
                    when (uiState) {
                        is UIState.NotStarted -> Text("")
                        is UIState.Loading -> CircularProgressIndicator()
                        is UIState.Error -> Text("Problem: ${uiState.error.message}")
                        is UIState.Success -> Text(" ${uiState.data}")
                    }
                }


            }

        }
    }
}