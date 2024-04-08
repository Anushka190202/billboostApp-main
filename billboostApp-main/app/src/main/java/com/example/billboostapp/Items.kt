package com.example.billboostapp

import com.example.billboostapp.DataModels.Clients
import com.example.billboostapp.DataModels.Company
import com.google.firebase.Timestamp
import java.io.Serializable

data class BillModel(
    var clientsModel: Clients? = null,
    var companyModel: Company? = null,
    var date: String? = null,
    var subTotal: Double? = 0.0,
    var gst: Double? = 0.0,
    var total: Double? = 0.0,
    var ammountPaid: Double? = 0.0,
    var billItem: ArrayList<Items>? = arrayListOf(),
    var billpdf:String?=null,
    var id: String? = null,
    var invoiceNumber: Int? = null,
    var accountName: String? = null,
    var accountNumber: String? = null,
    var referenceNumber: String? = null,
    var timeStamp : Timestamp? = null,
    var balanceAmount : Double? = 0.0,
    var isPaid : Boolean? = false,
    var paymentDate : String? = "",
    var isDeductionApplied : Boolean = false,
    var deductionAmount : Double = 0.0,
    var deductionPercent : Double = 0.0,
    var note: String?= null,
    var newInvoiceString: String?= null,
): Serializable

data class Items(

//    var sno:Int?=null,
    var itemName:String?=null,
    var quantity:Int?=0,
    var description: String? = null,
    var price_per_item:Double?= 0.0,
//    var total:String?=null,
){
    var price : Double ?= 0.0
        get() {
            return (price_per_item?:0.0).times(quantity?:0)
        }
}
