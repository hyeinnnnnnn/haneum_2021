package com.ondocha.ondochaApp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_record.*
import com.ondocha.ondochaApp.R
import com.ondocha.ondochaApp.RecordInfoListItem

class RecordActivity : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance().collection("Root")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)
        val TAG = "Record"
        val items = mutableListOf<RecordInfoListItem>()
        val adapter = com.ondocha.ondochaApp.RecordInfoAdapter(items)
        record_list.adapter = adapter

        db.document("Suspected").collection("Info").get()
            .addOnSuccessListener { result ->
                items.clear()
                val item = result.toObjects(RecordInfoListItem::class.java)
                item.forEach {
                    items.add(0,it)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.e("RecordActivity", "Error getting documents: $exception")
            }

        back_record.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        record_list.setOnItemClickListener { parent: AdapterView<*>, view: View, position: Int, id: Long ->
            val item = parent.getItemAtPosition(position) as RecordInfoListItem
            val rCode = item.Code+"_"+item.date+"_"+item.time
            Log.d(TAG, item.Code.toString())

            val memoIntent = Intent(this, RecordMemoActivity::class.java)
            memoIntent.putExtra("code",rCode)
            startActivity(memoIntent)
        }

    }
}