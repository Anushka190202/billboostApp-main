package com.example.billboostapp

import android.view.View

interface ClickInterface {
    fun onClick(position: Int, clickType: ClickType ?= ClickType.EDIT): Boolean
    fun onMenuClick(position: Int, clickType: ClickType ?= ClickType.EDIT, anchorView: View) :Boolean

}

interface EditClickInterface {
    fun onClick(position: Int)


}
enum class ClickType{
    ADD, EDIT, DELETE,LongClick,SingleClick,Viewpdf, MORE
}

