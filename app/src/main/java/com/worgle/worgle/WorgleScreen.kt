package com.worgle.worgle

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
fun WorgleScreen(viewModel: WorgleViewModel) {
    val todayWord by viewModel.todayWord.collectAsState()
    var userInput by remember { mutableStateOf("") }
    val wordLength = todayWord?.length ?: 0
    var similarity by remember { mutableStateOf<Int?>(null) }
    val context = LocalContext.current

    LaunchedEffect(Unit) { viewModel.fetchTodayWord() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Lemon)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        TitleText()
        InstructionText(wordLength)
        InputSection(
            userInput = userInput,
            onUserInputChange = { userInput = it },
            onSubmit = {
                similarity = todayWord?.let { calculateSimilarity(userInput, it) }
                showToast(context, userInput, todayWord)
                logResults(similarity, todayWord, userInput)
            }
        )
        similarity?.let { SimilarityText(it) }
    }
}

@Composable
fun TitleText() {
    Text(
        text = "🤪 워글워글 🤪",
        style = MaterialTheme.typography.headlineLarge,
        textAlign = TextAlign.Center,
        color = Green
    )
}

@Composable
fun InstructionText(wordLength: Int) {
    Text(
        text = "오늘의 단어 글자 수 : $wordLength",
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(8.dp)
    )
}

@Composable
fun InputSection(userInput: String, onUserInputChange: (String) -> Unit, onSubmit: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = userInput,
            onValueChange = onUserInputChange,
            label = { Text("단어를 입력하세요!") },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.weight(1f),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Green,
                unfocusedBorderColor = Green,
                cursorColor = Green,
                focusedLabelColor = Green
            )
        )

        Button(
            onClick = onSubmit,
            modifier = Modifier.size(80.dp, 48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Green, contentColor = Lemon)
        ) {
            Text(text = "입력", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun SimilarityText(similarity: Int) {
    val color = when {
        similarity == 100 -> Green
        similarity < 50 -> Color.Red
        else -> Color.Blue
    }
    Text(
        text = if (similarity == 100) "정답입니다!🎉" else "유사도는 $similarity 입니다.",
        color = color,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(8.dp)
    )
}

fun showToast(context: android.content.Context, userInput: String, todayWord: String?) {
    val message = if (userInput == todayWord) "정답입니다!🎉" else "오답입니다!😩"
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun logResults(similarity: Int?, todayWord: String?, userInput: String) {
    Log.d("WorgleScreen", "similarity: $similarity")
    Log.d("WorgleScreen", "todayWord: $todayWord")
    Log.d("WorgleScreen", "userInput: $userInput")
}

@Preview
@Composable
fun PreviewWorgleScreen() {
    WorgleScreen(viewModel = WorgleViewModel(LocalContext.current, MyApi.API_KEY))
}
