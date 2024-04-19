package com.example.billboostapp.Activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.billboostapp.BillItemsAdapter
import com.example.billboostapp.BillModel
import com.example.billboostapp.ClickInterface
import com.example.billboostapp.ClickType
import com.example.billboostapp.Items
import com.example.billboostapp.ShowBillItemsAdapter
import com.example.billboostapp.databinding.ActivityInvoiceTemplateBinding
import com.google.gson.Gson

class InvoiceTemplate : AppCompatActivity() {
    lateinit var binding:ActivityInvoiceTemplateBinding
    lateinit var showBillItemsAdapter: ShowBillItemsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        var billModel = BillModel()
        var billsItem = arrayListOf<Items>(Items())
        lateinit var billsItemAdapter: BillItemsAdapter
        super.onCreate(savedInstanceState)
        binding = ActivityInvoiceTemplateBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        var intentData = intent
        if(intent!=null) {
            var bundle = intent.extras
            var model = bundle?.getString("billModel", "")
            if (model != null) {
                billModel = convertStringToJSon(model)
                binding.tvIssueDate.setText(billModel.date.toString())
                binding.tvInvoiceNo.setText(billModel.invoiceNumber.toString())
                binding.tvClientName.setText(billModel.clientsModel?.name)
                binding.tvClientEmail.setText(billModel.clientsModel?.email)
                binding.tvClientAddress.setText(billModel.clientsModel?.address)
                binding.tvClientContact.setText(billModel.clientsModel?.contact)
                binding.tvCompanyName.setText(billModel.companyModel?.name)
                binding.tvCompanyEmail.setText(billModel.companyModel?.email)
                binding.tvCompanyAddress.setText(billModel.companyModel?.address)
                binding.tvCompanyContact.setText(billModel.companyModel?.contact)
                binding.tvTax.setText(billModel.gst.toString())
                binding.tvAmountPaid.setText(billModel.ammountPaid.toString())
                binding.tvSubtotal.setText(billModel.subTotal.toString())
                binding.tvTotal.setText(billModel.total.toString())
            }
        }
        showBillItemsAdapter = ShowBillItemsAdapter(billModel.billItem!!)
        binding.rvItems.layoutManager = LinearLayoutManager(this)
        binding.rvItems.adapter = showBillItemsAdapter

    }
}
public fun convertStringToJSon(value: String): BillModel {
    val convertedModel = Gson().fromJson(value, BillModel::class.java)
    return convertedModel
}