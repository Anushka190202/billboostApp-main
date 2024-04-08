package com.example.billboostapp.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.billboostapp.Items
import com.example.billboostapp.R



class ItemListAdapter(var items: ArrayList<Items>): RecyclerView.Adapter<ItemListAdapter.ViewHolder>() {
    class ViewHolder(view: android.view.View) : RecyclerView.ViewHolder(view) {
        var tvSno = view.findViewById<TextView>(R.id.tvSno)
        var tvItemName = view.findViewById<TextView>(R.id.tvItemName)
        var tvQuantity = view.findViewById<TextView>(R.id.tvQuantity)
        var tvPricePerItem = view.findViewById<TextView>(R.id.tvPrice)
        var tvTotal = view.findViewById<TextView>(R.id.tvTotal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val ItemView = LayoutInflater.from(parent.context).inflate(R.layout.item_list_layout, parent, false)
        return ViewHolder(ItemView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
//        holder.tvSno.setText((item.sno.toString()))
        holder.tvItemName.setText (item.itemName)
        holder.tvQuantity.setText (item.quantity.toString())
        holder.tvPricePerItem.setText(item.price_per_item.toString())


    }
}