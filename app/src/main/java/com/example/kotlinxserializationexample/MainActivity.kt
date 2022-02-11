package com.example.kotlinxserializationexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.kotlinxserializationexample.model.Dept
import com.example.kotlinxserializationexample.model.Dept2
import com.example.kotlinxserializationexample.model.Employee
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@ExperimentalSerializationApi
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        makeDeptToJson()
        makeJsonToDept()
        makeDept2Json()
    }

    private fun makeDeptToJson() {
        val dept = Dept(1, "Marketing", "USA/Seattle")  // data class 생성
        val deptJson = Json.encodeToString(dept)    // data class -> Json 으로 변환
        Log.d(
            TAG,
            deptJson
        )    // D/MainActivity: {"no":1,"name":"Marketing","location":"USA/Seattle"}
    }

    private fun makeJsonToDept() {
        val dept = Dept(1, "Marketing", "USA/Seattle")
        val deptJson = Json.encodeToString(dept)
        val deptFromJson = Json.decodeFromString<Dept>(deptJson) // json string -> data class 로 변환
        Log.d(TAG, deptFromJson.toString()) // Dept(no=1, name=Marketing, location=USA/Seattle)
    }

    private fun makeDept2Json() {
        val employees = listOf(
            Employee(0, "Smith"),
            Employee(1, "Mike"),
            Employee(2, "John")
        )

        val dept = Dept2(1, "Marketing", "USA/Seattle", employees)
        val deptJson = Json.encodeToString(dept)
        Log.d(TAG, deptJson)
        /**
         *  { "no":1,
         *    "name":"Marketing",
         *    "location":"USA/Seattle",
         *    "employees":
         *        [{"no":0,"name":"Smith"},
         *         {"no":1,"name":"Mike"},
         *         {"no":2,"name":"John"}
         *        ]
         *   }
         */

        val deptFromJson = Json.decodeFromString<Dept2>(deptJson)
        Log.d(TAG, deptFromJson.toString())
        /**
         *  Dept2(
         *      no=1,
         *      name=Marketing,
         *      location=USA/Seattle,
         *      employees=
         *          [Employee(no=0, name=Smith),
         *           Employee(no=1, name=Mike),
         *           Employee(no=2, name=John)]
         *  )
         */

        // Json Option 1. pretty Json
        val prettyJson = Json { prettyPrint = true }
        val deptPrettyJson = prettyJson.encodeToString(dept)
        Log.d(TAG, deptPrettyJson)

        val deptFromPrettyJson = prettyJson.decodeFromString<Dept2>(deptPrettyJson)
        Log.d(TAG, deptFromPrettyJson.toString())
    }

    companion object {
        const val TAG = "MainActivity"
    }
}
