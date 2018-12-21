fun polarOpposites(unit1: Char, unit2: Char): Boolean {
    return unit1.toUpperCase() == unit2 || unit2.toUpperCase() == unit1
}

fun somePolarOpposite(units: List<Char>): Boolean {
    return units.windowed(size = 2) {polarOpposites(it[0], it[1])}.any {it}
}

fun reduceUnits(units: List<Char>): List<Char> {
    var raw = units.toMutableList()
    while(somePolarOpposite(raw)) {
        var skip = false
        raw = raw.windowed(size = 2, partialWindows = true) {
            when {
                skip -> { skip = false; noUnits()
                }
                isLastUnit(it) -> keepUnit(it)
                !polarOpposites(it[0], it[1]) -> {skip = false; keepUnit(it)}
                else -> {skip = true; noUnits()
                }
            }
        }.flatten().toMutableList()
    }
    return raw
}

private fun keepUnit(it: List<Char>) = listOf(it[0])

private fun noUnits(): List<Char> = listOf()

private fun isLastUnit(it: List<Char>) = it.size == 1
