package com.example.afurtak.photonotes

import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast

class EditNote : AppCompatActivity() {


    private lateinit var mEditImageView : ImageView
    private lateinit var mAcceptButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        mAcceptButton = findViewById(R.id.button_accept_note)
        mAcceptButton.setOnClickListener {
            acceptImage()
        }

        mEditImageView = findViewById<ImageView>(R.id.iv_edit_view)
        mEditImageView.setImageBitmap(intent.extras.get("data") as Bitmap)

    }

    private fun acceptImage() {
        Toast.makeText(this, "Accepted image", Toast.LENGTH_LONG).show()
    }
}
