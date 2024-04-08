package com.example.billboostapp.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.billboostapp.R
class InvoiceAdapter (var namesList:List<String>): RecyclerView.Adapter<InvoiceAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvInvoiceNo= view.findViewById<TextView>(R.id.tvName)
        var tvName= view.findViewById<TextView>(R.id.tvName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.invoice_list,parent, false)
        return InvoiceAdapter.ViewHolder(itemView)
    }
    override fun getItemCount(): Int {
        return namesList.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = namesList[position]
        holder.tvName.setText(item)
        holder.tvInvoiceNo.setText(item)

    }
}