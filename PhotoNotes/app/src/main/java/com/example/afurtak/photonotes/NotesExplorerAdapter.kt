package com.example.afurtak.photonotes

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.io.File

class NotesExplorerAdapter(private val context: Context, private var path: String) : RecyclerView.Adapter<NotesExplorerAdapter.Holder>(){

    constructor(context: Context) : this(context, ".")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.notes_explorer_item, parent, false)
        val holder = Holder(view)
        view.setOnClickListener({
            path += "/" + File(context.filesDir, path).listFiles()
                    .filter { it.isDirectory }[holder.adapterPosition].name
            notifyDataSetChanged()
        })
        return holder
    }

    override fun getItemCount(): Int {
        return File(context.filesDir, path).listFiles()
                .filter{ it.isDirectory }.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val name = File(context.filesDir, path).listFiles()
                .filter { it.isDirectory }[position].name
        holder.bind(name)
    }

    class Holder(itemList: View) : RecyclerView.ViewHolder(itemList) {
        private var  mTextView: TextView = itemList.findViewById(R.id.tv_item_label)

        fun bind(fileName: String) {
            mTextView.text = fileName
        }
    }
}