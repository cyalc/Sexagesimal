// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import controller.ControlUnitController
import ui.components.BabylonianDigit
import ui.components.OutputScreen
import ui.units.ControlUnit

@Composable
@Preview
fun App() {
    val controller = remember { ControlUnitController() }

    MaterialTheme {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                ControlUnit {
                    controller.processAction(it)
                }
                Column {
                    OutputScreen(controller.state.outputRaw)
                    Spacer(modifier = Modifier.size(8.dp))
                    OutputScreen(controller.state.output.forHumans)
                }
            }

            Row(modifier = Modifier.padding(start = 16.dp)) {
                controller.state.blocks.map {
                    Column {
                        Text(
                            "_", color = Color.Red, fontSize = 24.sp,
                            modifier = Modifier.alpha(if (it.isActive) 1f else 0f)
                        )
                        BabylonianDigit(tens = it.tens, ones = it.ones)
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}

fun main() = singleWindowApplication(
    title = "Sexagesimal 0.1",
    state = WindowState(width = 1000.dp, height = 280.dp),
) {
    App()
}
