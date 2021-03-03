package cn.nnn.notebook.ui.setting

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import cn.nnn.notebook.ActivityCollector
import cn.nnn.notebook.R
import cn.nnn.notebook.BaseActivity
import cn.nnn.notebook.NoteApplication
import cn.nnn.notebook.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : BaseActivity() {

    companion object {

        fun actionStart(context: Context) {
            val intent = Intent(context, SettingActivity::class.java)
            context.startActivity(intent)
        }

        fun isDarkTheme(context: Context): Boolean {
            val flag = context.resources.configuration.uiMode and
                    Configuration.UI_MODE_NIGHT_MASK
            return flag == Configuration.UI_MODE_NIGHT_YES
        }
    }

    private fun init() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        toolbar()

        activitySetting_NightMode()

        activitySetting_AppLock()

        activitySetting_AutoSave()

        activitySetting_DeleteConfirm()
    }

    private fun toolbar() {
        setSupportActionBar(activitySetting_Toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun activitySetting_NightMode() {
        activitySetting_NightMode.isChecked = NoteApplication.isDarkTheme
        activitySetting_NightMode.setOnClickListener {
            NoteApplication.isDarkTheme =
                !this.getSharedPreferences(NoteApplication.Setting, Context.MODE_PRIVATE)
                    .getBoolean("boolean_dark_theme_state", false)

            this.getSharedPreferences(NoteApplication.Setting, Context.MODE_PRIVATE)
                .edit { putBoolean("boolean_dark_theme_state", NoteApplication.isDarkTheme) }

            ActivityCollector.finishAll()

            AppCompatDelegate.setDefaultNightMode(
                if (NoteApplication.isDarkTheme || NoteApplication.isDarkThemeSystem) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )
            MainActivity.actionStart()
        }
    }

    private fun activitySetting_AppLock() {
        activitySetting_AppLock.isChecked = NoteApplication.lockSetting
        activitySetting_AppLock.setOnClickListener {
            NoteApplication.lockSetting = !NoteApplication.lockSetting
            this.getSharedPreferences(NoteApplication.Setting, Context.MODE_PRIVATE)
                .edit { putBoolean("boolean_isLocked_state", NoteApplication.lockSetting) }
        }
    }

    private fun activitySetting_AutoSave() {
        activitySetting_AutoSave.isChecked = NoteApplication.isAutoSave
        activitySetting_AutoSave.setOnClickListener {
            NoteApplication.isAutoSave = !NoteApplication.isAutoSave
            this.getSharedPreferences(NoteApplication.Setting, Context.MODE_PRIVATE)
                .edit { putBoolean("boolean_isAutoSave_state", NoteApplication.isAutoSave) }
        }
    }

    private fun activitySetting_DeleteConfirm() {
        activitySetting_DeleteConfirm.isChecked = NoteApplication.deleteConfirm
        activitySetting_DeleteConfirm.setOnClickListener {
            NoteApplication.deleteConfirm = !NoteApplication.deleteConfirm
            this.getSharedPreferences(NoteApplication.Setting, Context.MODE_PRIVATE)
                .edit { putBoolean("boolean_deleteConfirm_state", NoteApplication.deleteConfirm) }
        }
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

}
