package com.example.billboostapp.Fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.billboostapp.BillModel
import com.example.billboostapp.ClickInterface
import com.example.billboostapp.EditClickInterface
import com.example.billboostapp.databinding.LayoutBillsBinding



class BillAdapter(var arrayList: ArrayList<BillModel>, var clickListener: EditClickInterface)  : RecyclerView.Adapter<BillAdapter.ViewHolder>(){
    class ViewHolder(var binding: LayoutBillsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindData(userModel: BillModel, position: Int, clickListener: EditClickInterface){
            binding.billModel = userModel
            binding.position = position
            binding.clickListener = clickListener

            binding.ivEdit.setOnClickListener {
                clickListener.onClick(position)
            }
            binding.ivView.setOnClickListener{
                clickListener.onViewClick(position)
            }
            binding.ivDelete.setOnClickListener{
                clickListener.onDelete(position, BillModel())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutBillsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(arrayList[position], position, clickListener)

    }

    override fun getItemCount(): Int =arrayList.size
}