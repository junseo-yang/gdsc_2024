package com.example.pa

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatCompletion
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import kotlinx.coroutines.launch

@OptIn(BetaOpenAI::class)
class AiActivity : AppCompatActivity() {

    private val openAI = OpenAI(BuildConfig.OPEN_AI_KEY)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            val chatCompletionRequest = ChatCompletionRequest(
                model = ModelId("gpt-3.5-turbo"),
                messages = listOf(
                    ChatMessage(
                        role = ChatRole.System,
                        content = "You are a helpful assistant!"
                    ),
                    ChatMessage(
                        role = ChatRole.User,
                        content = "Hello!"
                    )
                )
            )

            val completion: ChatCompletion = openAI.chatCompletion(chatCompletionRequest)
            val responseContent = completion.choices.first().message?.content
            Log.d("AiActivity", "Completion: $completion")
            Log.d("AiActivity", "Response: $responseContent")

        }
    }
}

