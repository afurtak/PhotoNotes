package com.example.afurtak.photonotes

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import android.view.Display
import android.os.Environment.getExternalStorageDirectory
import android.graphics.drawable.BitmapDrawable
import android.R.attr.bitmap






class EditNote : AppCompatActivity() {


    private lateinit var mEditImageView: ImageView
    private lateinit var mAcceptButton: Button

    private lateinit var topLeft: DragDropView
    private lateinit var topRight: DragDropView
    private lateinit var bottomLeft: DragDropView
    private lateinit var bottomRight: DragDropView
    private lateinit var bitmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        mAcceptButton = findViewById(R.id.button_accept_note)
        mAcceptButton.setOnClickListener {
            acceptImage()
        }

        mEditImageView = findViewById(R.id.iv_edit_view)
        bitmap = intent.extras.get("data") as Bitmap
        mEditImageView.setImageBitmap(bitmap)

        topLeft = findViewById(R.id.top_left)
        topRight = findViewById(R.id.top_right)
        bottomLeft = findViewById(R.id.bottom_left)
        bottomRight = findViewById(R.id.bottom_right)

    }

    private fun acceptImage() {
        val width = mEditImageView.width
        val height = mEditImageView.height

        println("$width, $height")

        val topLeftPoint = Point((topLeft.x.toInt() * bitmap.width / width).toLong(), (topLeft.y.toInt() * bitmap.height / height).toLong())
        val topRightPoint = Point((topRight.x.toInt()* bitmap.width / width).toLong(), (topRight.y.toInt() * bitmap.height / height).toLong())
        val bottomLeftPoint = Point((bottomLeft.x.toInt()* bitmap.width / width).toLong(), (bottomLeft.y.toInt() * bitmap.height / height).toLong())
        val bottomRightPoint = Point((bottomRight.x.toInt()* bitmap.width / width).toLong(), (bottomRight.y.toInt() * bitmap.height / height).toLong())

        mEditImageView.setImageBitmap(parseBitmap(topLeftPoint, topRightPoint, bottomLeftPoint, bottomRightPoint, bitmap))
    }
}
