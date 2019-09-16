package com.example.jevodan.union.ui.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jevodan.union.R
import com.example.jevodan.union.data.model.MenuBasket

class MenuBasketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val stady = itemView.findViewById<TextView>(R.id.textView_stady)
    private val countStady = itemView.findViewById<TextView>(R.id.textView_count)

    fun bind(menuBasket: MenuBasket) = with(menuBasket) {
        stady.text = this.title
        countStady.text = this.countOrder.toString()
    }

}