// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
            Card(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(16.dp)
                    .clickable { },
                elevation = 8.dp
            ) {
                Column(modifier = Modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    ControlUnit {
                        controller.processAction(it)
                    }
                    OutputScreen(controller.state.outputRaw)
                    OutputScreen(controller.state.output.forHumans)
                }
            }

            Column {
                val scrollStateHorizontal = rememberScrollState(0)
                Row(
                    modifier = Modifier.padding(start = 16.dp)
                        .horizontalScroll(scrollStateHorizontal)
                ) {
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
                Spacer(modifier = Modifier.size(8.dp))
                HorizontalScrollbar(
                    modifier = Modifier.fillMaxWidth()
                        .padding(16.dp),
                    adapter = rememberScrollbarAdapter(scrollStateHorizontal)
                )
            }
        }
    }
}

fun main() = singleWindowApplication(
    title = "Sexagesimal 0.1",
    state = WindowState(width = 400.dp, height = 320.dp),
) {
    App()
}
