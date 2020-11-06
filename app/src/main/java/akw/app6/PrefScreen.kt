package akw.app6

import android.content.Context
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ContextAmbient

@Composable
fun PrefScreen() {


    val context: Context = ContextAmbient.current


    Column {
        Text(text = "PrefScreen")
    }
}