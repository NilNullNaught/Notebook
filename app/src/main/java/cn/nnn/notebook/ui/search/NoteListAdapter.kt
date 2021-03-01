package cn.nnn.notebook.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.nnn.notebook.R
import cn.nnn.notebook.logic.model.dataclass.Note
import cn.nnn.notebook.ui.notepage.NotepageActivity


class NoteListAdapter(val context: Context, var noteList: List<Note>):
    RecyclerView.Adapter<NoteListAdapter.ViewHolder>() {


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val noteTitle: TextView = view.findViewById(R.id.itemNoteList_Title)
        val noteContent: TextView = view.findViewById(R.id.itemNoteList_Content)
        val noteTime:TextView = view.findViewById(R.id.itemNoteList_Time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_notelist, parent, false)

        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            val note = noteList[position]
            //打开 NotepageActivity
            NotepageActivity.actionStart(context,note)
        }
        return holder

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = noteList[position]
        holder.noteTitle.text = note.title
        holder.noteContent.text = note.content
        holder.noteTime.text = note.create_time
    }

    override fun getItemCount() = noteList.size

}
