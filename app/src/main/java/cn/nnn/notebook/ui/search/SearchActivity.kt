package cn.nnn.notebook.ui.search

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import cn.nnn.notebook.BaseActivity
import cn.nnn.notebook.R
import cn.nnn.notebook.logic.model.dataclass.Note
import cn.nnn.notebook.ui.main.MainActivity
import cn.nnn.notebook.ui.main.NoteCardAdapter
import cn.nnn.notebook.ui.notepage.NotepageActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseActivity() {

    private lateinit var viewModel: SearchViewModel
    private var noteListAdapter:NoteListAdapter? = null

    companion object {
        fun actionStart(context: Context) {
            val intent = Intent(context, SearchActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        viewModel()

        toolbar()

        activitySearch_RecyclerView()

        activitySearch_Clear()

        activitySearch_Search()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun viewModel(){
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        viewModel.noteList.observe(this, Observer {
            noteListAdapter?.notifyDataSetChanged()
        })
    }

    private fun toolbar(){
        setSupportActionBar(activitySearch_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun activitySearch_RecyclerView(){
        val layoutManager = LinearLayoutManager(this)
        activitySearch_RecyclerView.layoutManager = layoutManager
        noteListAdapter = NoteListAdapter(this, viewModel.noteList.value!! as List<Note>)
        activitySearch_RecyclerView.adapter = noteListAdapter
    }

    private fun activitySearch_Clear(){
        activitySearch_Clear.setOnClickListener {
            activitySearch_Search.setText("")
        }
    }

    private fun  activitySearch_Search(){
        activitySearch_Search.addTextChangedListener( object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s.toString() != ""){
                    viewModel.queryNote(s.toString())
                }else{
                    viewModel.clear()
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }


}
