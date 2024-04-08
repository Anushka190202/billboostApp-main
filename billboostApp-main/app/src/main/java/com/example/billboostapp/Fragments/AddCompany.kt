package com.example.billboostapp.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.billboostapp.R
import com.example.billboostapp.databinding.ActivityAddCompanyBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class AddCompany : Fragment() {
    lateinit var binding: ActivityAddCompanyBinding
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityAddCompanyBinding.inflate(layoutInflater)
        binding.btnAdd.setOnClickListener {
            val company = hashMapOf(
                "name" to binding.etCompany.text.toString(),
                "contact" to binding.etCompanyContact.text.toString(),
                "email" to binding.etCompanyEmail.text.toString(),
                "address" to binding.etCompanyAddress.text.toString(),
            )
            db.collection("companies")
                .add(company).addOnSuccessListener {
                    Toast.makeText(requireContext(), "company added", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(requireContext(), "company not added", Toast.LENGTH_SHORT).show()
                }
            findNavController().navigate(R.id.companyFragment)

        }


        return binding.root
    }
}