package com.worgle.worgle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.worgle.worgle.network.MyApi
import com.worgle.worgle.ui.theme.WorgleWorgleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            WorgleWorgleTheme {
                val viewModel = WorgleViewModel(LocalContext.current, MyApi.API_KEY)
                WorgleScreen(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun WorgleScreen(viewModel: WorgleViewModel = WorgleViewModel(LocalContext.current, MyApi.API_KEY)) {
    val todayWord = viewModel.todayWord
    var userInput by remember { mutableStateOf("") }
    var similarity by remember { mutableIntStateOf(0) }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("🤪 워글워글 🤪", style = MaterialTheme.typography.headlineLarge)
        Text("오늘의 단어: $todayWord", style = MaterialTheme.typography.bodySmall)

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextField(
                value = userInput,
                onValueChange = { userInput = it },
                label = { Text("단어를 입력하세요!") },
                modifier = Modifier.weight(1f)
            )

            Button(
                onClick = {
                    similarity = calculateSimilarity(userInput, todayWord)
                    result = if (userInput == todayWord) "정답입니다!🎉" else "오답입니다!😩"
                },
                modifier = Modifier.size(80.dp, 48.dp)
            ) {
                Text("입력", style = MaterialTheme.typography.bodySmall)
            }
        }

        if(similarity != 0) {
            if (similarity < 50) {
                Text("유사도는 $similarity 입니다.", color = Color.Red)
            } else {
                Text("유사도는 $similarity 입니다.", color = Color.Blue)
            }
        }
    }
}

fun calculateSimilarity(userInput: String, todayWord: String): Int {
    // TODO: 유사도 계산 알고리즘 구현 or 외부 라이브러리 사용
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