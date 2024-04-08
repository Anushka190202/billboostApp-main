package com.example.billboostapp.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.billboostapp.DataModels.Clients
import com.example.billboostapp.R
import com.example.billboostapp.databinding.FragmentUpdateClientBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore


class UpdateClientFragment : Fragment() {
    lateinit var binding:FragmentUpdateClientBinding
    val db=Firebase.firestore

    var model = Clients()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            model = it.getSerializable("model") as Clients
            println("ClientData : $model")
            binding= FragmentUpdateClientBinding.inflate(layoutInflater)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (model.id!=null) {
            binding.etClient.setText(model.name.toString())
            binding.etClientContact.setText(model.contact.toString())
            binding.etClientEmail.setText(model.email.toString())
            binding.etClientAddress.setText(model.address.toString())
            binding.btnUpdate.setOnClickListener {
                val client = mapOf(
                    "name" to binding.etClient.text.toString(),
                    "contact" to binding.etClientContact.text.toString(),
                    "email" to binding.etClientEmail.text.toString(),
                    "address" to binding.etClientAddress.text.toString()
                )

                db.collection("clients").document(model.id.toString())
                    .update(client).addOnSuccessListener {
                        Toast.makeText(requireContext(), "client updated", Toast.LENGTH_SHORT)
                            .show()
                        findNavController().navigate(R.id.clientFragment)
                    }.addOnFailureListener {
                        Toast.makeText(requireContext(), "client not updated!!", Toast.LENGTH_SHORT)
                            .show()
                    }

            }
        }
        return binding.root
    }

}