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
}