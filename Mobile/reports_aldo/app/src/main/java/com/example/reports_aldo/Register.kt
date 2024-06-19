package com.example.reports_aldo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.reports_aldo.R
import com.example.reports_aldo.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Register : AppCompatActivity() {

    // View Binding
    private lateinit var binding: ActivityRegisterBinding

    // Firebase Authentication
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize View Binding
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = Firebase.auth

        // Set click listener for the Sign-Up button
        binding.intermediateSignUp.setOnClickListener {
            signUpUser()
        }

//         Set click listener for the Redirect to Login text
        binding.arrowSignUp.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }

    private fun signUpUser() {
        val email = binding.editEmailSignUp.text.toString()
        val pass = binding.editPasswordSignUp.text.toString()
        val confirmPassword = binding.editConfirm.text.toString()

        // Check if email or password fields are blank
        if (email.isBlank() || pass.isBlank() || confirmPassword.isBlank()) {
            Toast.makeText(this, "Email and Password can't be blank", Toast.LENGTH_SHORT).show()
            return
        }

        // Check if passwords match
        if (pass != confirmPassword) {
            Toast.makeText(this, "Password and Confirm Password do not match", Toast.LENGTH_SHORT)
                .show()
            return
        }

        // Create user with email and password
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Successfully Signed Up", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Sign Up Failed!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
