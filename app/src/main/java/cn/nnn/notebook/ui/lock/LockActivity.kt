package cn.nnn.notebook.ui.lock

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cn.nnn.notebook.BaseActivity
import cn.nnn.notebook.NoteApplication
import cn.nnn.notebook.R
import cn.nnn.notebook.ui.main.MainActivity
import cn.nnn.notebook.ui.setting.SettingActivity
import kotlinx.android.synthetic.main.activity_lock.*

class LockActivity : BaseActivity() {

    companion object{
        fun actionStart(context: Context) {
            if (NoteApplication.isLocking){
                val intent = Intent(context, LockActivity::class.java)
                context.startActivity(intent)
                NoteApplication.isLocking = !NoteApplication.isLocking
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lock)

        activityLock_Confirm.setOnClickListener {
            MainActivity.actionStart()
        }
    }
}
