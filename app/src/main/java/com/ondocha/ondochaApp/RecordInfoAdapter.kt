package com.ondocha.ondochaApp

import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.BaseAdapter
import android.widget.TextView

class RecordInfoAdapter (val items: MutableList<RecordInfoListItem>): BaseAdapter() {
    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Any = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {

        val recordview: View = LayoutInflater.from(parent?.context).inflate(R.layout.record_list_item, null)

        val Recordlist : RecordInfoListItem = items[position]
        val date = recordview.findViewById<TextView>(R.id.text_date)
        val time = recordview.findViewById<TextView>(R.id.text_time)
        val temperature = recordview.findViewById<TextView>(R.id.text_temperature)

        val hour = Integer.parseInt(Recordlist.time!!) / 100
        val min = Integer.parseInt(Recordlist.time!!) % 100

        val year = Integer.parseInt(Recordlist.date!!) / 10000
        val month = (Integer.parseInt(Recordlist.date!!) % 10000 / 100)
        val day = Integer.parseInt(Recordlist.date!!) % 100
        date.text = ("$year/$month/$day")
        time.text = ("$hour : $min")
        temperature.text = Recordlist.Ftem.toString()



        return recordview
    }
}