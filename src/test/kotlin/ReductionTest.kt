import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Ignore
import org.junit.Test
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class ReductionTest {

    @Test
    fun polarOpposites_simple_false() {
        assertThat(polarOpposites('a', 'a'), `is`(false))
    }

    @Test
    fun polarOpposites_simple2_false() {
        assertThat(polarOpposites('a', 'B'), `is`(false))
    }

    @Test
    fun polarOpposites_simple_true() {
        assertThat(polarOpposites('a', 'A'), `is`(true))
    }

    @Test
    fun polarOpposites_simple2_true() {
        assertThat(polarOpposites('A', 'a'), `is`(true))
    }

    @Test
    fun polarOpposites_simple2_2upper() {
        assertThat(polarOpposites('A', 'A'), `is`(false))
    }
    @Test
    fun reduceUnits_simple() {
        val data = "abBd"
        val expected = "ad"
        assertThat(reduceUnits(data), `is`(expected))
    }

    @Test
    fun reduceUnits_complex() {
        val data = "abcdDCdcecghEeHGhgdD"
        val expected = "abdcechg"
        assertThat(reduceUnits(data), `is`(expected))
    }

    @Test
    fun somePolarOpposite_none() {
        assertThat(somePolarOpposite("ab"), `is`(false))
    }

    @Test
    fun somePolarOpposite_simpleTrue() {
        assertThat(somePolarOpposite("abaAb"), `is`(true))
    }

    @Test
    fun reduceUnits_fromFile() {
        assertThat(reduceUnitsFromFile("example.txt"), `is`("dabCBAcaDA"))
    }

    @Test
    fun reduceUnitsSize_fromFile() {
        assertThat(reduceUnitsFromFile("example.txt").length   , `is`(10))
    }

    @Test
    fun reduceUnitsSize_part1() {
        assertThat(reduceUnitsFromFile("part1.txt").length, `is`(9562))
    }

    @Test
    @Ignore
    fun reduceUnitsSize_part1Timed() {
        val times = mutableListOf<Long>()
        repeat(10) {
            val start = LocalDateTime.now()
            reduceUnitsFromFile("part1.txt")
            times.add(start.until(LocalDateTime.now(), ChronoUnit.MILLIS))
        }
        times.forEachIndexed { index, l ->  println("Attempt: $index, time: ${l}ms)") }
        println("Average: ${times.average()}ms")
    }

    @Test
    fun generateOpposites_range() {
        assertThat(generateOpposites('a'..'g'), `is`(listOf("aA", "bB", "cC", "dD" , "eE", "fF", "gG")))
    }

    @Test
    fun removeOppositeUnits_simple() {
        assertThat(removeOppositeUnits("aA", "dabAcCaCBAcCcaDA"), `is`("dbcCCBcCcD"))
    }

    @Test
    fun reduceWithoutUnits_simple() {
        assertThat(reduceWithoutUnits("dabAcCaCBAcCcaDA", generateOpposites('a'..'a').first()), `is`("dbCBcD"))
    }

    @Test
    fun reduceWithoutUnits_list() {
        val expected = mapOf("aA" to "dbCBcD", "bB" to "daCAcaDA", "cC" to "daDA", "dD" to "abCBAc")
        val actual = reduceWithoutUnits("dabAcCaCBAcCcaDA", generateOpposites('a'..'d'))
        assertThat(actual, `is`(expected))
    }

    @Test
    fun reduceWithoutUnitsSize_example() {
        val actual = reduceWithoutUnits("dabAcCaCBAcCcaDA", generateOpposites('a'..'d'))
        assertThat(actual.minBy { (_, reducedPolymer) -> reducedPolymer.length }?.value?.length, `is`(4))
    }

    @Test
    fun reduceWithoutUnitsSize_fromFile() {
        val actual = reduceWithoutUnits(loadPolymer("example2.txt"), generateOpposites('a'..'d'))
        assertThat(actual.minBy { (_, reducedPolymer) -> reducedPolymer.length }?.value?.length, `is`(4))
    }

    @Test
    @Ignore
    fun reduceWithoutUnitsSize_part2() {
        val actual = reduceWithoutUnits(loadPolymer("part1.txt"), generateOpposites('a'..'z'))
        assertThat(actual.minBy { (_, reducedPolymer) -> reducedPolymer.length }?.value?.length, `is`(4))
    }


}