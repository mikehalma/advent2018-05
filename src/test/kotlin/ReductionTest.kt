import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Ignore
import org.junit.Test

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
    fun reduceUnits_simple() {
        val data = mutableListOf('a', 'b', 'B', 'd')
        val expected = listOf('a', 'd')
        assertThat(reduceUnits(data), `is`(expected))
    }

    @Test
    fun reduceUnits_complex() {
        val data = mutableListOf('a', 'b', 'c', 'd', 'D', 'C', 'd', 'c', 'e', 'c', 'g', 'h', 'E', 'e', 'H', 'G', 'h', 'g', 'd', 'D')
        val expected = listOf('a', 'b', 'd', 'c', 'e', 'c', 'h', 'g')
        assertThat(reduceUnits(data), `is`(expected))
    }

    @Test
    fun somePolarOpposite_none() {
        assertThat(somePolarOpposite(listOf('a', 'b')), `is`(false))
    }

    @Test
    fun somePolarOpposite_simpleTrue() {
        assertThat(somePolarOpposite(listOf('a', 'b', 'a', 'A', 'b')), `is`(true))
    }

    @Test
    fun reduceUnits_fromFile() {
        assertThat(reduceUnits("example.txt"), `is`("dabCBAcaDA"))
    }

    @Test
    @Ignore
    fun reduceUnits_part1() {
        assertThat(reduceUnits("part1.txt"), `is`(""))
    }
}