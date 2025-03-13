package com.worgle.worgle

fun calculateSimilarity(userInput: String, todayWord: String): Int {
    val len1 = userInput.length
    val len2 = todayWord.length

    val dp = Array(len1 + 1) { IntArray(len2 + 1) }
    for (i in 0..len1) dp[i][0] = i
    for (j in 0..len2) dp[0][j] = j

    for (i in 1..len1) {
        for (j in 1..len2) {
            dp[i][j] = if (userInput[i - 1] == todayWord[j - 1]) {
                dp[i - 1][j - 1]
            } else {
                1 + minOf(dp[i - 1][j], dp[i][j - 1], dp[i - 1][j - 1])
            }
        }
    }

    val maxLength = maxOf(len1, len2)
    return ((1 - dp[len1][len2].toDouble() / maxLength) * 100).toInt()
}