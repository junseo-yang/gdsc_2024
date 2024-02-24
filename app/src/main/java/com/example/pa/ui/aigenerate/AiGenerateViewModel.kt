package com.example.pa.ui.aigenerate

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
import com.example.pa.data.local.Tasks
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class AiGenerateViewModel(private val repository: DatabaseRepository) : ViewModel() {

    private val openAI = OpenAI(BuildConfig.OPEN_AI_KEY)

    private val _text = MutableLiveData<String>()
    val text: LiveData<String> = _text
    fun insertTask(topic: String, startDate: String, endDate: String) {
        // Convert the start date from String to LocalDateTime
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val startDate = LocalDate.parse(startDate, formatter)
        val endDate = LocalDate.parse(endDate, formatter)


        // Create the Tasks entity
        val task = Tasks(
            taskId = 0,
            taskInput = topic,
            startDate = startDate,
            isTaskComplete = false,
            endDate = endDate,  // TODO add value of end date
        )
        viewModelScope.launch {
            repository.insert(task)
        }
    }
        @OptIn(BetaOpenAI::class)
    fun fetchChatCompletion(topic: String, startDate: String, endDate: String) {
        viewModelScope.launch {
            val schedule = "Please make a topic schedule with a detail hourly through out the day within the range of Start Date and End Date: " +
                    "Topic - $topic, Start Date - $startDate, End Date - $endDate."
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
            _text.postValue(completion.choices.first().message?.content ?: "No response")
        }
    }
}