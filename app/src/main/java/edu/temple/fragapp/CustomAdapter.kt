package edu.temple.fragapp

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class CustomAdapter (_context: Context, _items: Array<String>) : BaseAdapter (){

    val context = _context
    val items = _items

    override fun getCount(): Int {
        return items.size + 1
    }

    override fun getItem(position: Int): Any {
        return items[position - 1]
    }

    override fun getItemId(position: Int): Long {
        return (position - 1).toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val textView : TextView by lazy {
            if (convertView is TextView)
                convertView
            else
                TextView(context)
        }

        return textView.apply {
            textSize = 32f
            text = if (position == 0)
                "Please select an color"
            else
                items[position - 1]
        }
    }
}