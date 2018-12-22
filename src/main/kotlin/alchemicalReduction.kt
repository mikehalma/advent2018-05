import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.io.File
import java.nio.charset.Charset

fun polarOpposites(unit1: Char, unit2: Char): Boolean {
    return when {
        unit1.isLowerCase() -> unit1.toUpperCase() == (unit2)
        unit2.isLowerCase() -> unit2.toUpperCase() == (unit1)
        else -> false
    }
}

private val LOWER_FIRST_REGEX = "(\\p{Ll})(?=\\p{Lu})(?i)\\1".toRegex()
private val UPPER_FIRST_REGEX = "(\\p{Lu})(?=\\p{Ll})(?i)\\1".toRegex()

fun somePolarOpposite(units: String): Boolean {
    return LOWER_FIRST_REGEX.containsMatchIn(units)
        || UPPER_FIRST_REGEX.containsMatchIn(units)
}

fun getFirstPolarOpposite(units: String): String {
    var result = LOWER_FIRST_REGEX.find(units)
    if (result == null || result.groups.isEmpty()) {
        result = UPPER_FIRST_REGEX.find(units)
    }
    return result?.groupValues?.get(0) ?: ""
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
    val result = reduceUnits(removeOppositeUnits(units, polymer))
    return result
}

fun reduceWithoutUnits(polymer: String, units: List<String>): List<String> {
    return (0.until(units.size)).pmap {
        reduceWithoutUnits(polymer, units[it])
    }
}

fun <A, B>Iterable<A>.pmap(f: suspend (A) -> B): List<B> = runBlocking {
    map { async { f(it) } }.map { it.await() }
}