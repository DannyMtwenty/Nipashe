package com.example.nipashe.db

import androidx.room.TypeConverter
import javax.xml.transform.Source

class Converters {
    @TypeConverter
    fun fromSource(source: com.example.nipashe.data.Source) : String{
        return source.name
    }

    @TypeConverter
    fun toSource(name : String) : com.example.nipashe.data.Source{
        return com.example.nipashe.data.Source(name,name)
    }
}