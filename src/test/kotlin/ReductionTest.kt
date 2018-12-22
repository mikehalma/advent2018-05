import kotlinx.coroutines.delay
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Ignore
import org.junit.Test
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlin.system.measureTimeMillis

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
    fun somePolarOpposite_reversedTrue() {
        assertThat(somePolarOpposite("abAab"), `is`(true))
    }

    @Test
    fun getFirstPolarOpposite_simple_returnsFirst() {
        assertThat(getFirstPolarOpposite("abaAb"), `is`("aA"))
    }

    @Test
    fun getFirstPolarOpposite_upperFirst_returnsFirst() {
        assertThat(getFirstPolarOpposite("abAab"), `is`("Aa"))
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
    @Ignore
    fun reduceUnitsSize_part1() {
        assertThat(reduceUnitsFromFile("part1.txt").length, `is`(9562))
    }

    @Test
    @Ignore
    fun reduceUnitsSize_part1Timed() {
        val totalTimeStart = LocalDateTime.now()
        val times = mutableMapOf<LocalDateTime, Long>()
        (1..10).map {
            val start = LocalDateTime.now()
            reduceUnitsFromFile("part1.txt")
            times.put(LocalDateTime.now(), start.until(LocalDateTime.now(), ChronoUnit.MILLIS))
        }
        times.values.forEachIndexed{ index, l ->  println("Attempt: $index, time: ${l}ms)") }
        println("Average: ${times.values.average()}ms")
        println("Total time: ${totalTimeStart.until(LocalDateTime.now(), ChronoUnit.MILLIS)}")
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
        val expected = listOf("dbCBcD", "daCAcaDA", "daDA", "abCBAc")
        val actual = reduceWithoutUnits("dabAcCaCBAcCcaDA", generateOpposites('a'..'d'))
        assertThat(actual, `is`(expected))
    }

    @Test
    fun reduceWithoutUnitsSize_example() {
        val actual = reduceWithoutUnits("dabAcCaCBAcCcaDA", generateOpposites('a'..'d'))
        assertThat(actual.minBy { reducedPolymer -> reducedPolymer.length }?.length, `is`(4))
    }

    @Test
    fun reduceWithoutUnitsSize_fromFile() {
        val actual = reduceWithoutUnits(loadPolymer("example2.txt"), generateOpposites('a'..'d'))
        assertThat(actual.minBy { reducedPolymer -> reducedPolymer.length }?.length, `is`(4))
    }

    @Test
    fun reduceWithoutUnitsSize_part2() {
        val actual = reduceWithoutUnits(loadPolymer("part1.txt"), generateOpposites('a'..'z'))
        assertThat(actual.minBy { reducedPolymer -> reducedPolymer.length }?.length, `is`(4934))
    }

    @Test
    fun pmap_isParallel() {
        val killParallel = "dead"
        val time = measureTimeMillis {
            val output = (1..100).pmap {
                println("$killParallel starting at ${LocalDateTime.now()}")
                delay(1000)
                it * 2
            }

            println(output)
        }
        println("Total time: $time")
    }


}