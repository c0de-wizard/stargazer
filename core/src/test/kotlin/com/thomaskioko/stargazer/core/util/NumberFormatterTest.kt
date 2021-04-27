package com.thomaskioko.stargazer.core.util

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

internal class NumberFormatterTest {

    @Test
    fun `givenANumber verifyItsFormatted Correctly`() {

        assertThat("5").isEqualTo(countFormatter(5))
        assertThat("124").isEqualTo(countFormatter(124))
        assertThat("5.8k").isEqualTo(countFormatter(5_821))
        assertThat("10k").isEqualTo(countFormatter(10_500))
        assertThat("12k").isEqualTo(countFormatter(12000))
        assertThat("92M").isEqualTo(countFormatter(92_150_000))
    }
}