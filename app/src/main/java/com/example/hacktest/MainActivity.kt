package com.example.hacktest

import SandboxResponse
import UrlRequest
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hacktest.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 버튼 클릭 이벤트 처리
        val buttonSend = findViewById<Button>(R.id.buttonSend)
        val editTextMessage = findViewById<EditText>(R.id.editTextMessage)

        buttonSend.setOnClickListener {
            val url = editTextMessage.text.toString()
            sendUrlToSandbox(url)
        }
    }

    private fun sendUrlToSandbox(url: String) {
        val apiService = ApiClient.apiService
        val call = apiService.analyzeUrl(UrlRequest(url))
        call.enqueue(object : Callback<SandboxResponse> {
            override fun onResponse(call: Call<SandboxResponse>, response: Response<SandboxResponse>) {
                if (response.isSuccessful) {
                    val result = response.body()?.result
                    Toast.makeText(this@MainActivity, "Analysis Result: $result", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this@MainActivity, "Error: ${response.message()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<SandboxResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Failed: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}
