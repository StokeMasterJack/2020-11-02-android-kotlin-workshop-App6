package akw.app6

import android.util.Log
import androidx.compose.foundation.Text
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedTask
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.delay

suspend fun loadUsers(requestCount: Int = 0): List<User> {
    Log.w("UsersScreen", "loadUsers")
    delay(3000)

    if (requestCount % 3 == 0 && requestCount != 0) {
        throw RuntimeException("Network error")
    }

    return listOf(
        User("dford", "David", "Ford"),
        User("kford", "Kellie", "Ford"),
        User("jblow", "Joe", "Blow")
    )
}


//loading, ErrorPanel, UsersPanel
@Composable
fun UsersScreen() {

    val (uiState, setUIState) = remember { mutableStateOf<UIState<List<User>>>(UIState.NotStarted) }
    val (requestCount, setRequestCount) = remember { mutableStateOf(0) }  //not needed

    LaunchedTask(1) {
        try {
            setUIState(UIState.Loading)
            val users = loadUsers(requestCount)
            setUIState(UIState.Success(users))
        } catch (e: Exception) {
            setUIState(UIState.Error(e))
        }
    }

    when (uiState) {
        is UIState.NotStarted -> Text("NotStarted")
        is UIState.Loading -> CircularProgressIndicator()
        is UIState.Error -> ErrorPanel(uiState.error)
        is UIState.Success -> UsersPanel(uiState.data)
    }
}


@Composable
fun UsersPanel(users: List<User>) {
    Text("UsersPanel: $users")

//    ScrollableColumn {
//        users.forEach {
//            Text("${}")
//        }
//    }
}

@Composable
fun ErrorPanel(e: Throwable) {
    Text("Error: ${e.message}")


}

