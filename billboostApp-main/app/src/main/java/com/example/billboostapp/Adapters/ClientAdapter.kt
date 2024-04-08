package com.example.billboostapp.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.billboostapp.DataModels.Clients
import com.example.billboostapp.R
import com.example.billboostapp.clickInterface.ClientListInterface

class ClientAdapter(var clientList:List<Clients>, var clientInterface: ClientListInterface): RecyclerView.Adapter<ClientAdapter.ViewHolder>(){
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvName=view.findViewById<TextView>(R.id.tvName)
        var tvContact=view.findViewById<TextView>(R.id.tvContact)
        var tvEmail=view.findViewById<TextView>(R.id.tvEmail)
        var tvAddress=view.findViewById<TextView>(R.id.tvAddress)
        var delete=view.findViewById<ImageView>(R.id.delete)
        var update=view.findViewById<ImageView>(R.id.edit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.client_company_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
       return clientList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = clientList[position]
        holder.tvName.text = item.name
        holder.tvContact.text = item.contact
        holder.tvEmail.text = item.email
        holder.tvAddress.text = item.address

        holder.update.setOnClickListener{
            clientInterface.onUpdate(clientList[position])
        }
        holder.delete.setOnClickListener{
            clientInterface.onDelete(clientList[position])
        }
    }

}