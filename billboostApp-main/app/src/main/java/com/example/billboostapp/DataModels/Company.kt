package com.example.billboostapp.DataModels

import java.io.Serializable

data class Company(
    var name:String?=null,
    var contact:String?=null,
    var email:String?=null,
    var invoiceStartingLetters: String?= null,
    var address:String?=null,
    var invoiceNumber: Int= 0,
    var id:String?=""
): Serializable {
    override fun toString(): String {
        return name.toString()
    }


}
