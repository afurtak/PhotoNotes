package com.example.afurtak.photonotes

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class NotesExplorerAdapter(private val context: Context) : RecyclerView.Adapter<NotesExplorerAdapter.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.notes_explorer_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return context.filesDir.listFiles().size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val name = context.filesDir.list()[position]
        holder.bind(name)
    }

    class Holder(itemList: View) : RecyclerView.ViewHolder(itemList) {
        var  mTextView: TextView = itemList.findViewById(R.id.tv_item_label)

        fun bind(fileName: String) {
            mTextView.text = fileName
        }
    }
}