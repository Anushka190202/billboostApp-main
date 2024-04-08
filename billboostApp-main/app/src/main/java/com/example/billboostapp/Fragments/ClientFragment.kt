package com.example.billboostapp.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.billboostapp.Adapters.ClientAdapter
import com.example.billboostapp.DataModels.Clients
import com.example.billboostapp.R
import com.example.billboostapp.clickInterface.ClientListInterface
import com.example.billboostapp.databinding.FragmentClientBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class ClientFragment: Fragment() ,ClientListInterface{
    lateinit var binding: FragmentClientBinding
    var db = Firebase.firestore
    lateinit var clientAdapter: ClientAdapter
    var clientList = ArrayList<Clients>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =FragmentClientBinding.inflate(layoutInflater)
        binding.fbAdd.setOnClickListener {
            findNavController().navigate(R.id.addClient)
        }
        var layoutManager = LinearLayoutManager(requireContext())
        binding.rvClients.layoutManager= layoutManager
        clientAdapter = ClientAdapter(clientList,this)
//

        val docRef = db.collection("clients")
        docRef.get().addOnSuccessListener {documents->
            binding.loader.hide()
            for (document in documents) {
                val client = document.toObject<Clients>()
                client.id = document.id
                clientList.add(client)

            }

            binding.rvClients.adapter= clientAdapter
        }.addOnFailureListener {
            Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT).show()

        }
        clientAdapter.notifyDataSetChanged()
        return binding.root
    }

    override fun onUpdate(clients: Clients) {
        println("onUpdate:$clients")
        var bundle = Bundle()
        bundle.putSerializable("model",clients)
        findNavController().navigate((R.id.updateClientFragment),
        bundle)

    }

    override fun onDelete(clients: Clients) {
        db.collection("clients").document(clients.id.toString())
            .delete()
            .addOnSuccessListener {Toast.makeText(requireContext(),"client deleted!!" ,Toast.LENGTH_SHORT).show() }
            .addOnFailureListener { Toast.makeText(requireContext(),"client not deleted!!" ,Toast.LENGTH_SHORT).show()  }
            }

}