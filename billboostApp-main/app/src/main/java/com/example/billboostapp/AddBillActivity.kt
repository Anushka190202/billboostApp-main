package com.example.billboostapp

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.billboostapp.Activities.HomeActivity

import com.example.billboostapp.DataModels.Clients
import com.example.billboostapp.DataModels.Company
import com.example.billboostapp.DataModels.CustomizationModel
import com.example.billboostapp.databinding.ActivityAddBillBinding
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.firestore
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddBillActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddBillBinding
    lateinit var clientAdapter: ArrayAdapter<Clients>
    var clientList = arrayListOf<Clients>()
    var collectionName = "bills"
    var db = Firebase.firestore
    var customizationModel = CustomizationModel()

    lateinit var companyAdapter: ArrayAdapter<Company>
    var companyList = arrayListOf<Company>()


    var billsItem = arrayListOf<Items>(Items())
    lateinit var billsItemAdapter: BillItemsAdapter
    //    var billList= arrayListOf<BillModel>()
    var cal = Calendar.getInstance()
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.US)
    var billId = ""
    var billModel = BillModel()
    var selectedCompany : Company = Company()
    var selectedClient : Clients = Clients()
    private val TAG = "BillingFragment"
    var gst = 0.0
    var deductionPercent = 0.0
    var deductionAmount = 0.0
    var userBillModel = BillModel()
    var view: View? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBillBinding.inflate(layoutInflater)
        setContentView(binding.root)
            println("UserBillModelUpdate: $userBillModel")

        binding.cbGstPst.setOnCheckedChangeListener { buttonView, isChecked ->
            calculateTotal()
        }
        clientList.clear()
        companyList.clear()

        db.collection("clients")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var client = document.toObject(Clients::class.java)
                    client.id = document.id
                    clientList.add(client)
                }
                clientAdapter =
                    ArrayAdapter(this, android.R.layout.simple_list_item_1, clientList)
                binding.tvClientName.adapter = clientAdapter

                if(billModel.id.isNullOrEmpty() == false){
                    var index = clientList.indexOfFirst { element-> element.id == billModel.clientsModel?.id }
                    binding.tvClientName.setSelection(index, false)
                }
            }

        binding.tvClientName.onItemSelectedListener=object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                selectedClient = p0?.getItemAtPosition(position) as Clients
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

                db.collection("companies")
                    .get()
                    .addOnSuccessListener { result ->
                        for (document in result) {
                            var company = document.toObject(Company::class.java)
                            company.id = document.id
                            companyList.add(company)
                        }
                        companyAdapter =
                            ArrayAdapter(this, android.R.layout.simple_list_item_1, companyList)
                        binding.spCompanyName.adapter = companyAdapter

                        if(billModel.id.isNullOrEmpty() == false){
                            var index = companyList.indexOfFirst { element-> element.id == billModel.companyModel?.id }
                            binding.spCompanyName.setSelection(index, false)

                        }else if(companyList.isNotEmpty()){
                            binding.etInvoiceNo.setText((companyList[0].invoiceNumber+1).toString())
                        }
                    }
        binding.spCompanyName.onItemSelectedListener=object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                selectedCompany=p0?.getItemAtPosition(position) as Company

//                mainActivity.userModelList.add(CompaniesModel(name = selectedCompany.toString(),address = companyList[position].address, logo = companyList[position].logo))
                Log.e("companylist", "onItemSelected: ${companyList}")
                if(billModel.id.isNullOrEmpty() == true){
                    binding.etInvoiceNo.setText((selectedCompany.invoiceNumber+1).toString())
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        binding.etPickUpDate.text = sdf.format(cal.time)
                        Log.e("billarraylist", "onViewCreated: ${billsItem}",)
                        binding.etPickUpDate.setOnClickListener {
                            DatePickerDialog(
                                this,
                                { _, year, month, date ->
                                    cal.set(Calendar.YEAR, year)
                                    cal.set(Calendar.MONTH, month)
                                    cal.set(Calendar.DAY_OF_MONTH, date)
                                    binding.etPickUpDate.text = sdf.format(cal.time)
                                },
                                cal.get(Calendar.YEAR),
                                cal.get(Calendar.MONTH),
                                cal.get(Calendar.DATE),
                            ).show()
                        }
                        binding.btnAddItem.setOnClickListener {
                            billsItem.clear()
                            billsItem.addAll(billsItemAdapter.getList())
                            if (billsItem.last().price_per_item == 0.0) {
                                Toast.makeText(this, "Enter the Price", Toast.LENGTH_SHORT).show()
                                return@setOnClickListener
                            } else if (billsItem.last().description.isNullOrEmpty()) {
                                Toast.makeText(this, "Enter the Description", Toast.LENGTH_SHORT)
                                    .show()
                            } else if (billsItem.last().quantity == 0) {
                                Toast.makeText(this, "Enter the Qty", Toast.LENGTH_SHORT).show()
                            }
                            billsItemAdapter.addItem()
                            Toast.makeText(this, "value added", Toast.LENGTH_SHORT).show()
                        }
                        billsItemAdapter = BillItemsAdapter(object : ClickInterface {
                            override fun onClick(position: Int, clickType: ClickType?): Boolean {
                                when (clickType) {
                                    ClickType.EDIT -> {
                                        calculateTotal()
                                    }

                                    ClickType.DELETE -> {
                                        if (position == 0) {
                                            Toast.makeText(
                                                this@AddBillActivity,
                                                "You can only edit this item",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        } else {
                                            AlertDialog.Builder(this@AddBillActivity).apply {
                                                setTitle(resources.getString(R.string.delete_alert))
                                                //name from the model is passed to string.xml file key to show there with text
                                                setTitle(
                                                    resources.getString(
                                                        R.string.delete_message,
                                                        billsItem[position].itemName
                                                    )
                                                )
                                                setPositiveButton(resources.getString(R.string.yes)) { _, _ ->
                                                    billsItem.removeAt(position)
                                                    billsItemAdapter.removeItem(
                                                        position,
                                                        binding.tvTotal
                                                    )
                                                    billsItemAdapter.notifyItemRemoved(position)

                                                    Log.e("remove", "onClick: ${billsItem}",)
//                                deleting the particular collection from firestore
//                                mainActivity.db.collection(collectionName)
//                                    .document(productModelList[position].productId ?: "").delete()
                                                }

                                                setNegativeButton(resources.getString(R.string.no)) { _, _ -> }
                                                show()
                                            }
                                        }
//                        billsItem.removeAt(position)
//                        billsItemAdapter.notifyDataSetChanged()
                                    }

                                    else -> {}
                                }
                                return true
                            }

                            override fun onMenuClick(
                                position: Int,
                                clickType: ClickType?,
                                anchorView: View
                            ): Boolean {
                                return true
                            }
                        })

                        billsItemAdapter.addItem()
                        billsItemAdapter.notifyDataSetChanged()
                        binding.rvOrderDetail.apply {
                            layoutManager = LinearLayoutManager(this@AddBillActivity)
                            adapter = billsItemAdapter
                        }

                        binding.btnAddOrder.setOnClickListener {
                            if (billsItem.last().quantity == 0) {
                                Toast.makeText(this, "Enter the Qty", Toast.LENGTH_SHORT).show()
                                return@setOnClickListener
                            } else if (billsItem.last().description.isNullOrEmpty()) {
                                Toast.makeText(this, "Enter the Description", Toast.LENGTH_SHORT)
                                    .show()
                                return@setOnClickListener
                            } else if (billsItem.last().price_per_item == 0.0) {
                                Toast.makeText(this, "Enter the Price", Toast.LENGTH_SHORT).show()
                                return@setOnClickListener
                            } else {
                                // var selectedClient = binding.tvClientName.selectedItem as ClientsModel
                                if (selectedClient.id.isNullOrEmpty()) {
                                    Toast.makeText(
                                        this,
                                        resources.getString(R.string.select_client),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    return@setOnClickListener
                                }
                                // var selectedCompany = binding.spCompanyName.selectedItem as CompaniesModel
                                if (selectedCompany.id.isNullOrEmpty()) {
                                    Toast.makeText(
                                        this,
                                        resources.getString(R.string.select_company),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    return@setOnClickListener
                                }
                                if (billsItemAdapter.getList().isEmpty()) {
                                    Toast.makeText(
                                        this,
                                        resources.getString(R.string.enter_items_in_bill),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    return@setOnClickListener
                                }

                                var total =
                                    String.format(
                                        "%.2f",
                                        binding.tvTotal.text.toString().toDouble()
                                    )
                                        .toDouble()
                                var amountPaid =
                                    String.format(
                                        "%.2f",
                                        binding.tvAmountPaid.text.toString().toDouble()
                                    )
                                        .toDouble()
                                if (amountPaid > total) {
                                    Toast.makeText(
                                        this,
                                        resources.getString(R.string.total_amount_is_less_than_amount_paid),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    return@setOnClickListener
                                }

                                var newBillModel = BillModel(
                                    clientsModel = selectedClient,
                                    companyModel = selectedCompany,
                                    billItem = billsItem,
                                    date = binding.etPickUpDate.text.toString(),

                                    subTotal = String.format(
                                        "%.2f",
                                        binding.tvItemSubTotal.text.toString().toDouble()
                                    ).toDouble(),
                                    gst = String.format(
                                        "%.2f",
                                        binding.tvGst.text.toString().toDouble()
                                    )
                                        .toDouble(),
                                    total = String.format(
                                        "%.2f",
                                        binding.tvTotal.text.toString().toDouble()
                                    )
                                        .toDouble(),
                                    ammountPaid = String.format(
                                        "%.2f",
                                        binding.tvAmountPaid.text.toString().toDouble()
                                    )
                                        .toDouble(),
                                )

                                if (billModel.id.isNullOrEmpty() == false) {
                                    newBillModel.invoiceNumber = billModel.invoiceNumber
                                    newBillModel.newInvoiceString = billModel.newInvoiceString
                                    newBillModel.id = billModel.id
                                    newBillModel.balanceAmount = billModel.balanceAmount
                                    newBillModel.timeStamp = billModel.timeStamp
                                    db.collection(collectionName)
                                        .document(billModel.id ?: "")
                                        .set(newBillModel)
                                        .addOnSuccessListener {
                                            AlertDialog.Builder(this@AddBillActivity).apply {
                                                setTitle(resources.getString(R.string.alert))
                                                setMessage(resources.getString(R.string.bill_updated_successfully))
                                                setNegativeButton(resources.getString(R.string.back)) { _, _ ->
                                                    startActivity(Intent(this@AddBillActivity,HomeActivity::class.java))
                                                    finish()
                                                }
                                                show()
                                            }
                                        }.addOnFailureListener {
                                            AlertDialog.Builder(this@AddBillActivity).apply {
                                                setTitle(resources.getString(R.string.alert))
                                                setMessage(resources.getString(R.string.sorry_cannot_add))
                                                setPositiveButton(resources.getString(R.string.ok)) { _, _ -> }
                                                show()
                                            }

                                        }
                                } else {
                                    newBillModel.invoiceNumber =
                                        binding.etInvoiceNo.text.toString().toInt()
                                    newBillModel.newInvoiceString =
                                        selectedCompany.invoiceStartingLetters + binding.etInvoiceNo.text.toString()
                                    newBillModel.timeStamp = Timestamp.now()
                                    newBillModel.balanceAmount = total - amountPaid
                                    db.collection(collectionName).add(newBillModel)
                                        .addOnSuccessListener {
                                            var invoiceId = it.id as? String
                                            Log.e(TAG, "invoice Id $invoiceId")
                                            customizationModel.invoiceNumber =
                                                binding.etInvoiceNo.text.toString().toInt()
                                            db.collection("companies")
                                                .document((selectedCompany.id ?: ""))
                                                .update(
                                                    "invoiceNumber",
                                                    ((selectedCompany?.invoiceNumber ?: 0) + 1)
                                                ).addOnSuccessListener {
                                                    AlertDialog.Builder(this@AddBillActivity)
                                                        .apply {
                                                            setTitle(resources.getString(R.string.alert))
                                                            setMessage(resources.getString(R.string.bill_added_successfully))
                                                            setNegativeButton(resources.getString(R.string.back)) { _, _ ->
                                                                startActivity(Intent(this@AddBillActivity,HomeActivity::class.java))
                                                                finish()
                                                            }
                                                            show()
                                                        }
                                                }

                                        }.addOnFailureListener {
                                            AlertDialog.Builder(this@AddBillActivity).apply {
                                                setTitle(resources.getString(R.string.alert))
                                                setMessage(resources.getString(R.string.sorry_cannot_add))
                                                setPositiveButton(resources.getString(R.string.ok)) { _, _ -> }
                                                show()
                                            }
                                        }
                                }
                            }
                        }
        intent.let {
            var bundle = it.extras
            var model = bundle?.getString("billModel", "")
            if (model != null) {
                billModel = convertStringToJSon(model)
                billsItem.addAll(billModel.billItem as ArrayList<Items>)
                billsItemAdapter.notifyDataSetChanged()
                updateLayout()
            }
        }
    }

    private fun updateLayout() {
        binding.etPickUpDate.setText(billModel.date)
        binding.btnAddOrder.setText("Update Order")
       // billModel.invoiceNumber?.let { binding.etInvoiceNo.setSelection(it) }
        binding.tvItemSubTotal.setText(billModel.subTotal.toString())
        binding.tvGst.setText(billModel.gst.toString())
        binding.tvTotal.setText(billModel.total.toString())
        binding.tvAmountPaid.setText(billModel.ammountPaid.toString())

        billsItemAdapter.addItems(billModel.billItem?: arrayListOf())
        if((billModel.gst?:0.0)>0.0){
            binding.cbGstPst.isChecked = true
        }
    }


    fun calculateTotal() {
        gst = 0.0
        deductionPercent = 0.0
        deductionAmount = 0.0
        billsItem.clear()
        billsItem.addAll(billsItemAdapter.getList())
        var total = billsItem.sumOf { it.price ?: 0.0 }
        binding.tvItemSubTotal.setText(total.toString())
        if (binding.cbGstPst.isChecked) {
            gst = total * (20f/100f)
        } else {
            gst = 0.0
        }
        var totalwithoutDeduction=(gst+total)-deductionAmount
        System.out.println("gst $gst (gst+ total) ${(gst+total)}")
        binding.tvGst.setText((String.format("%.2f", gst)).toString())
        println("GST: ${(String.format("%.2f", gst))}")
        binding.tvTotal.setText((String.format("%.2f", totalwithoutDeduction.toDouble())).toString())

    }
    public fun convertStringToJSon(value: String) : BillModel{
        val convertedModel = Gson().fromJson(value, BillModel::class.java)
        return convertedModel
    }
}