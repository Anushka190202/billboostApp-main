package com.example.billboostapp.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.billboostapp.Adapters.CompanyAdapter
import com.example.billboostapp.DataModels.Company
import com.example.billboostapp.R
import com.example.billboostapp.clickInterface.CompanyListInterface
import com.example.billboostapp.databinding.FragmentCompanyBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class CompanyFragment : Fragment(), CompanyListInterface {
    lateinit var binding: FragmentCompanyBinding
    var db = Firebase.firestore
    lateinit var companyAdapter: CompanyAdapter
    var companyList = ArrayList<Company>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCompanyBinding.inflate(layoutInflater)
        binding.fbAdd.setOnClickListener {
            findNavController().navigate(R.id.addCompany)
        }
        var layoutManager = LinearLayoutManager(requireContext())
        binding.rvCompany.layoutManager = layoutManager
        companyAdapter = CompanyAdapter(companyList, this)
        val docRef = db.collection("companies")
        docRef.get().addOnSuccessListener { documents ->
            binding.loader.hide()
            for (document in documents) {
                var company = document.toObject<Company>()
                company.id = document.id
                companyList.add(company)
                Toast.makeText(
                    requireContext(),
                    "${document.id} => ${document.data}",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d("TAG", "${document.id} => ${document.data}")
            }
            companyAdapter.notifyDataSetChanged()
            binding.rvCompany.adapter = companyAdapter
        }.addOnFailureListener {
            Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT).show()
            Log.e("exception in firebase", it.message.toString())
        }
        return binding.root
    }


    override fun onUpdate(company: Company) {
        println("onUpdate:$company")
        var bundle = Bundle()
        bundle.putSerializable("model", company)
        findNavController().navigate((R.id.updateCompanyFragment),bundle)

    }

    override fun onDelete(company: Company){
        db.collection("company").document(company.id.toString())
            .delete().addOnSuccessListener {
                Toast.makeText(requireContext(),"company deleted!!",Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(requireContext(),"company not deleted!!",Toast.LENGTH_SHORT).show()
            }
    }

}

