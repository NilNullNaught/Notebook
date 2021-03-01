package cn.nnn.notebook.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cn.nnn.notebook.NoteApplication
import cn.nnn.notebook.logic.dao.AppDatabase
import cn.nnn.notebook.logic.model.dataclass.Note
import kotlin.concurrent.thread

class SearchViewModel: ViewModel(){

    private var _noteList = ArrayList<Note>()
    val noteList = MutableLiveData<ArrayList<Note>>()

    init {
        noteList.value = _noteList
    }

    fun queryNote(s:String){
        thread {
            _noteList.clear()
            _noteList.addAll(AppDatabase.getDatabase(NoteApplication.context).noteDao().queryNoteByCondition(s))
            noteList.postValue(noteList.value)
        }
    }
}