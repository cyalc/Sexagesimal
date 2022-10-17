package ui.units

import ControlUnitAction
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.components.SymbolOne
import ui.components.SymbolTen

@Composable
@Preview
fun ControlUnit(
    onAction: (ControlUnitAction) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Button(
            onClick = { onAction(ControlUnitAction.TEN) },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
        ) {
            SymbolTen(modifier = Modifier.size(24.dp))
        }

        Button(
            onClick = { onAction(ControlUnitAction.ONE) },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
        ) {
            SymbolOne(modifier = Modifier.size(24.dp))
        }

        Button(
            onClick = { onAction(ControlUnitAction.NEXT) },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
        ) {
            Icon(
                imageVector = Icons.Outlined.Check,
                null
            )
        }

        Button(
            onClick = { onAction(ControlUnitAction.CLEAR_ENTRY) },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray)
        ) {
            Text("CE", color = Color.White)
        }

        Button(
            onClick = { onAction(ControlUnitAction.CLEAR) },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)

        ) {
            Text("C", color = Color.White)
        }
    }
}
