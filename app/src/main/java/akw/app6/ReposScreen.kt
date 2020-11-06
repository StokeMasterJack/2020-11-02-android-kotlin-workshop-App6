package akw.app6

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.chrisbanes.accompanist.coil.CoilImage
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

data class GHUser(var id: Int, var login: String, var url: String, val avatar_url: String?)
data class Repo(
    var id: Int,
    var name: String,
    var full_name: String,
    var url: String,
    var description: String?,
    var owner: GHUser

)

interface GithubService {
    @GET("users")
    suspend fun fetchUsers(): List<GHUser>

    @GET("repositories")
    suspend fun fetchRepos(): List<Repo>

}

interface UserService {
    @GET("users.json")
    suspend fun fetchUsers(): List<User>

}

fun mkSampleDataRetrofit(): Retrofit {
    return mkRetrofit("https://storage.googleapis.com/smart-soft-sample-data/")
}

fun mkGithubRetrofit(): Retrofit {
    return mkRetrofit("https://api.github.com/")
}

fun mkGithubService(): GithubService {
    val retro = mkGithubRetrofit()
    return retro.create(GithubService::class.java)
}


private fun mkRetrofit(baseUrl: String): Retrofit {
    val httpClient = OkHttpClient.Builder().build()
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient)
        .build()
}


fun mkUsersService(): GithubService {
    val retro = mkGithubRetrofit()
    return retro.create(GithubService::class.java)
}

@Composable
fun ReposScreen(onRowClick: (Repo) -> Unit = {}) {

    val (uiState, setUIState) = remember { mutableStateOf<UIState<List<Repo>>>(UIState.NotStarted) }
    val (requestCount, setRequestCount) = remember { mutableStateOf(0) }  //not needed

    onDispose(callback = {
        Log.w("ReposScreen", "onDispose")
    })


    onActive(callback = {
        Log.w("ReposScreen", "onActive")
    })

    LaunchedTask {
        try {
            setUIState(UIState.Loading)
            val githubService = mkGithubService()
            val repos = githubService.fetchRepos()
            setRequestCount(requestCount + 1)
            setUIState(UIState.Success(repos))
        } catch (e: Exception) {
            setUIState(UIState.Error(e))
        }
    }

    when (uiState) {
        is UIState.NotStarted -> Text("")
        is UIState.Loading -> Box(
            alignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) { CircularProgressIndicator(modifier = Modifier) }
        is UIState.Error -> ErrorPanel(uiState.error)
        is UIState.Success -> ReposPanel(uiState.data, onRowClick)
    }
}


@Composable
fun ReposPanel(repos: List<Repo>, onRowClick: (Repo) -> Unit = {}) {
    //ScrollableColumn
    LazyColumnFor(repos) {
        RepoRow(repo = it, onRepoClick = onRowClick)
        Divider()
    }
}

@Composable
fun ErrorPanel(e: Throwable) {
    Text("Error: ${e.message}")
}

@Composable
private fun RepoImage(imageUrl: String?) {

    val imageModifier = Modifier.preferredSize(46.dp).clip(shape = CircleShape)
    val contentScale = ContentScale.Crop

    @Composable
    fun PlaceHolder() {
        Image(
            asset = imageResource(id = R.drawable.placeholder_1_1),
            contentScale = contentScale,
            modifier = imageModifier
        )
    }



    if (imageUrl != null) {
        CoilImage(
            data = imageUrl,
            contentScale = contentScale,
            modifier = imageModifier,
            loading = { PlaceHolder() }
        )
    } else {
        PlaceHolder()
    }

}

@Composable
private fun RepoRow(repo: Repo, onRepoClick: (Repo) -> Unit) {


    Row(
        modifier = Modifier.clickable(onClick = { onRepoClick(repo) }).fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        RepoImage(repo.owner.avatar_url)

        Column(
            modifier = Modifier.padding(start = 12.dp, bottom = 10.dp)
                .preferredHeightIn(min = 46.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,

            ) {
            Text(
                text = repo.full_name,
                maxLines = 1,
                style = MaterialTheme.typography.subtitle1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = repo.description ?: "",
                maxLines = 2,
                style = MaterialTheme.typography.body2,
                overflow = TextOverflow.Ellipsis
            )

        }

    }
}
//}