package com.example.billboostapp.Activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.billboostapp.databinding.ActivityLoginBinding
import com.example.billboostapp.databinding.ActivitySignUpBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class SignUpActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpBinding
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        binding.tvLogin.setOnClickListener {
            var intent = Intent(this, ActivityLoginBinding::class.java)
            startActivity(intent)
        }
        binding.btnSignUp.setOnClickListener {
            if (TextUtils.isEmpty(binding.etEmail.text.toString())) {
                Toast.makeText(this, "Enter Email!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (TextUtils.isEmpty(binding.etPassword.text.toString())) {
                Toast.makeText(this, "Enter Password!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                val email = binding.etEmail.text.toString()
                val password = binding.etPassword.text.toString()
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            var intent = Intent(this, ActivityLoginBinding::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(
                                baseContext,
                                "Authentication failed!!" + task.exception,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }
    }
}