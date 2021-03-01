package cn.nnn.notebook.logic.dao

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper
import cn.nnn.notebook.logic.model.dataclass.Note

@Database(version = 5, entities = [Note::class])
abstract class AppDatabase: RoomDatabase() {

    abstract fun noteDao(): NoteDao

    //因为原则上全局应该只存在一份 AppDatabase 的实例，所以这里使用单例模式
    companion object {

        private var instance: AppDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): AppDatabase {
            instance?.let {
                return it
            }
            return Room.databaseBuilder(
                context.applicationContext,//使用 applicationContext，而不能使用普通的 context，否则容易出现内存泄漏的情况
                AppDatabase::class.java,//AppDatabase 的 Class 类型
                "app_database")//数据库名！
                .fallbackToDestructiveMigration()//!!!!
                .build().apply { //最后调用build()方法完成构建，并将创建出来的实例赋值给instance变量，然后返回当前实例即可。
                    instance = this
                }
        }
    }

}