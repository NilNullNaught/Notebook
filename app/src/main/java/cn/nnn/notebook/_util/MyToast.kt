package cn.nnn.notebook._util

import android.content.Context
import android.widget.Toast
import cn.nnn.notebook.NoteApplication

fun Int.showToast( duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(NoteApplication.context, this, duration).show()
}

fun String.showToast( duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(NoteApplication.context, this, duration).show()
}