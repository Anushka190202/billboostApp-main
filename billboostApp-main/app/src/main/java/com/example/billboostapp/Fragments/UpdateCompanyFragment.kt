package com.example.billboostapp.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.billboostapp.DataModels.Company
import com.example.billboostapp.R
import com.example.billboostapp.databinding.FragmentUpdateCompanyBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class UpdateCompanyFragment : Fragment() {
    lateinit var binding: FragmentUpdateCompanyBinding
    val db= Firebase.firestore
    var model = Company()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentUpdateCompanyBinding.inflate(layoutInflater)
        arguments?.let {
            model=it.getSerializable("model")as Company

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (model.id != null) {
            binding.etCompany.setText(model.name.toString())
            binding.etCompanyContact.setText(model.contact.toString())
            binding.etCompanyEmail.setText(model.email.toString())
            binding.etCompanyAddress.setText(model.address.toString())
            binding.btnUpdate.setOnClickListener {
                val company = mapOf(
                    "name" to binding.etCompany.text.toString(),
                    "contact" to binding.etCompanyContact.text.toString(),
                    "email" to binding.etCompanyEmail.text.toString(),
                    "address" to binding.etCompanyAddress.text.toString()
                )

                db.collection("companies").document(model.id.toString())
                    .update(company).addOnSuccessListener {
                        Toast.makeText(requireContext(), "company updated", Toast.LENGTH_SHORT)
                            .show()
                        findNavController().navigate(R.id.companyFragment)
                    }.addOnFailureListener {
                        Toast.makeText(requireContext(), "company not updated!!", Toast.LENGTH_SHORT)
                            .show()
                    }

            }

        }
        // Inflate the layout for this fragment
        return binding.root
    }

}