package com.example.afurtak.photonotes

import android.content.Intent
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class SaveNoteActivity : AppCompatActivity() {

    lateinit var previewNoteImage: ImageView
    lateinit var nameOfNoteInput: EditText
    lateinit var saveNoteButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_note)

        previewNoteImage = findViewById(R.id.iv_preview_of_note)
        nameOfNoteInput = findViewById(R.id.et_name_of_note)
        saveNoteButton = findViewById(R.id.button_save_note)

        if (intent.hasExtra("data"))
            previewNoteImage.setImageBitmap(intent.extras.get("data") as Bitmap)

        saveNoteButton.setOnClickListener({
            saveImage(nameOfNoteInput.text.toString())
        })
    }

    private fun saveImage(nameOfNote: String) {
        val bitmap = intent.extras.get("data") as Bitmap
        try {
            val out = FileOutputStream(File(filesDir, "$nameOfNote.jpg"))
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.close()
        }
        catch (e: IOException) {
            e.printStackTrace()
        }

        val startMainActivityIntent = Intent(this, MainActivity::class.java)
        startActivity(startMainActivityIntent)
    }

}
