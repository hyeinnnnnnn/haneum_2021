package com.ondocha.ondochaApp

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.BaseAdapter
import android.widget.TextView


class MainVisitorAdaptor(val items: MutableList<MainVisitorListItem>): BaseAdapter() {

    override fun getCount(): Int = items.size
    override fun getItem(position: Int): Any = items[position]
    override fun getItemId(position: Int): Long = position.toLong()
    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val visitorview: View = LayoutInflater.from(parent?.context).inflate(R.layout.visitor_list_item, null)
        val visitorlist : MainVisitorListItem = items[position]
        val count = visitorview.findViewById<TextView>(R.id.text_count)
        val time = visitorview.findViewById<TextView>(R.id.text_time)
        val temperature = visitorview.findViewById<TextView>(R.id.text_temperature)
        var size = items.size
        var hour = Integer.parseInt(visitorlist.time!!) / 100
        var min = Integer.parseInt(visitorlist.time!!) % 100
        var tem = visitorlist.Ftem?.toFloat()
        if (tem != null) {
            if(tem >= 37.5){
                visitorview.setBackgroundColor(Color.parseColor("#FFE9E9"))
            }
        }
        count.text = (size-position).toString()
        time.text = ("$hour : $min")
        temperature.text = visitorlist.Ftem
        return visitorview
    }
}