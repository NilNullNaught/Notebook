package cn.nnn.notebook

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import cn.nnn.notebook.ui.setting.SettingActivity
import java.util.concurrent.locks.Lock

class NoteApplication:Application() {

    companion object {
        const val Setting = "Setting"

        var isDarkTheme:Boolean = false
        var isDarkThemeSystem:Boolean = false

        var isLocking:Boolean = false //应用锁是否解除

        var lockSetting:Boolean = false //是否开启应用锁

        var isAutoSave:Boolean = true

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext

        isDarkTheme = context.getSharedPreferences(Setting, Context.MODE_PRIVATE)
            .getBoolean("boolean_dark_theme_state", false)
        isDarkThemeSystem = SettingActivity.isDarkTheme(context)
        //如果应用设置为黑夜模式或当前系统为黑夜模式，则开启黑夜模式
        AppCompatDelegate.setDefaultNightMode(
            if(isDarkTheme || isDarkThemeSystem)  AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)

        lockSetting = context.getSharedPreferences(Setting, Context.MODE_PRIVATE)
                .getBoolean("boolean_isLocked_state", false)
        isLocking = lockSetting

        isAutoSave = context.getSharedPreferences(Setting, Context.MODE_PRIVATE)
            .getBoolean("boolean_isAutoSave_state", true)


    }






}