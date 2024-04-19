package com.example.billboostapp

import android.view.View
import com.example.billboostapp.DataModels.Company

interface ClickInterface {
    fun onClick(position: Int, clickType: ClickType ?= ClickType.EDIT): Boolean
    fun onMenuClick(position: Int, clickType: ClickType ?= ClickType.EDIT, anchorView: View) :Boolean


}

interface EditClickInterface {
    fun onClick(position: Int)
    fun onViewClick(position: Int)
    fun onDelete(position: Int,billModel: BillModel)
}

enum class ClickType{
    ADD, EDIT, DELETE,LongClick,SingleClick,Viewpdf, MORE
}

