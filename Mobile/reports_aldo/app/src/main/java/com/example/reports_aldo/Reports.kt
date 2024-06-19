package com.example.reports_aldo

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.reports_aldo.databinding.ActivityReportsBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.util.*

class Reports : AppCompatActivity() {

    private lateinit var binding: ActivityReportsBinding
    private lateinit var firestore: FirebaseFirestore
    private lateinit var storage: FirebaseStorage
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firestore = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()

        binding.buttonCapture.setOnClickListener {
            captureImage()
        }

        binding.buttonUpload.setOnClickListener {
            uploadData()
        }
    }

    private val captureImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageBitmap = result.data?.extras?.get("data") as Bitmap
            val uri = getImageUriFromBitmap(imageBitmap)
            binding.imageView.setImageBitmap(imageBitmap)
            imageUri = uri
        }
    }

    private fun captureImage() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        captureImageLauncher.launch(takePictureIntent)
    }

    private fun getImageUriFromBitmap(bitmap: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(contentResolver, bitmap, "TempImage", null)
        return Uri.parse(path)
    }

    private fun uploadData() {
        val description = binding.editTextDescription.text.toString()
        val urgency = binding.spinnerUrgency.selectedItem.toString()

        if (imageUri == null || description.isBlank()) {
            Toast.makeText(this, "Image and Description are required", Toast.LENGTH_SHORT).show()
            return
        }

        val fileName = UUID.randomUUID().toString()
        val storageRef = storage.reference.child("images/$fileName.jpg")
        val uploadTask = storageRef.putFile(imageUri!!)

        uploadTask.addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener { uri ->
                saveMetadata(uri.toString(), description, urgency)
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Upload failed: ${it.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveMetadata(imageUrl: String, description: String, urgency: String) {
        val data = hashMapOf(
            "imageUrl" to imageUrl,
            "description" to description,
            "urgency" to urgency,
            "timestamp" to System.currentTimeMillis()
        )

        firestore.collection("images")
            .add(data)
            .addOnSuccessListener {
                Toast.makeText(this, "Upload successful", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
