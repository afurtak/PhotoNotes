package com.example.afurtak.photonotes

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import java.io.File
import java.io.FileInputStream
import java.io.IOException

class NotesExplorerAdapter(private val context: Context, private var path: String) : RecyclerView.Adapter<NotesExplorerAdapter.Holder>(){

    companion object {
        val imageExtensions = arrayOf(".jpg", ".bmp", ".gif", "jpeg")
    }

    constructor(context: Context) : this(context, ".")

    fun goToFolder(folder: String) {
        if (File(context.filesDir, "$path/$folder").isDirectory) {
            path += "/$folder"
            notifyDataSetChanged()
        }
    }

    fun goToPreviousFolder() {
        if (path != ".")
            path = path.dropLast(path.length - path.lastIndexOf('/'))

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.notes_explorer_item, parent, false)

        val holder = Holder(view)
        view.setOnClickListener({
            goToFolder(File(context.filesDir, path).listFiles()
                    .filter { it.isDirectory || it.name.endsWith(NotesExplorerAdapter.imageExtensions[0]) }[holder.adapterPosition].name)
        })
        return holder
    }

    override fun getItemCount(): Int {
        return File(context.filesDir, path).listFiles()
                .filter{ it.isDirectory || it.name.endsWith(NotesExplorerAdapter.imageExtensions[0]) }.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val file = File(context.filesDir, path).listFiles()
                .filter { it.isDirectory || it.name.endsWith(NotesExplorerAdapter.imageExtensions[0])}[position]
        holder.bind(file)
    }

    class Holder(itemList: View) : RecyclerView.ViewHolder(itemList) {
        private var mTextView: TextView = itemList.findViewById(R.id.tv_item_label)
        private var mIconImageView: ImageView = itemList.findViewById(R.id.iv_icon)

        fun bind(file: File) {
            mTextView.text = file.name
            if (!file.isDirectory) {
                val options = BitmapFactory.Options()
                options.inPreferredConfig = Bitmap.Config.ARGB_8888
                try {
                    val bitmap = BitmapFactory.decodeStream(FileInputStream(file), null, options)
                    mIconImageView.setImageBitmap(bitmap)
                    mIconImageView.scaleType = ImageView.ScaleType.CENTER
                }
                catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            else {
                mIconImageView.setImageResource(R.drawable.folder)
            }
        }
    }
}