package com.example.kotlinxserializationexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.kotlinxserializationexample.model.Dept
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@ExperimentalSerializationApi
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        makeDeptToJson()
    }

    private fun makeDeptToJson() {
        val dept = Dept(1, "Marketing", "USA/Seattle")  // data class 생성
        val deptJson = Json.encodeToString(dept)    // data class -> Json 으로 변환
        Log.d(TAG, deptJson)    // D/MainActivity: {"no":1,"name":"Marketing","location":"USA/Seattle"}
    }

    companion object {
        const val TAG = "MainActivity"
    }
}
