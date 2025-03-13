package com.worgle.worgle

fun getRandomKoreanChar(): Char {
    val consonants = listOf('ㄱ', 'ㄴ', 'ㄷ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅅ', 'ㅇ', 'ㅈ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ')
    val vowels = listOf('ㅏ', 'ㅑ', 'ㅓ', 'ㅕ', 'ㅗ', 'ㅛ', 'ㅜ', 'ㅠ', 'ㅡ', 'ㅣ')

    val choIndex = consonants.indices.random() * 588
    val jungIndex = vowels.indices.random() * 28
    val unicodeValue = 0xAC00 + choIndex + jungIndex

    return unicodeValue.toChar()
}

