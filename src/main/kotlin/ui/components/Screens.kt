package ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

private const val ROW_UNIT_SIZE = 3

private const val IMAGE_ONE_PATH = "Babylonian_1.svg"
private const val IMAGE_TEN_PATH = "Babylonian_10.svg"

@Composable
fun OutputScreen(output: String) {
    Text(output)
}

@Composable
fun BabylonianDigit(tens: Int, ones: Int) {
    Row {
        TensScreen(tens)
        OnesScreen(ones)
    }
}

@Composable
private fun TensScreen(n: Int) {
    Screen(n) { SymbolTen(modifier = Modifier.size(24.dp)) }
}

@Composable
private fun OnesScreen(n: Int) {
    Screen(n) { SymbolOne(modifier = Modifier.size(24.dp)) }
}

@Composable
private fun Screen(n: Int, symbol: @Composable (Int) -> Unit) {
    Column {
        SymbolRow(symbol, n.inRow(1))
        if (n > ROW_UNIT_SIZE)
            SymbolRow(symbol, n.inRow(2))
        if (n > ROW_UNIT_SIZE * 2)
            SymbolRow(symbol, n.inRow(3))
    }
}

@Composable
private fun SymbolRow(symbol: @Composable (Int) -> Unit, count: Int) {
    Row(
        horizontalArrangement = Arrangement.spacedBy((-16).dp)
    ) {
        for (i in 0 until count) {
            symbol(i)
        }
    }
}

@Composable
fun SymbolTen(modifier: Modifier) {
    Symbol(IMAGE_TEN_PATH, modifier)
}

@Composable
fun SymbolOne(modifier: Modifier) {
    Symbol(IMAGE_ONE_PATH, modifier)
}

@Composable
private fun Symbol(path: String, modifier: Modifier) {
    Image(
        painterResource(path),
        "",
        modifier = modifier
    )
}

private fun Int.inRow(step: Int): Int {
    val total = this
    val current = step * ROW_UNIT_SIZE
    return if (total >= current) ROW_UNIT_SIZE
    else total % ROW_UNIT_SIZE
}