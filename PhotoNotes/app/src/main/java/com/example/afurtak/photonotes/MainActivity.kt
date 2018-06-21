package com.example.afurtak.photonotes

import android.content.Intent
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import org.opencv.android.OpenCVLoader

class MainActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 1
        private const val REQUEST_IMAGE_EDIT = 2
    }

    lateinit var image: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        print(OpenCVLoader.OPENCV_VERSION)
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

        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            openEditActivity(data!!.extras.get("data") as Bitmap)
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
