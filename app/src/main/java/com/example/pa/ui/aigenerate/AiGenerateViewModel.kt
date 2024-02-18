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
import kotlinx.coroutines.launch

class AiGenerateViewModel : ViewModel() {

    private val openAI = OpenAI(BuildConfig.OPEN_AI_KEY)

    private val _text = MutableLiveData<String>()
    val text: LiveData<String> = _text


    @OptIn(BetaOpenAI::class)
    fun fetchChatCompletion(userInput: String) {
        viewModelScope.launch {
            val chatCompletionRequest = ChatCompletionRequest(
                model = ModelId("gpt-3.5-turbo"),
                messages = listOf(
                    ChatMessage(
                        role = ChatRole.System,
                        content = "You are a helpful assistant!"
                    ),
                    ChatMessage(
                        role = ChatRole.User,
                        content = userInput
                    )
                )
            )

            val completion: ChatCompletion = openAI.chatCompletion(chatCompletionRequest)
            _text.postValue(completion.choices.first().message?.content ?: "No response")
        }
    }
}