package com.example.billboostapp.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.billboostapp.databinding.ActivityInvoiceTemplateBinding

class InvoiceTemplate : AppCompatActivity() {
    lateinit var binding:ActivityInvoiceTemplateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInvoiceTemplateBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }

}