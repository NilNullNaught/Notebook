package cn.nnn.notebook.logic.model.dataclass

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Note(var create_time:String = "",var update_time:String = "",var title:String,var content:String):
    Serializable {

    //使用 @PrimaryKey 注解将 id 字段设为主键，再把 autoGenerate 参数指定成 true，使得主键的值是自动生成的。
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

}