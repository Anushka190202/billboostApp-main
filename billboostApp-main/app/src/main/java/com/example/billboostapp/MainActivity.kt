package com.example.billboostapp
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.billboostapp.Activities.login
import com.example.billboostapp.databinding.ActivityLoginBinding
import com.example.billboostapp.databinding.ActivityMainBinding
import com.example.billboostapp.databinding.ActivitySignUpBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Handler().postDelayed({
            startActivity(Intent(this, login::class.java))
        },5000)
    }
}