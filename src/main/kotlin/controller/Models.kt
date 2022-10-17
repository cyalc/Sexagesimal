enum class ControlUnitAction {
    TEN,
    ONE,
    NEXT,
    CLEAR_ENTRY,
    CLEAR,
}

data class State(
    val blocks: List<Block>,
    val outputRaw: String,
    val output: Numeral
)

data class Numeral(
    val realValue: Double,
    val forHumans: String
)

data class Block(
    val tens: Int,
    val ones: Int,
    val isActive: Boolean
)