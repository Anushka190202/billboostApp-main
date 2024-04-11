package com.example.billboostapp.Fragments

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.billboostapp.Activities.AddInvoice
import com.example.billboostapp.Activities.InvoiceTemplate
import com.example.billboostapp.Adapters.InvoiceAdapter
import com.example.billboostapp.AddBillActivity
import com.example.billboostapp.BillModel
import com.example.billboostapp.EditClickInterface
import com.example.billboostapp.databinding.FragmentHomeBinding
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Calendar

class HomeFragment : Fragment(), EventListener<QuerySnapshot> {
    lateinit var binding: FragmentHomeBinding
    var billModel = arrayListOf<BillModel>()
    lateinit var adapter: BillAdapter
    var collectionName = "bills"
    var selectedBillModel : BillModel = BillModel()
    var simpleDateFormat = SimpleDateFormat("dd/MM/YYYY")
    var startDate = Calendar.getInstance()
    var endDate = Calendar.getInstance()
    lateinit var dbCollection: CollectionReference
    lateinit var query: Query
    var db = Firebase.firestore
    var totalAmount = 0.0
    var pendingAmount = 0.0
    lateinit var listener: ListenerRegistration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentHomeBinding.inflate(layoutInflater)
        binding.rvInvoice.layoutManager=LinearLayoutManager(requireContext())
//        var names= listOf<String>("Name 1","Name 2","Name 3")
//        var adapter = InvoiceAdapter(names)
//        binding.rvInvoice.adapter=adapter

          db.collection(collectionName).get().addOnSuccessListener {
              binding.loader.hide()
              for(snapshot in it.documentChanges){
                  var userModel = BillModel()
                  userModel = snapshot.document.toObject(BillModel::class.java)
                  userModel.id = snapshot.document.id
                  println("BillModel: $userModel")
                  billModel.add(userModel)
                  adapter.notifyDataSetChanged()


              }
          }.addOnFailureListener {
              Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT).show()
          }
//        query = dbCollection.orderBy("timeStamp" )
//        query = dbCollection.orderBy("timeStamp")
//            .whereGreaterThanOrEqualTo("timeStamp", Timestamp(startDate.time))
//            .whereLessThanOrEqualTo("timeStamp", Timestamp(endDate.time))
//        listener = query.addSnapshotListener(this)


        binding.fbAdd.setOnClickListener{
            var intent= Intent(this@HomeFragment.requireContext(),AddBillActivity::class.java)
            startActivity(intent)
        }
        adapter = BillAdapter(billModel, object :EditClickInterface{
            override fun onClick(position : Int) {
                var intent= Intent(this@HomeFragment.requireContext(),AddBillActivity::class.java)
                var bundle = Bundle()
                bundle.putString("billModel",convertJsonToString(billModel[position]))
                intent.putExtras(bundle)
                startActivity(intent)
            }

            override fun onViewClick(position: Int) {
                var intent= Intent(this@HomeFragment.requireContext(),InvoiceTemplate::class.java)
                var bundle = Bundle()
                bundle.putString("billModel",convertJsonToString(billModel[position]))
                intent.putExtras(bundle)
                startActivity(intent)
            }

        })

        binding.rvInvoice.layoutManager = LinearLayoutManager(requireContext())
        binding.rvInvoice.adapter = adapter
        return binding.root
    }
    fun convertJsonToString(obj : BillModel) : String {
        val json = Gson().toJson(obj)
        return json
    }

    override fun onEvent(snapshots: QuerySnapshot?, error: FirebaseFirestoreException?) {
        if (error != null) {
            return
        }
        for (snapshot in snapshots!!.documentChanges) {
            val userModel = convertObject(snapshot.document)
            Log.e(TAG, " userModel $userModel")
            when (snapshot.type) {
                DocumentChange.Type.ADDED -> {
                    userModel?.let { billModel.add(it) }
                    totalAmount += (userModel?.total ?: 0.0)
                    if (userModel?.isPaid == false)
                        pendingAmount += (userModel?.balanceAmount ?: 0.0)
                    Log.e(TAG, "userModelList ${billModel.size}")
                }

                DocumentChange.Type.MODIFIED -> {
                    userModel?.let {
                        var index = getIndex(userModel)
                        if (index > -1) {
                            totalAmount = totalAmount - (billModel[index].total?:0.0)
                            pendingAmount = pendingAmount - (billModel[index].balanceAmount?:0.0)
                            billModel.set(index, it)
                            totalAmount = totalAmount + (billModel[index].total?:0.0)
                            pendingAmount = pendingAmount + (billModel[index].balanceAmount?:0.0)


                        }
                    }
                }

                DocumentChange.Type.REMOVED -> {
                    userModel?.let {
                        var index = getIndex(userModel)
                        if (index > -1) {
                            totalAmount = totalAmount - (billModel[index].total?:0.0)
                            pendingAmount = pendingAmount - (billModel[index].balanceAmount?:0.0)
                            billModel.removeAt(index)

                        }
                    }
                }
            }
            adapter?.notifyDataSetChanged()
        }

//        binding.isEmpty = billModel.isNullOrEmpty()
//        binding.isLoading = false
    }


    private fun getIndex(userModel: BillModel): Int {
        var index = -1
        index = billModel.indexOfFirst { element ->
            element.id?.equals(userModel.id) == true
        }
        return index
    }

    fun convertObject(snapshot: QueryDocumentSnapshot): BillModel? {
        val userModel: BillModel? =
            snapshot.toObject(BillModel::class.java)
        userModel?.id = snapshot.id ?: ""
        return userModel
    }

}