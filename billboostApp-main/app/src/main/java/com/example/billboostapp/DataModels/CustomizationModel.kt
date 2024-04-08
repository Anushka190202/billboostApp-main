package com.example.billboostapp.DataModels

data class CustomizationModel(
    var vat : Double =0.0,
    var invoiceNumber : Int = 0,
    var estimationNumber : Int = 0,
    var note : String = ""
)