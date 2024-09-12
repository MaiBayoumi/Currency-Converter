package com.example.currencyconverter.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.squareup.picasso.Picasso
import com.example.currencyconverter.R
import com.example.currencyconverter.models.Currency


class CardAdapter(val list: List<Currency>) : PagerAdapter() {
    override fun getCount(): Int = list.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    @SuppressLint("SetTextI18n")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        // Inflate the layout
        val layoutInflater =
            LayoutInflater.from(container.context).inflate(R.layout.item_vp_card, container, false)

        // Find the views by their IDs
        val tvDate = layoutInflater.findViewById<TextView>(R.id.tv_date)
        val tvOlishNarxi = layoutInflater.findViewById<TextView>(R.id.tv_olish_narxi)
        val tvSotishNarxi = layoutInflater.findViewById<TextView>(R.id.tv_sotish_narxi)
        val imageDollor = layoutInflater.findViewById<ImageView>(R.id.image_dollor)

        // Set the text for the date
        tvDate.text = list[position].date

        // Set the text for the prices, handle empty strings
        if (list[position].nbu_cell_price.isEmpty()) {
            tvOlishNarxi.text = "Mavjud emas"
        } else {
            tvOlishNarxi.text = "${list[position].nbu_cell_price} UZS"
        }

        if (list[position].nbu_buy_price.isEmpty()) {
            tvSotishNarxi.text = "Mavjud emas"
        } else {
            tvSotishNarxi.text = "${list[position].nbu_buy_price} UZS"
        }

        // Load the flag image using Picasso
        val str1 = "https://nbu.uz/local/templates/nbu/images/flags/"
        val s = ".png"
        Picasso.get().load("$str1${list[position].code}$s").into(imageDollor)

        // Add the inflated view to the container
        container.addView(layoutInflater)

        return layoutInflater
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val view = `object` as View
        container.removeView(view)
    }
}
