package com.example.jevodan.union.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jevodan.union.R
import com.example.jevodan.union.data.model.MenuBasket

class MainAdapter : RecyclerView.Adapter<MenuBasketViewHolder>() {

    var menuBaskets: List<MenuBasket> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuBasketViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_menu_basket, parent, false)
        return MenuBasketViewHolder(view);
    }

    override fun getItemCount() = menuBaskets.size

    override fun onBindViewHolder(holder: MenuBasketViewHolder, position: Int): Unit {
       holder.bind(menuBaskets[position])
    }


}