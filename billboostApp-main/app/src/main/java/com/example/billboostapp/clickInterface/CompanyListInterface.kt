package com.example.billboostapp.clickInterface

import com.example.billboostapp.DataModels.Company

interface CompanyListInterface {
    fun onUpdate(company: Company)
    fun onDelete(company:Company)
}