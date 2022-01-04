package com.example.labtest

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private lateinit var btnPhone: Button
    private lateinit var txtPhone: EditText
    private val requestCall = 1

    private lateinit var btnEmail:Button
    private lateinit var txtEmail: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         txtEmail = findViewById<EditText>(R.id.inEmail)
         txtPhone = findViewById<EditText>(R.id.inPhone)

         btnEmail = findViewById<Button>(R.id.btnOne)
         btnPhone = findViewById<Button>(R.id.btnTwo)

        btnEmail.setOnClickListener(){

            goToBrowser()
        }

        btnPhone.setOnClickListener(){
            makePhoneCall()

        }


    }

    private fun makePhoneCall() {
        val number: String = txtPhone.text.toString()
        if (number.trim { it <= ' ' }.isNotEmpty()) {
            if (ContextCompat.checkSelfPermission(
                    this@MainActivity,
                    Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    requestCall
                )
            } else {
                val dial = "tel:$number"
                startActivity(Intent(Intent.ACTION_CALL, Uri.parse(dial)))
            }
        } else {
            Toast.makeText(this@MainActivity, "Enter Phone Number", Toast.LENGTH_SHORT).show()
        }
    }

    private fun goToBrowser(){

        val linkAddress: String = txtEmail.text.toString()


        val openURL = Intent(android.content.Intent.ACTION_VIEW)
        openURL.data = Uri.parse("http://$linkAddress")
        startActivity(openURL)

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == requestCall) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                makePhoneCall()
            }
            else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show()

            }
        }
    }
}