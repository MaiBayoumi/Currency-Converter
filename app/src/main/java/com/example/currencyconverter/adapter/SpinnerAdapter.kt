package com.example.currencyconverter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.currencyconverter.R
import com.example.currencyconverter.models.Currency
import com.squareup.picasso.Picasso

class SpinnerAdapter(var list: List<Currency>) : BaseAdapter() {
    override fun getCount(): Int = list.size

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val itemView: View = convertView
            ?: LayoutInflater.from(parent?.context).inflate(R.layout.item_spinner_c, parent, false)

        // Find the views using findViewById
        val tvItemSpinner = itemView.findViewById<TextView>(R.id.tv_item_spinner)
        val imageSpinnerItem = itemView.findViewById<ImageView>(R.id.image_spinner_item)

        // Set the text for the spinner item
        tvItemSpinner.text = list[position].code

        // Set the image using Picasso or a resource
        val str = "https://nbu.uz/local/templates/nbu/images/flags/"
        val str1 = ".png"
        if (position == list.size - 1) {
            imageSpinnerItem.setImageResource(R.drawable.flag_uzb)
        } else {
            Picasso.get().load("$str${list[position].code}$str1")
                .into(imageSpinnerItem)
        }

        return itemView
    }
}
