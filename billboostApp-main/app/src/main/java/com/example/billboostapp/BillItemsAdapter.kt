package com.example.billboostapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.billboostapp.databinding.AddItemLayoutBinding

class BillItemsAdapter(
    var clickListener: ClickInterface,
)
    : RecyclerView.Adapter<BillItemsAdapter.ViewHolder>(){
    public var arrayList: ArrayList<Items> = arrayListOf()
    inner class ViewHolder(var binding: AddItemLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindData(billItem: Items, position: Int, clickListener: ClickInterface){
            binding.billModel = billItem
            binding.position = position
            binding.clickListener = clickListener
//            billItem.quantity?.let { binding.tvQty.setText(it) }
//            billItem.description?.let { binding.tvDesc.setText(it) }
//            billItem.price_per_item?.let { binding.tvItemPrice.setText(it.toString()) }


            /*     binding.tvQty.doOnTextChanged { text, start, before, count ->
                     if(text.isNullOrEmpty() == false)
                         arrayList[position].qty = binding.tvQty.text.toString().toInt()

                 }*/
            binding.tvDesc.doOnTextChanged { text, start, before, count ->
                arrayList[position].description = binding.tvDesc.text.toString()
            }


            binding.tvQty.doAfterTextChanged {
                if(binding.tvQty.text.isNullOrEmpty() == false) {
                    arrayList[position].quantity = binding.tvQty.text.toString().toInt()
                    arrayList[position].price = (arrayList[position].price_per_item?:0.0).times(arrayList[position].quantity?:0)
                    clickListener.onClick(position, ClickType.EDIT)
                }
            }
            binding.tvItemPrice.doAfterTextChanged {
                if(binding.tvItemPrice.text.isNullOrEmpty() == false) {
                    arrayList[position].price_per_item = binding.tvItemPrice.text.toString().toDouble()
                    arrayList[position].price = (arrayList[position].price_per_item?:0.0).times(arrayList[position].quantity?:0)
                    clickListener.onClick(position, ClickType.EDIT)
                }
                binding.ivclose.setOnClickListener {
                    clickListener.onClick(position,ClickType.DELETE)
                }
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AddItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            bindData(arrayList[position], position, clickListener)
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