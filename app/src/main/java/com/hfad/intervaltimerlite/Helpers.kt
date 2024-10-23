package com.hfad.intervaltimerlite

object TimeUtils {
    // Convert seconds to "hh:mm:ss" format
    fun timerToHhMmSs(millis: Long): String {
        val totalSeconds = millis / 1000
        val hours = (totalSeconds / 3600).toString().padStart(2, '0')
        val minutes = ((totalSeconds % 3600) / 60).toString().padStart(2, '0')
        val seconds = (totalSeconds % 60).toString().padStart(2, '0')
        return "$hours:$minutes:$seconds"
    }

    fun hhMmSsToMillis(time: String): Long {
        val parts = time.split(":").map { it.toLongOrNull() ?: 0L }
        return (parts.getOrNull(0) ?: 0L) * 3600 * 1000 + // Hours to milliseconds
                (parts.getOrNull(1) ?: 0L) * 60 * 1000 +   // Minutes to milliseconds
                (parts.getOrNull(2) ?: 0L) * 1000          // Seconds to milliseconds
    }

    fun deleteDots(time: String): String {
        val parts = time.split(":")
        val timeWithoutDots = parts.joinToString("").drop(1)
        return timeWithoutDots
    }


    // Format a string into "hh:mm:ss" format
    fun formatToHhMmSs(input: String): String {
        val digits = input.filter { it.isDigit() }
        val padded = digits.padStart(6, '0').takeLast(6) // Ensure 6 digits
        val hours = padded.substring(0, 2)
        val minutes = padded.substring(2, 4)
        val seconds = padded.substring(4, 6)
        return "$hours:$minutes:$seconds"
    }
}