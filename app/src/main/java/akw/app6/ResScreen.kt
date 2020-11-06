package akw.app6

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.VectorAsset
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource

@Composable
fun ResScreen() {

    val appName: String = stringResource(R.string.app_name)
    val title1: String = stringResource(R.string.title1)
    val gitHub: VectorAsset = vectorResource(R.drawable.ic_github)

    Column {
        Icon(gitHub)
        Text(text = appName)
    }
}