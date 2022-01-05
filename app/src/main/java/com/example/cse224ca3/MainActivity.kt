package com.example.cse224ca3

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private lateinit var button: Button
    private lateinit var imageView: ImageView
    companion object{
        val IMAGE_REQUEST_CODE = 100
        val REQUEST_IMAGE_CAPTURE = 1
    }
    lateinit var requestGallery: ActivityResultLauncher<String>
    lateinit var requestCamera: ActivityResultLauncher<String>
    lateinit var imageCapture: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinn = findViewById<Spinner>(R.id.spin)
        val ADialog = findViewById<Button>(R.id.submit)
        button = findViewById<Button>(R.id.pic)
        imageView = findViewById(R.id.picc)
        val namee = findViewById<EditText>(R.id.entertxt)
        val wingg = findViewById<Spinner>(R.id.spin)
      //  val rell = findViewById<RelativeLayout>
        val ll = findViewById<LinearLayout>(R.id.llayout)



        val lan = arrayOf("Army", "Navy", "Air Force")

        if (spinn != null) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, lan)
            spinn.adapter = adapter
        }

        ADialog.setOnClickListener() {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Are you sure?")
                .setMessage("Do you want to submit the details, you cannot make any further changes")
            builder.setPositiveButton("Submit") { a, b ->
                val name = namee.text.toString()
                Intent(this, RegisterationSuccessful::class.java).also {
                    it.putExtra("Extra_Name", name)
                    startActivity(it)
                }
            }
            builder.setNeutralButton("Don't Submit") { a, b ->

            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()
        }
            requestGallery =
                registerForActivityResult(ActivityResultContracts.RequestPermission())
                {
                    if (it) {
                        Toast.makeText(
                            applicationContext,
                            "Permission Granted",
                            Toast.LENGTH_LONG
                        )
                            .show()
                        pickImagegallery()
                      //  setActivityResult()
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Permission Denied",
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                }
        requestCamera =
            registerForActivityResult(ActivityResultContracts.RequestPermission())
            {
                if (it) {
                    Toast.makeText(
                        applicationContext,
                        "Permission Granted",
                        Toast.LENGTH_LONG
                    )
                        .show()
                     takePhoto()
                    //  setActivityResult()
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Permission Denied",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            }

        button.setOnClickListener() {
           var b2:Button = Button(this)
            var b3:Button = Button(this)

           b2.text = "Upload from Gallery"
            b3.text = "Camera"

           b2.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT )
            b2.setOnClickListener(){
               storagePermission()
            }
            b3.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
            b3.setOnClickListener(){
                cameraPermission()
            }

            ll.addView(b2)
            ll.addView(b3)

        }

    }
    private fun pickImagegallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK ){
            imageView.setImageURI(data?.data)}
        else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                val imageBitmap = data!!.extras!!.get("data") as Bitmap
                imageView.setImageBitmap(imageBitmap)
            }


    }
    private fun takePhoto(){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
    }



       // private fun setActivityResult() {
       //     val intent = Intent()
      //      intent.action = Intent.ACTION_VIEW
      //      intent.type = "image/*"
      //      startActivity(intent)
     //   }

        fun storagePermission() {
            when {
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED -> { Toast.makeText(applicationContext, "Opening Gallery, Permission already granted", Toast.LENGTH_LONG).show()

                    pickImagegallery()
                }
                else -> {
                    requestGallery.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }
        }
    fun cameraPermission() {
        when {
            ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED -> { Toast.makeText(applicationContext, "Opening Camera, Permission already granted", Toast.LENGTH_LONG).show()

                takePhoto()
            }
            else -> {
                requestCamera.launch(android.Manifest.permission.CAMERA)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
         menuInflater.inflate(R.menu.optionsmenu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
         val id = item.itemId
        return when (id) {
            R.id.settings -> {
                Toast.makeText(applicationContext, "Settings Selected", Toast.LENGTH_LONG).show()
                true
            }
            R.id.feedback -> {
                Toast.makeText(applicationContext, "feedBack is selected", Toast.LENGTH_LONG).show()
                true
            }
            R.id.quit -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}