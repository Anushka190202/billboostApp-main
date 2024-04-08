package com.example.billboostapp.Activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.billboostapp.databinding.ActivityLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class login : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var auth: FirebaseAuth
    public override fun onStart() {
        super.onStart()
        val currentUser=auth.currentUser
        if (currentUser != null) {
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        binding.tvSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        binding.btnLogin.setOnClickListener {
            if (TextUtils.isEmpty(binding.etEmail.text.toString())) {
                Toast.makeText(this, "Enter Email!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (TextUtils.isEmpty(binding.etPassword.text.toString())) {
                Toast.makeText(this, "Enter Password!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {

                val email = binding.etEmail.text.toString()
                val password = binding.etPassword.text.toString()
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            startActivity(Intent(this, HomeActivity::class.java))
                        } else {
                            Toast.makeText(
                                baseContext,
                                "Authentication Failed!!!",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
            }

        }



    }


}