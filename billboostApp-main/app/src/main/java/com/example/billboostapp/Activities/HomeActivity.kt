package com.example.billboostapp.Activities

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.billboostapp.R
import com.example.billboostapp.R.string.close
import com.example.billboostapp.R.string.open
import com.example.billboostapp.databinding.ActivityHomeBinding
import com.example.billboostapp.databinding.ActivityLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    lateinit var navController: NavController
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    var auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        actionBarDrawerToggle= ActionBarDrawerToggle(this, binding.dl, open, close)
        binding.dl.addDrawerListener(actionBarDrawerToggle)
        navController = findNavController(R.id.fragmentContainer)
        actionBarDrawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.navBar.setNavigationItemSelectedListener {
            navController.navigate(R.id.homeFragment)
            when(it.itemId){
                R.id.m_home->{
                    navController.navigate(R.id.homeFragment)
                }

                R.id.m_clients->{

                    navController.navigate(R.id.clientFragment)

                }
                R.id.m_company->{

                    navController.navigate(R.id.companyFragment)
                }
                R.id.m_logout->{
                    var builder = AlertDialog.Builder(this)
                    builder.setTitle("Logout")
                    builder.setMessage("Do you want to logout?")
                    builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
                        auth.signOut()
                        Toast.makeText(this, "logout successfully!!", Toast.LENGTH_SHORT).show()
                        var intent= Intent(this,ActivityLoginBinding::class.java)
                        startActivity(intent)
                        this.finish()
                    })
                    builder.setNegativeButton(
                        "No",
                        DialogInterface.OnClickListener { dialogInterface, i ->
                            dialogInterface.dismiss()
                        })

                    var dialog = builder.create()
                    dialog.setCancelable(false)
                    dialog.show()
                }
            }
            binding.dl.closeDrawer(GravityCompat.START)
            true
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item))
            return true
        return super.onOptionsItemSelected(item)
    }
}
