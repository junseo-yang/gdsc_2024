package com.example.pa.ui.aigenerate

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatCompletion
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import com.example.pa.BuildConfig
import com.example.pa.data.DatabaseRepository
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class AiGenerateViewModel(private val repository: DatabaseRepository) : ViewModel() {

    private val openAI = OpenAI(BuildConfig.OPEN_AI_KEY)

    private val _text = MutableLiveData<String>()
    val text: LiveData<String> = _text
    @OptIn(BetaOpenAI::class)
    fun fetchChatCompletion(topic: String, startDate: LocalDate, endDate: LocalDate) {
        viewModelScope.launch {
            try {
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

                val formattedStartDate = startDate.format(formatter)
                val formattedEndDate = endDate.format(formatter)
                Log.d("Topic", "Topic: $topic")
                Log.d("Start Date", "Start Date: $formattedStartDate")
                Log.d("End Date", "End Date: $formattedEndDate")

                val schedule = "Create a schedule for the following details: " +
                        "Topic - $topic, Start Date - $formattedStartDate, End Date - $formattedEndDate."

                val chatCompletionRequest = ChatCompletionRequest(
                    model = ModelId("gpt-3.5-turbo"),
                    messages = listOf(
                        ChatMessage(
                            role = ChatRole.System,
                            content = "You are a helpful assistant!"
                        ),
                        ChatMessage(
                            role = ChatRole.User,
                            content = schedule
                        )
                    )
                )

                val completion: ChatCompletion = openAI.chatCompletion(chatCompletionRequest)
                _text.value = completion.choices.first().message?.content ?: "No response"
            } catch (e: Exception){
                _text.value = "ERROR! fetchChatCompletion()"
            }
        }
    }
}