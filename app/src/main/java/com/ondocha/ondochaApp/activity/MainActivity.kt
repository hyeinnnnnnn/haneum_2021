/**
 * 메인화면
 * 파이어스토어 데이터구조도 확인
 * 개인 ID를 쓴다면 파이어스토어 "Root" 를 ID로 변경
 * 리스트뷰는 리사이클러뷰로 변경
 * 예외처리 안함
 * 알림 --> Firebase Function -> index.js 업로드
 * 패키지 이름 모두 소문자로 변경,,
 */

package com.ondocha.ondochaApp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
import com.ondocha.ondochaApp.*
import kotlinx.android.synthetic.main.activity_initialsetname_dialog.*
import kotlinx.android.synthetic.main.activity_setting.*
import java.util.*

class MainActivity : AppCompatActivity() {
    val DB = FirebaseFirestore.getInstance().collection("Root")
    val TAG = "MainActivity"
    var todaydoc : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Firebase.messaging.isAutoInitEnabled = true
        val calen = Calendar.getInstance()
        val year : String = calen.get(Calendar.YEAR).toString()
        val month : String = if((calen.get(Calendar.MONTH)+1) < 10) {
            "0" + (calen.get(Calendar.MONTH) + 1).toString()
        } else {
                (calen.get(Calendar.MONTH) + 1).toString()
        }
        val day : String = if((calen.get(Calendar.DATE)+1) < 10) {
            "0" + (calen.get(Calendar.DATE) + 1).toString()
        } else {
                (calen.get(Calendar.DATE) + 1).toString()
        }
        todaydoc = year+month+day
        date.text = (year + "년 " + month + "월 " + day + "일")
        DB.document("Name").get().addOnSuccessListener { document ->
            main_name.text = document["name"].toString()
            val mainname : String = document["name"].toString()
            if(mainname == "NAME") {
                val dialog = InitnameDialog(this)
                dialog.showDialog()
                dialog.setOnClickListener(object : InitnameDialog.OnDialogClickListener {
                    override fun onClicked(name: String) {
                        main_name.text = name
                        val map = mutableMapOf<String,Any>()
                        map["name"] = name
                        DB.document("Name").update(map).addOnSuccessListener { Toast.makeText(this@MainActivity,"지점명이 설정되었습니다.",Toast.LENGTH_SHORT).show() }
                    }
                })
            }
        }

        gdata()
        /** 버튼 클릭리스너 start **/
        recordbutton.setOnClickListener {
            startActivity(Intent(this, RecordActivity::class.java))
        }
        setting.setOnClickListener {
            startActivity(Intent(this, SettingActivity::class.java))
        }
        reload.setOnClickListener {
            gdata()
        }
        /** 버튼 클릭리스너 end **/

        /** 디바이스 토큰 받아오기    **/
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            val token = hashMapOf(
                "tokens" to task.result
            )
            DB.document("token").set(token)
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
        })
    }

    private fun gdata() {

        val items = mutableListOf<MainVisitorListItem>()
        val adapter = MainVisitorAdaptor(items)
        visitor_list.adapter = adapter
        DB.document("Visitor_Info").collection(todaydoc).get()
            .addOnSuccessListener { result ->
                items.clear()
                for (document in result) {
                    val atem = document["Atem"].toString()
                    val ftem = document["Ftem"].toString()
                    val tem : String = if(atem.toDouble() >= 37.5) { ftem }
                        else { atem }
                    val item = result.toObjects(MainVisitorListItem::class.java)
                    item.forEach {
                        items.add(0,it)
                    }
                }
                visitorNum.text = items.size.toString()
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.e("MainActivity", "Error getting documents: $exception")
            }
    }
}

