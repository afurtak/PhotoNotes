package com.example.afurtak.photonotes

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


    private lateinit var mEditImageView: ImageView
    private lateinit var mAcceptButton: Button

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

        val points = arrayOf(
                Point((topLeft.x.toInt() * bitmap.width / width).toDouble(), (topLeft.y.toInt() * bitmap.height / height).toDouble()),
                Point((topRight.x.toInt()* bitmap.width / width).toDouble(), (topRight.y.toInt() * bitmap.height / height).toDouble()),
                Point((bottomRight.x.toInt()* bitmap.width / width).toDouble(), (bottomRight.y.toInt() * bitmap.height / height).toDouble()),
                Point((bottomLeft.x.toInt()* bitmap.width / width).toDouble(), (bottomLeft.y.toInt() * bitmap.height / height).toDouble())
        )

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
        Utils.matToBitmap(destImage, bitmap)

        mEditImageView.setImageBitmap(bitmap)
    }
}
