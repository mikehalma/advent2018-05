import java.io.File
import java.nio.charset.Charset

fun polarOpposites(unit1: Char, unit2: Char): Boolean {
    return unit1.toUpperCase() == unit2 || unit2.toUpperCase() == unit1
}

fun somePolarOpposite(units: List<Char>): Boolean {
    return units.windowed(size = 2) {polarOpposites(it[0], it[1])}.any {it}
}

fun getFirstPolarOpposite(units: List<Char>): List<Char> {
    return units.windowed(size = 2).first {polarOpposites(it[0], it[1])}
}

fun reduceUnits(units: List<Char>): List<Char> {
    var raw = units.toMutableList()
    while(somePolarOpposite(raw)) {
        val opposites = getFirstPolarOpposite(raw).joinToString("")
        raw = raw.joinToString("").replaceFirst(opposites, "").toCharArray().toMutableList()
    }
    return raw
}

fun reduceUnits(fileName: String):List<Char> {
    return reduceUnits(loadPolymer(fileName))
}

fun loadPolymer(fileName :String) :List<Char> {
    return File(object {}.javaClass.getResource(fileName).toURI()).readText(Charset.defaultCharset()).toCharArray().toList()
}
