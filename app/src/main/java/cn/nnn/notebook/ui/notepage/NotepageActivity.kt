package cn.nnn.notebook.ui.notepage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import cn.nnn.notebook.R
import cn.nnn.notebook._util.showToast
import cn.nnn.notebook.BaseActivity
import cn.nnn.notebook.NoteApplication
import cn.nnn.notebook.logic.model.dataclass.Note
import cn.nnn.notebook.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_notepage.*

class NotepageActivity : BaseActivity() {

    lateinit var viewModel: NotepageViewModel

    companion object{

        fun actionStart(context: Context, note: Note) {
            val intent = Intent(context, NotepageActivity::class.java)
            intent.putExtra("note_data", note)
            context.startActivity(intent)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notepage)

        viewModel(intent.getSerializableExtra("note_data") as Note)

        toolbar()

        activityNotepage_TitleText()

        activityNotepage_ContentText()
    }

    private fun viewModel(note:Note){
        viewModel = ViewModelProvider(this, NotepageViewModelFactory(note))
            .get(NotepageViewModel::class.java)
    }

    private fun toolbar(){
        setSupportActionBar(activityNotepage_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun activityNotepage_TitleText(){
        activityNotepage_TitleText.setText(viewModel.note.value?.title)

        activityNotepage_TitleText.addTextChangedListener( object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.note.value?.title = s.toString()
                viewModel.isSaved.value = false
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    private fun activityNotepage_ContentText(){
        activityNotepage_ContentText.setText(viewModel.note.value?.content)
        activityNotepage_ContentText.addTextChangedListener( object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.note.value?.content = s.toString()
                viewModel.isSaved.value = false
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_notepage, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            R.id.toolbarNotepage_Save ->{
                viewModel.executeSave()
                getString(R.string.notepageActivity_SaveAttention).showToast()
            }
            R.id.toolbarNotepage_Delete ->{
                viewModel.executeDelete()
                MainActivity.actionStart()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPause() {
        super.onPause()
        if (activityNotepage_ContentText.text.toString() == "" && activityNotepage_TitleText.text.toString() == ""){
            return
        }

        if (NoteApplication.isAutoSave){
            viewModel.executeSave()
        }
    }

    override fun onBackPressed() {
        if (viewModel.isSaved.value!! || NoteApplication.isAutoSave){
            finish()
        }else{
            AlertDialog.Builder(this).apply {
                setTitle(getString(R.string.notepageActivity_SaveTitle))
                setMessage(getString(R.string.notepageActivity_SaveMessage))
                setCancelable(false)
                setPositiveButton(getString(R.string.confirm)){ _, _ ->
                    finish()
                }
                setNegativeButton(getString(R.string.cancel)) { _, _ ->
                }
                show()
            }
        }
    }
}
