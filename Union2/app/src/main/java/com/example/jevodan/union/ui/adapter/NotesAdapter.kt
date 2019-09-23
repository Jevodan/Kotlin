package com.example.jevodan.union.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.jevodan.union.R
import com.example.jevodan.union.data.model.Color
import com.example.jevodan.union.data.model.Notes

/**
 * отдаем функцию,которрая принимает аргументом Notes и ничего не возвращает(Unit)
 */
class NotesAdapter(val onItemClick: ((Notes) -> Unit)? = null) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    var notes: List<Notes> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_menu_note, parent, false)
        return NotesViewHolder(view)
    }

    override fun getItemCount() = notes.size

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) = holder.bind(notes[position])


    inner class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val titleTextView = itemView.findViewById<TextView>(R.id.textView_title)
        private val descTextView = itemView.findViewById<TextView>(R.id.textView_desc)

        fun bind(notes: Notes) = with(notes) {
            titleTextView.text = this.title
            descTextView.text = this.desc

            val color = when (this.color) {
                Color.WHITE -> R.color.white
                Color.YELLOW -> R.color.yellow
                Color.GREEN -> R.color.green
                Color.BLUE -> R.color.blue
                Color.RED -> R.color.red
                Color.VIOLET -> R.color.violet
            }
            itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, color))
            itemView.setOnClickListener {
                onItemClick?.invoke(notes)
            }
            //    var notesNew = Notes("0",Color.WHITE)
            //    titleTextView.text = this.concatt(notesNew).title
        }

    }


}