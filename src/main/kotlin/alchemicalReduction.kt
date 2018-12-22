import java.io.File
import java.nio.charset.Charset

fun polarOpposites(unit1: Char, unit2: Char): Boolean {
    return when {
        unit1.isLowerCase() -> unit1.toUpperCase() == (unit2)
        unit2.isLowerCase() -> unit2.toUpperCase() == (unit1)
        else -> false
    }
}

fun somePolarOpposite(units: String): Boolean {
    return units.windowed(size = 2) {polarOpposites(it[0], it[1])}.any {it}
}

fun getFirstPolarOpposite(units: String): String {
    return units.windowed(size = 2).first {polarOpposites(it[0], it[1])}
}

fun reduceUnits(units: String): String {
    var reduced = units
    while(somePolarOpposite(reduced)) {
        val opposites = getFirstPolarOpposite(reduced)
        reduced = reduced.replaceFirst(opposites, "")
    }
    return reduced
}

fun reduceUnitsFromFile(fileName: String):String {
    return reduceUnits(loadPolymer(fileName))
}

fun loadPolymer(fileName :String) :String {
    return File(object {}.javaClass.getResource(fileName).toURI()).readText(Charset.defaultCharset())
}

fun generateOpposites(range: CharRange) :List<String> {
    return range.map {String(arrayOf(it, it.toUpperCase()).toCharArray())}
}

fun removeOppositeUnits(units: String, polymer: String): String {
    val first = units.first().toString()
    val last = units.toCharArray()[1].toString()
    var result = polymer.replace(first, "")
    result = result.replace(last, "")
    return result
}

fun reduceWithoutUnits(polymer: String, units: String): String {
    return reduceUnits(removeOppositeUnits(units, polymer))
}

fun reduceWithoutUnits(polymer: String, units: List<String>): Map<String, String> {
    return units.map { it to reduceWithoutUnits(polymer, it) }.toMap()
}