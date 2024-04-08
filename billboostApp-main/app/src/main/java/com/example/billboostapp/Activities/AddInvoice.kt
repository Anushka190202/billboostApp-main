package com.example.billboostapp.Activities

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.billboostapp.DataModels.Clients
import com.example.billboostapp.DataModels.Company
import com.example.billboostapp.databinding.ActivityAddInvoiceBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
lateinit var binding: ActivityAddInvoiceBinding
class AddInvoice : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    var arrayList1 = ArrayList<Clients>()
    var arrayList2 = ArrayList<Company>()
    var db = Firebase.firestore
    lateinit var arrayAdapter1: ArrayAdapter<Clients>
    lateinit var arrayAdapter2: ArrayAdapter<Company>
    private var year = 0
    private var month = 0
    private var day = 0
    private lateinit var calendar: Calendar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddInvoiceBinding.inflate(layoutInflater)
        setContentView(binding.root)


        arrayAdapter1 = ArrayAdapter(
            this, android.R
                .layout.simple_list_item_1, arrayList1
        )
        binding.spClient.adapter = arrayAdapter1
        db.collection("clients").addSnapshotListener { value, error ->
            if (error != null) {
                return@addSnapshotListener
            }
            for (snapshot in value!!.documentChanges) {
                when (snapshot.type) {

                    com.google.firebase.firestore.DocumentChange.Type.ADDED -> {
                        var clientModel = snapshot.document.toObject(Clients::class.java)
                        arrayList1.add(clientModel)
                        println("ArrayList: $arrayList1")
                        arrayAdapter1.notifyDataSetChanged()
                    }

                    com.google.firebase.firestore.DocumentChange.Type.MODIFIED -> TODO()
                    com.google.firebase.firestore.DocumentChange.Type.REMOVED -> TODO()
                }
            }
        }

        arrayAdapter2 = ArrayAdapter(
            this, android.R
                .layout.simple_list_item_1, arrayList2
        )
        binding.spCompany.adapter = arrayAdapter2
        db.collection("companies").addSnapshotListener { value, error ->
            if (error != null) {
                return@addSnapshotListener
            }
            for (snapshot in value!!.documentChanges) {
                when (snapshot.type) {
                    com.google.firebase.firestore.DocumentChange.Type.ADDED -> {
                        var clientModel = snapshot.document.toObject(Company::class.java)
                        arrayList2.add(clientModel)
                        println("ArrayList: $arrayList2")
                        arrayAdapter2.notifyDataSetChanged()

                    }

                    com.google.firebase.firestore.DocumentChange.Type.MODIFIED -> TODO()
                    com.google.firebase.firestore.DocumentChange.Type.REMOVED -> TODO()
                }
            }

        }
//        binding.imageButton.setOnClickListener{
//            addNewView()
//
//        }

        binding.tvIssueDate.setOnClickListener {
            title = "KotlinApp"
            calendar = Calendar.getInstance()
            year = calendar.get(Calendar.YEAR)
            month = calendar.get(Calendar.MONTH)
            day = calendar.get(Calendar.DAY_OF_MONTH)
            val dialog = DatePickerDialog(this, { _, year, month, day_of_month ->
                calendar[Calendar.YEAR] = year
                calendar[Calendar.MONTH] = month + 1
                calendar[Calendar.DAY_OF_MONTH] = day_of_month
                val myFormat = "dd/MM/yyyy"
                val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
                binding.tvIssueDate.text = sdf.format(calendar.time)
            }, calendar[Calendar.YEAR], calendar[Calendar.MONTH], calendar[Calendar.DAY_OF_MONTH])
            dialog.datePicker.minDate = calendar.timeInMillis
            calendar.add(Calendar.YEAR, 0)
            dialog.datePicker.maxDate = calendar.timeInMillis
            dialog.show()
        }
        binding.tvDueDate.setOnClickListener {
            title = "KotlinApp"
            calendar = Calendar.getInstance()
            year = calendar.get(Calendar.YEAR)
            month = calendar.get(Calendar.MONTH)
            day = calendar.get(Calendar.DAY_OF_MONTH)
            val dialog = DatePickerDialog(this, { _, year, month, day_of_month ->
                calendar[Calendar.YEAR] = year
                calendar[Calendar.MONTH] = month + 1
                calendar[Calendar.DAY_OF_MONTH] = day_of_month
                val myFormat = "dd/MM/yyyy"
                val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
                binding.tvDueDate.text = sdf.format(calendar.time)
            }, calendar[Calendar.YEAR], calendar[Calendar.MONTH], calendar[Calendar.DAY_OF_MONTH])
            dialog.datePicker.minDate = calendar.timeInMillis
            calendar.add(Calendar.YEAR, 0)
            dialog.show()
        }
        binding.btnDone.setOnClickListener{
            var intent=Intent(this,InvoiceTemplate::class.java)
            startActivity(intent)
        }

        }
//    private fun addNewView() {
//        // this method inflates the single item layout
//        // inside the parent linear layout
//        val inflater = LayoutInflater.from(this).inflate(R.layout.add_item_layout,null)
//        binding.rvItem.addView(inflater, binding.rvItem.childCount)
//    }
    }

