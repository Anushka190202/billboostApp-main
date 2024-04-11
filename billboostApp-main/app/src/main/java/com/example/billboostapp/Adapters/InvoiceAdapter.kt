package com.example.billboostapp.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.billboostapp.R
class InvoiceAdapter (var ItemList:List<String>): RecyclerView.Adapter<InvoiceAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvItemName= view.findViewById<TextView>(R.id.tvItemName)
        var tvprice= view.findViewById<TextView>(R.id.tvPrice)
        var tvquantity= view.findViewById<TextView>(R.id.tvQuantity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.invoice_list,parent, false)
        return InvoiceAdapter.ViewHolder(itemView)
    }
    override fun getItemCount(): Int {
        return ItemList.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = ItemList[position]
        holder.tvItemName.setText(item)
        holder.tvprice.setText(item)
        holder.tvquantity.setText(item)

    }
}