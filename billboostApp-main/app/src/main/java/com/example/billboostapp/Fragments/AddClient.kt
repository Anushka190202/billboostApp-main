package com.example.billboostapp.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.billboostapp.R
import com.example.billboostapp.databinding.ActivityAddClientBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class AddClient : Fragment() {
    lateinit var binding: ActivityAddClientBinding
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddClientBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.btnAdd.setOnClickListener {
            val client = hashMapOf(
                "name" to binding.etClient.text.toString(),
                "contact" to binding.etClientContact.text.toString(),
                "email" to binding.etClientEmail.text.toString(),
                "address" to binding.etClientAddress.text.toString(),
            )

            db.collection("clients")
                .add(client).addOnSuccessListener {
                    Toast.makeText(requireContext(), "client added", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(requireContext(), "client not added", Toast.LENGTH_SHORT).show()
                }
            findNavController().navigate(R.id.clientFragment)
        }

        return binding.root
    }

}