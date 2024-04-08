package com.example.billboostapp.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.billboostapp.Adapters.CompanyAdapter.ViewHolder
import com.example.billboostapp.DataModels.Company
import com.example.billboostapp.R
import com.example.billboostapp.clickInterface.CompanyListInterface

class CompanyAdapter (var companyList: List<Company>,var companyListInterface: CompanyListInterface):RecyclerView.Adapter<ViewHolder>() {
    class ViewHolder(view: android.view.View) : RecyclerView.ViewHolder(view) {
        var tvName=view.findViewById<TextView>(R.id.tvName)
        var tvContact=view.findViewById<TextView>(R.id.tvContact)
        var tvEmail=view.findViewById<TextView>(R.id.tvEmail)
        var tvAddress=view.findViewById<TextView>(R.id.tvAddress)
        var update=view.findViewById<ImageView>(R.id.edit)
        var delete=view.findViewById<ImageView>(R.id.delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.client_company_list, parent, false)
        return CompanyAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return companyList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = companyList[position]
        holder.tvName.text = item.name
        holder.tvContact.text = item.contact
        holder.tvEmail.text = item.email
        holder.tvAddress.text = item.address
        holder.update.setOnClickListener{
            companyListInterface.onUpdate(companyList[position])
        }
        holder.delete.setOnClickListener{
            companyListInterface.onDelete(companyList[position])
        }
    }
}