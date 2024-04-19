package com.example.billboostapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.billboostapp.databinding.AddItemLayout1Binding
import com.example.billboostapp.databinding.AddItemLayoutBinding

class ShowBillItemsAdapter(var arrayList: ArrayList<Items>)
    : RecyclerView.Adapter<ShowBillItemsAdapter.ViewHolder>(){

    inner class ViewHolder(var binding: AddItemLayout1Binding): RecyclerView.ViewHolder(binding.root) {
        fun bindData(billItem: Items){
            binding.tvQty.setText(billItem.quantity.toString())
            binding.tvDesc.setText(billItem.description)
            binding.tvItemPrice.setText(billItem.price_per_item.toString())
//            billItem.quantity?.let { binding.tvQty.setText(it) }
//            billItem.description?.let { binding.tvDesc.setText(it) }
//            billItem.price_per_item?.let { binding.tvItemPrice.setText(it.toString()) }


            /*     binding.tvQty.doOnTextChanged { text, start, before, count ->
                     if(text.isNullOrEmpty() == false)
                         arrayList[position].qty = binding.tvQty.text.toString().toInt()

                 }*/


            }


        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AddItemLayout1Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            bindData(arrayList[position])
        }


    }

    override fun getItemCount(): Int = arrayList.size

    fun addItem(){
        arrayList.add(Items())
        notifyItemInserted(arrayList.size)
    }

    fun removeItem(position: Int,view: View) {
        if (position >= 0 && position < arrayList.size) {
            arrayList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun addItems(array: ArrayList<Items>){
        arrayList.clear()
        arrayList.addAll(array)
        notifyDataSetChanged()
    }


    fun getList() : ArrayList<Items>{
        return arrayList
    }

    interface deleteHandler {
        fun deleteItem(position: Int)
    }
}