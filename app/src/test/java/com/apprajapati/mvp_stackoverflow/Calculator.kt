package com.apprajapati.mvp_stackoverflow

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class Calculator {
    fun sum(a: Int, b: Int) = a + b
}


class CalculatorTest {
    @Test
    fun addition_isCorrect() {
        val cal = Calculator()
        Assertions.assertThat(cal.sum(2, 3)).isEqualTo(5)
    }
}