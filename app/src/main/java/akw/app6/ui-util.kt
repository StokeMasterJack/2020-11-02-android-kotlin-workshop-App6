package akw.app6

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.preferredWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

sealed class UIState<out T> {
    object NotStarted : UIState<Nothing>()
    object Loading : UIState<Nothing>()
    data class Success<T>(val data: T) : UIState<T>()
    data class Error(val error: Throwable) : UIState<Nothing>()
}

@Composable
fun VSpace(preferredHeight: Int = 10) {
    Spacer(modifier = Modifier.preferredHeight(preferredHeight.dp))
}

@Composable
fun HSpace(preferredWidth: Int = 10) {
    Spacer(modifier = Modifier.preferredWidth(preferredWidth.dp))
}