package akw.app6

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.ConstraintLayout
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

enum class BlackjackAction {
    Deal, Hit, Stay
}

typealias BlackjackDispatch = (BlackjackAction) -> Unit

@Composable
fun ButtonsView(
    isGameOver: Boolean,
    dispatch: BlackjackDispatch,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {


        Button(enabled = isGameOver, modifier = Modifier.padding(all = 10.dp), onClick = {
            dispatch(BlackjackAction.Deal)
        }) {
            Text(text = "Deal")
        }
        Button(enabled = !isGameOver, modifier = Modifier.padding(all = 10.dp), onClick = {
            dispatch(BlackjackAction.Hit)
        }) {
            Text(text = "Hit")
        }
        Button(enabled = !isGameOver, modifier = Modifier.padding(all = 10.dp), onClick = {
            dispatch(BlackjackAction.Stay)
        }) {
            Text(text = "Stay")
        }

    }
}

@Composable
fun BlackjackScreen() {

    ConstraintLayout() {
        val (btns, ph, dh) = createRefs()

        ButtonsView(isGameOver = false, dispatch = {}, modifier = Modifier.constrainAs(btns) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })

        Card(backgroundColor = Color.Yellow, modifier = Modifier.constrainAs(ph) {
            top.linkTo(btns.bottom)
            start.linkTo(parent.start)
            end.linkTo(dh.start)
            bottom.linkTo(parent.bottom)
        }) { Text("ph", modifier = Modifier.fillMaxSize()) }

        Card(backgroundColor = Color.Cyan, modifier = Modifier.constrainAs(dh) {
            top.linkTo(btns.bottom)
            start.linkTo(ph.end)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)

        }) { Text("dh", modifier = Modifier.fillMaxSize()) }

        /*
        Card(backgroundColor = Color.Yellow) { Text("ph") }
        Card(backgroundColor = Color.Cyan) { Text("dh") }
        Card(backgroundColor = Color.LightGray) { Text("msg") }
*/

    }
}