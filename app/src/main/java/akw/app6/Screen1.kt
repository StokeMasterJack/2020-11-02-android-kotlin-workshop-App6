package akw.app6

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun Screen1() {
    val bg = MaterialTheme.colors.secondary

    Column {
        Text(text = "Screen1", color = bg)
    }
}