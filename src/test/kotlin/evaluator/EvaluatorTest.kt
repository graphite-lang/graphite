package evaluator

import org.graphite.`object`.objects.BooleanObject
import org.graphite.`object`.objects.IntObject
import org.graphite.`object`.objects.NullObject
import org.junit.jupiter.api.Test

class EvaluatorTest {
    private val evaluatorTestHelper = EvaluatorTestHelper()

    @Test
    fun `test eval integer expression`() {
        listOf(
            listOf("5", 5),
            listOf("10", 10),
            listOf("-5", -5),
            listOf("-10", -10),
            listOf("10 - 5 + 5", 10),
            listOf("5 + 5 + 5 + 5 - 10", 10),
            listOf("2 * 2 * 2 * 2 * 2", 32),
            listOf("5 * 2 + 10", 20),
            listOf("5 + 2 * 10", 25),
            listOf("5 * (2 + 10)", 60),
            listOf("5 * (2 + 10) + 3", 63),
            listOf("5 * (2 + 10) + 3 * 10", 90),
            listOf("5 * (2 + 10) + 3 * 10 / 2", 75),
            listOf("5 * (2 + 10) + 3 * 10 / 2 - 10", 65),
            listOf("5 * (2 + 10) + 3 * 10 / 2 - 10 + 5", 70),
        ).forEach { (input, expected) ->
            val evaluatedObject = evaluatorTestHelper.testEvaluator(input = input as String)

            if (evaluatedObject is IntObject) {
                assert(evaluatedObject.value == expected) {
                    "Evaluated object value is not `${expected}`! got `${evaluatedObject.value}`"
                }
            } else {
                error("Evaluated object is not `IntObject`! got ${evaluatedObject?.javaClass}")
            }
        }
    }

    @Test
    fun `test eval boolean expression`() {
        listOf(
            listOf("true", true),
            listOf("false", false),
            listOf("!((5 + 10 - 5) == 10)", false),
            listOf("5 == 5", true),
            listOf("5 != 5", false),
            listOf("5 < 5", false),
            listOf("5 > 5", false),
        ).forEach { (input, expected) ->
            val evaluatedObject = evaluatorTestHelper.testEvaluator(input = input as String)

            if (evaluatedObject is BooleanObject) {
                assert(evaluatedObject.value == expected) {
                    "Evaluated object value is not `${expected}`! got `${evaluatedObject.value}`"
                }
            } else {
                error("Evaluated object is not `BooleanObject`! got ${evaluatedObject?.javaClass}")
            }
        }
    }

    @Test
    fun `test eval bang operator`() {
        listOf(
            listOf("!true", false),
            listOf("!false", true),
            listOf("!!true", true),
            listOf("!!false", false)
        ).forEach { (input, expected) ->
            val evaluatedObject = evaluatorTestHelper.testEvaluator(input = input as String)

            if (evaluatedObject is BooleanObject) {
                assert(evaluatedObject.value == expected) {
                    "Evaluated object value is not `${expected}`! got `${evaluatedObject.value}`"
                }
            } else {
                error("Evaluated object is not `BooleanObject`! got ${evaluatedObject?.javaClass}")
            }
        }
    }

    @Test
    fun `test eval if expression`() {
        listOf(
            listOf("if (true) { 10 }", 10),
            listOf("if (false) { 10 }", null),
            listOf("if (1) { 10 }", null),
            listOf("if (1 < 2) { 10 }", 10),
            listOf("if (1 > 2) { 10 }", null),
        ).forEach { (input, expected) ->
            val evaluatedObject = evaluatorTestHelper.testEvaluator(input = input as String)

            println(evaluatedObject?.javaClass)

            if (expected == null) {
                if (evaluatedObject !is NullObject) {
                    error("Evaluated object is not `IntObject`! got ${evaluatedObject?.javaClass}")
                }
            } else if (expected is Int) {
                if (evaluatedObject is IntObject) {
                    assert(evaluatedObject.value == expected) {
                        "Evaluated object value is not `${expected}`! got `${evaluatedObject.value}`"
                    }
                } else {
                    error("Evaluated object is not `IntObject`! got ${evaluatedObject?.javaClass}")
                }
            }
        }
    }

    @Test
    fun `test eval return statement`() {
        listOf(
            listOf("return 10", 10),
            listOf(
                """
if (10 > 1) {
    if (10 > 1) {
        return 10
    }
    
    return 1
}
            """.trimIndent(), 10
            )
        ).forEach { (input, expected) ->
            val evaluatedObject = evaluatorTestHelper.testEvaluator(input = input as String)

            if (evaluatedObject is IntObject) {
                assert(evaluatedObject.value == expected) {
                    "Evaluated object value is not `${expected}`! got `${evaluatedObject.value}`"
                }
            } else {
                error("Evaluated object is not `IntObject`! got ${evaluatedObject?.javaClass}")
            }
        }
    }

    @Test
    fun `test eval variable statements`() {
        listOf(
            listOf("const a = 5 + 5; a", 10),
            listOf("var a = 5 + 5; a", 10),
        ).forEach { (input, expected) ->
            val evaluatedObject = evaluatorTestHelper.testEvaluator(input = input as String)

            if (evaluatedObject is IntObject) {
                assert(evaluatedObject.value == expected) {
                    "Evaluated object value is not `${expected}`! got `${evaluatedObject.value}`"
                }
            } else {
                error("Evaluated object is not `IntObject`! got ${evaluatedObject?.javaClass}")
            }
        }
    }
}