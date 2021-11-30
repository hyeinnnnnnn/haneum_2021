package com.ondocha.ondochaApp.activity

/**
 * 확진 의심자 기록- 메모 페이지
 */
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.ondocha.ondochaApp.R
import kotlinx.android.synthetic.main.activity_recordmemo.*

class RecordMemoActivity : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance().collection("Root").document("Suspected").collection("Info")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recordmemo)
        val recordCode = intent.getSerializableExtra("code").toString()

        db.document(recordCode).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    first_check.isChecked = (document["Confirmed"].toString() == "true")
                    second_check.isChecked = (document["Disinfection"].toString() == "true")
                    val mem = document["Memo"].toString()
                    recordMemo_edit.setText(mem)
                }
            }
            .addOnFailureListener { exception ->
                Log.d("RecordMemoActivity", "get failed with ", exception)
            }

        back_Memo.setOnClickListener {
            finish()
        }

        save_memo.setOnClickListener{
            val map = mutableMapOf<String, Any?>()
            map["Confirmed"] = first_check.isChecked
            map["Disinfection"] = second_check.isChecked
            map["Memo"] = (recordMemo_edit.text.toString())

            db.document(recordCode).update(map).addOnSuccessListener {
                startActivity(Intent(this, RecordActivity::class.java))
                Toast.makeText(this, "저장되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }

    }
}