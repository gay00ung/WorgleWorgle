package com.worgle.worgle

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.worgle.worgle.network.MyApi
import com.worgle.worgle.ui.theme.Green
import com.worgle.worgle.ui.theme.Lemon

@Composable
fun WorgleScreen(
    viewModel: WorgleViewModel
) {
    val todayWord by viewModel.todayWord.collectAsState()
    var userInput by remember { mutableStateOf("") }
    var similarity by remember { mutableIntStateOf(0) }
    var result by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.fetchTodayWord()
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Lemon),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "🤪 워글워글 🤪",
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center,
                color = Green
            )
        }

        Text(
            text = "오늘의 단어: ${todayWord ?: "불러오는 중..."}",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = userInput,
                onValueChange = { userInput = it },
                label = { Text("단어를 입력하세요!") },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Green,
                    unfocusedBorderColor = Green,
                    cursorColor = Green
                )
            )

            Button(
                onClick = {
                    similarity = todayWord?.let { calculateSimilarity(userInput, it) }!!
                    result = if (userInput == todayWord) "정답입니다!🎉" else "오답입니다!😩"
                },
                modifier = Modifier.size(80.dp, 48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Green,
                    contentColor = Lemon
                )
            ) {
                Text(
                    text = "입력",
                    style = MaterialTheme.typography.bodySmall
                )
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

@Preview
@Composable
fun PreviewWorgleScreen() {
    WorgleScreen(viewModel = WorgleViewModel(LocalContext.current, MyApi.API_KEY))
}