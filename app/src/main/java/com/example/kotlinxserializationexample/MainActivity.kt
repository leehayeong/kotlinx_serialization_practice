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
        makeDeptIgnoreJson()
        makeCoerceJson()
        makeEncodeDefaultJson()
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

    private fun makeDeptIgnoreJson() {
        val json = Json { ignoreUnknownKeys = true }
        val deptJson = """ {"no":"1","name":"Marketing","location":"USA/Seattle","nickName":"Wow!!!"} """
        val deptFromJson = json.decodeFromString<Dept>(deptJson)
        Log.d(TAG, deptFromJson.toString()) // ept(no=1, name=Marketing, location=USA/Seattle)
    }

    /**
     * Coercing input values (decoding: json -> data class)
     * 강력한 type check 를 느슨하게 조정 (default 값 할당)
     * - non null property 에 null 이 입력될 경우
     * - enum 값을 담는 property 에 정해지지 않은 enum 이 들어오는 경우
     */
    private fun makeCoerceJson() {
        val json = Json { coerceInputValues = true }
        val deptJson = """ {"no":"1","name":"Marketing","location":null} """
        val deptFromJson = json.decodeFromString<Dept>(deptJson)
        Log.d(TAG, deptFromJson.toString())
        // default 값이 model 에 지정되어 있어야 null 이 들어와도 default 값으로 들어감
        // D/MainActivity: Dept(no=1, name=Marketing, location=default)
        // ** coerceInputValues 옵션이 설정되어있지 않으면? 에러남
    }

    /**
     * Encoding defaults (encoding: data class -> json)
     * 값이 설정되지 않도라도 data class 의 default 값이 json 에 기본값으로 출력되도록 하기 위해 사용
     */
    private fun makeEncodeDefaultJson() {
        val json = Json { encodeDefaults = true }
        val dept = Dept(no = 1, name = "Marketing")
        val deptFromJson = json.encodeToString(dept)
        Log.d(TAG, deptFromJson)
        // Dept 는 no, name 만 선언하고 이상태로 json 으로 encoding 하면 data class 의 location default 값이 나옴
        // D/MainActivity: {"no":1,"name":"Marketing","location":"default"}
        // ** encodeDefaults 옵션이 설정되어있지 않으면? 값이 없는 property 는 json 에 포함되지 않음 -> D/MainActivity: {"no":1,"name":"Marketing"}
    }

    companion object {
        const val TAG = "MainActivity"
    }
}
