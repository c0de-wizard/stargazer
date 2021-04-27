package com.thomaskioko.stargazer.core.util

import java.util.*


/**
 * Recursive implementation, invokes itself for each factor of a thousand, increasing the class on each invocation.
 * @param value the number to format
 * @return a String representing the number n formatted in a cool looking way.
 */
fun countFormatter(value: Long): String {

    val suffixes: NavigableMap<Long, String> = TreeMap()
    suffixes[1_000L] = "k"
    suffixes[1_000_000L] = "M"

    if (value == Long.MIN_VALUE) return countFormatter(Long.MIN_VALUE + 1)
    if (value < 0) return "-" + countFormatter(-value)
    if (value < 1000) return value.toString() //deal with easy case
    val e: Map.Entry<Long, String> = suffixes.floorEntry(value)
    val divideBy = e.key
    val suffix = e.value
    val truncated = value / (divideBy / 10) //the number part of the output times 10
    val hasDecimal = truncated < 100 && truncated / 10.0 != (truncated / 10).toDouble()
    return if (hasDecimal) (truncated / 10.0).toString() + suffix else (truncated / 10).toString() + suffix
}