package com.example.afurtak.photonotes

import android.content.Intent
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.widget.Button

class MainActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 1
        private const val REQUEST_IMAGE_EDIT = 2
    }

    lateinit var mNotesExplorer: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var mPreviousDirectoryButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val notesExplorerAdapter = NotesExplorerAdapter(this)

        mNotesExplorer = findViewById(R.id.rv_notes_explorer)
        layoutManager = GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false)
        mNotesExplorer.adapter = notesExplorerAdapter
        mNotesExplorer.layoutManager = layoutManager

        mPreviousDirectoryButton = findViewById(R.id.button_previous_directory)
        mPreviousDirectoryButton.setOnClickListener({ notesExplorerAdapter.goToPreviousFolder() })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item!!.itemId == R.id.add_new_note)
            openCamera()

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (data != null)
            if (requestCode == REQUEST_IMAGE_CAPTURE && data.hasExtra("data")) {
                openEditActivity(data.extras.get("data") as Bitmap)
            }

    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (cameraIntent.resolveActivity(packageManager) != null)
            startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE)
    }

    private fun openEditActivity(bitmap: Bitmap) {
        val intent = Intent(this, EditNote::class.java)
        intent.putExtra("data", bitmap)
        startActivityForResult(intent, REQUEST_IMAGE_EDIT)
    }
}
