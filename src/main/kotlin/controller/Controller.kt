package controller

import Block
import ControlUnitAction
import Numeral
import State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlin.math.pow

class ControlUnitController {

    private val initialState = State(
        blocks = listOf(),
        output = Numeral(realValue = 0.0, forHumans = ""),
        outputRaw = ""
    )

    var state by mutableStateOf(initialState)
        private set

    fun processAction(action: ControlUnitAction) {
        state = when (action) {
            ControlUnitAction.TEN -> {
                val activeBlock = activeBlock(state) ?: emptyBlock()
                if (activeBlock.tens < 5) {
                    val updatedActiveBlock = activeBlock.copy(tens = activeBlock.tens + 1)
                    val updatedBlocks = state.blocks.update(updatedActiveBlock)
                    state.copy(blocks = updatedBlocks)
                        .calculateOutput()
                        .updatePointer()
                } else state
            }

            ControlUnitAction.ONE -> {
                val activeBlock = activeBlock(state) ?: emptyBlock()
                if (activeBlock.ones < 9) {
                    val updatedActiveBlock = activeBlock.copy(ones = activeBlock.ones + 1)
                    val newBlocks = state.blocks.update(updatedActiveBlock)
                    state.copy(blocks = newBlocks)
                        .calculateOutput()
                        .updatePointer()
                } else state
            }

            ControlUnitAction.NEXT ->
                state.copy(blocks = state.blocks + emptyBlock())
                    .calculateOutput()
                    .updatePointer()

            ControlUnitAction.CLEAR_ENTRY -> {
                val activeBlock = activeBlock(state) ?: emptyBlock()
                val updatedBlocks = if (activeBlock.tens == 0 && activeBlock.ones == 0) {
                    state.blocks.dropLast(1)
                } else {
                    val updatedActiveBlock = clearBlock(activeBlock)
                    state.blocks.update(updatedActiveBlock)
                }
                state.copy(blocks = updatedBlocks)
                    .calculateOutput()
                    .updatePointer()
            }

            ControlUnitAction.CLEAR ->
                state.copy(blocks = emptyList())
                    .calculateOutput()
                    .updatePointer()
        }
    }

    private fun List<Block>.update(block: Block): List<Block> = this.dropLast(1) + block

    private fun activeBlock(state: State): Block? = state.blocks.lastOrNull()

    private fun clearBlock(block: Block): Block = block.copy(tens = 0, ones = 0)

    private fun emptyBlock() = Block(0, 0, isActive = false)
}

private fun State.calculateOutput(): State {
    val outputRaw = blocks.joinToString(separator = ";") {
        (it.tens * 10 + it.ones).toString()
    }

    val output = blocks.reversed().mapIndexed { index, block ->
        (block.tens * 10 + block.ones) * 60.toDouble().pow(index.toDouble())
    }
        .sum()

    val outputForHumans = String.format("%.0f", output)
    return copy(outputRaw = outputRaw, output = Numeral(output, outputForHumans))

}

private fun State.updatePointer(): State {
    return copy(
        blocks = blocks.mapIndexed { index, block ->
            if (index == blocks.size - 1) {
                block.copy(isActive = true)
            } else {
                block.copy(isActive = false)
            }
        }
    )
}

