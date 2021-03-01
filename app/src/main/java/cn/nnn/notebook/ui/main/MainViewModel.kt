package cn.nnn.notebook.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cn.nnn.notebook.NoteApplication
import cn.nnn.notebook.logic.dao.AppDatabase
import cn.nnn.notebook.logic.model.dataclass.Note
import kotlin.concurrent.thread

class MainViewModel: ViewModel(){

    private var _noteList = ArrayList<Note>()
    val noteList : MutableLiveData<ArrayList<Note>> = MutableLiveData()

    init {
        noteList.value = _noteList
    }

    fun queryNoteList(){
        thread {
            _noteList.clear()
            _noteList.addAll(AppDatabase.getDatabase(NoteApplication.context).noteDao().loadAllNotes())
            noteList.postValue(noteList.value)
        }
   }


    fun sortList(int: Int){

        when(int){
            0 -> {
                noteList.value?.sortBy { it.create_time }
            }
            1 -> {
                noteList.value?.sortByDescending { it.create_time }
            }
            2 -> { noteList.value?.sortBy { it.update_time }
            }
            3 -> {
                noteList.value?.sortByDescending { it.update_time }
            }
            4 ->{
                noteList.value?.sortByDescending { it.title }
            }
            5 ->{
                noteList.value?.sortBy { it.title }
            }
        }
        noteList.value = noteList.value
    }
}