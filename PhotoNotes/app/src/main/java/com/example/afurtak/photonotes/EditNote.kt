package com.example.afurtak.photonotes

import android.content.Intent
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.core.MatOfPoint2f
import org.opencv.core.Point
import org.opencv.imgproc.Imgproc
import org.opencv.android.OpenCVLoader

class EditNote : AppCompatActivity() {

    private lateinit var editNoteImage: ImageView
    private lateinit var acceptButton: Button

    private lateinit var topLeft: DragDropView
    private lateinit var topRight: DragDropView
    private lateinit var bottomLeft: DragDropView
    private lateinit var bottomRight: DragDropView
    private lateinit var bitmap: Bitmap

    companion object {
        init {
            OpenCVLoader.initDebug()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        acceptButton = findViewById(R.id.button_accept_note)
        acceptButton.setOnClickListener {
            acceptImage()
        }

        editNoteImage = findViewById(R.id.iv_edit_view)
        bitmap = intent.extras.get("data") as Bitmap
        editNoteImage.setImageBitmap(bitmap)

        topLeft = findViewById(R.id.top_left)
        topRight = findViewById(R.id.top_right)
        bottomLeft = findViewById(R.id.bottom_left)
        bottomRight = findViewById(R.id.bottom_right)
    }

    private fun acceptImage() {
        val destImage = perspectiveTransform()
        Utils.matToBitmap(destImage, bitmap)
        editNoteImage.setImageBitmap(bitmap)
        openSaveNoteActivity()
    }

    private fun openSaveNoteActivity() {
        val saveNoteIntent = Intent(this, SaveNoteActivity::class.java)
        saveNoteIntent.putExtra("data", bitmap)
        startActivity(saveNoteIntent)
    }

    private fun perspectiveTransform(): Mat {
        val width = editNoteImage.width
        val height = editNoteImage.height

        val points = arrayOf(
                topLeft.getRescalePoint(bitmap.width, bitmap.height, width, height),
                topRight.getRescalePoint(bitmap.width, bitmap.height, width, height),
                bottomRight.getRescalePoint(bitmap.width, bitmap.height, width, height),
                bottomLeft.getRescalePoint(bitmap.width, bitmap.height, width, height))

        val src = MatOfPoint2f(
                points[0],
                points[1],
                points[2],
                points[3])

        val dst = MatOfPoint2f(
                Point(0.0, 0.0),
                Point(bitmap.width - 1.0, 0.0),
                Point(bitmap.width - 1.0, bitmap.height - 1.0),
                Point(0.0, bitmap.height - 1.0))

        val originalImg = Mat()
        val destImage = Mat()
        val warpMat = Imgproc.getPerspectiveTransform(src, dst)

        Utils.bitmapToMat(bitmap.copy(Bitmap.Config.ARGB_8888, true), originalImg)
        Imgproc.warpPerspective(originalImg, destImage, warpMat, originalImg.size())
        Imgproc.cvtColor(destImage, destImage, Imgproc.COLOR_RGB2GRAY)
        Imgproc.adaptiveThreshold(destImage, destImage, 255.0, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY, 11, 2.0)

        return destImage
    }
}
