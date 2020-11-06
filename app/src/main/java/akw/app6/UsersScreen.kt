package akw.app6

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedTask
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

data class GHUser(var id: Int, var login: String, var url: String)

interface UsersService {
    @GET("users")
    suspend fun fetchUsers(): List<GHUser>

}

private fun mkRetrofit(baseUrl: String): Retrofit {
    val httpClient = OkHttpClient.Builder().build()
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient)
        .build()
}


fun mkUsersService(): UsersService {
    val retro = mkRetrofit("https://api.github.com/")
    return retro.create(UsersService::class.java)
}

@Composable
fun UsersScreen() {

    val (uiState, setUIState) = remember { mutableStateOf<UIState<List<GHUser>>>(UIState.NotStarted) }
    val (requestCount, setRequestCount) = remember { mutableStateOf(0) }  //not needed

    LaunchedTask(1) {
        try {
            setUIState(UIState.Loading)
            val usersService = mkUsersService()
            val users = usersService.fetchUsers()
            setRequestCount(requestCount + 1)
            setUIState(UIState.Success(users))
        } catch (e: Exception) {
            setUIState(UIState.Error(e))
        }
    }

    when (uiState) {
        is UIState.NotStarted -> Text("")
        is UIState.Loading -> CircularProgressIndicator(modifier = Modifier)
        is UIState.Error -> ErrorPanel(uiState.error)
        is UIState.Success -> UsersPanel(uiState.data)
    }
}


@Composable
fun UsersPanel(users: List<GHUser>) {
    ScrollableColumn {
        users.forEach {
            Text("${it.id}  ${it.login}  ${it.url}")
        }
    }
}

@Composable
fun ErrorPanel(e: Throwable) {
    Text("Error: ${e.message}")
}

