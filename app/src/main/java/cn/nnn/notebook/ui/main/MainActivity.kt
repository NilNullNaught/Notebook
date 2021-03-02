package cn.nnn.notebook.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import cn.nnn.notebook.R
import cn.nnn.notebook.BaseActivity
import cn.nnn.notebook.NoteApplication
import cn.nnn.notebook.logic.model.dataclass.Note
import cn.nnn.notebook.ui.notepage.NotepageActivity
import cn.nnn.notebook.ui.search.SearchActivity
import cn.nnn.notebook.ui.setting.SettingActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private lateinit var viewModel: MainViewModel
    private var noteCardAdapter: NoteCardAdapter? = null
    private var sortNo: Int = 0
    private val sortSelectItems = arrayOf(
        "最早创建",
        "最晚创建",
        "最早更新",
        "最晚更新",
        "标题降序",
        "标题升序"
    )

    companion object {
        fun actionStart() {
            val intent = Intent(NoteApplication.context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            NoteApplication.context.startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel()

        toolbar()

        activityMain_RecyclerView()

        activityMain_NavView()

        activityMain_SwipeRefresh()

        activityMain_AddButton()
    }

    private fun viewModel() {
        viewModel = ViewModelProvider(this)
            .get(MainViewModel::class.java)

        viewModel.noteList.observe(this, Observer {
            Log.d("!!!!!","1000")
            noteCardAdapter?.notifyDataSetChanged()
            Log.d("!!!!!","${noteCardAdapter == null}")
            //表示刷新事件结束，并隐藏刷新进度条
            activityMain_SwipeRefresh.isRefreshing = false
        })
    }

    private fun toolbar() {
        //使用 Toolbar 代替 ActionBar
        setSupportActionBar(activityMain_Toolbar)

        //在 Toolbar 上添加滑动菜单按钮（将 home 按钮改为滑动菜单的呼出按钮）
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.category)
        }
    }

    private fun activityMain_RecyclerView(){
        val layoutManager = StaggeredGridLayoutManager(
            2,
            StaggeredGridLayoutManager.VERTICAL
        )
        activityMain_RecyclerView.layoutManager = layoutManager
        noteCardAdapter = NoteCardAdapter(this, viewModel.noteList.value!!)
        activityMain_RecyclerView.adapter = noteCardAdapter
    }

    private fun activityMain_NavView() {
        //滑动菜单选择监听器
        activityMain_NavView.setNavigationItemSelectedListener {
            //设置一个菜单项选中事件的监听器，当用户点击了任意菜单项时，就会回调到传入的Lambda表达式当中
            when (it.itemId) {
                R.id.navMenu_Setting -> {
                    SettingActivity.actionStart(this)
                }
            }
            //执行处理逻辑后，关闭滑动菜单
            //activityMain_DrawerLayout.closeDrawers()//注意，通过滑动菜单打开新的 activity ，关闭菜单动画和打开 activity 动画会相互影响造成卡顿
            //返回true表示此事件已被处理
            true
        }
    }

    private fun activityMain_SwipeRefresh(){
        //设置下拉刷新进度条的颜色
        activityMain_SwipeRefresh.setColorSchemeResources(R.color.colorPrimary)
        //设置下拉刷新的监听器，当用户进行了下拉刷新操作时，就会回调到 Lambda 表达式当中，然后我们在这里去处理具体的刷新逻辑就可以了。
        activityMain_SwipeRefresh.setOnRefreshListener {
            viewModel.queryNoteList()
        }
    }

    private fun activityMain_AddButton(){
        activityMain_AddButton.setOnClickListener {
            val note = Note(title = "", content = "")
            NotepageActivity.actionStart(this, note)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("!!!!!","1000")
        viewModel.queryNoteList()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.toolbarMain_Search -> {
                SearchActivity.actionStart(this)
            }
            R.id.toolbarMain_Sorting -> {
                AlertDialog.Builder(this).apply {
                    setTitle("排序")
                    setSingleChoiceItems(
                        sortSelectItems, sortNo
                    ) { dialog, which ->
                        viewModel.sortList(which)
                        sortNo = which
                        dialog.dismiss()
                    }
                    setCancelable(false)
                    setNegativeButton("取消") { _, _ ->
                    }
                    show()
                }
            }
            android.R.id.home -> activityMain_DrawerLayout.openDrawer(GravityCompat.START)
        }
        return true
    }

}
