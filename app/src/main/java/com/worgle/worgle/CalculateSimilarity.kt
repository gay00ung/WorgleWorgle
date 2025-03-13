package com.worgle.worgle

fun calculateSimilarity(userInput: String, todayWord: String): Int {
    val s = userInput.lowercase().trim()
    val t = todayWord.lowercase().trim()
    if (s.isEmpty() && t.isEmpty()) return 100
    if (s.isEmpty() || t.isEmpty()) return 0
    val distance = levenshteinDistance(s, t)
    val maxLen = maxOf(s.length, t.length)
    val similarity = (1 - distance.toFloat() / maxLen) * 100
    return similarity.toInt().coerceIn(0, 100)
}

fun levenshteinDistance(a: String, b: String): Int {
    val n = a.length
    val m = b.length
    if (n == 0) return m
    if (m == 0) return n
    val dp = Array(n + 1) { IntArray(m + 1) }
    for (i in 0..n) {
        dp[i][0] = i
    }
    for (j in 0..m) {
        dp[0][j] = j
    }
    for (i in 1..n) {
        for (j in 1..m) {
            val cost = if (a[i - 1] == b[j - 1]) 0 else 1
            dp[i][j] = minOf(dp[i - 1][j] + 1, dp[i][j - 1] + 1, dp[i - 1][j - 1] + cost)
        }
    }
    return dp[n][m]
}