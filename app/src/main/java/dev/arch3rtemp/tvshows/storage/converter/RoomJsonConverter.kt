package dev.arch3rtemp.tvshows.storage.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import javax.inject.Inject

@ProvidedTypeConverter
class RoomJsonConverter @Inject constructor(private val moshi: Moshi) {

    private val stringListType = Types.newParameterizedType(List::class.java, String::class.java)
    private val stringListAdapter = moshi.adapter<List<String>>(stringListType)

    private val intListType = Types.newParameterizedType(List::class.java, Int::class.javaObjectType)
    private val intListAdapter = moshi.adapter<List<Int>>(intListType)

    @TypeConverter
    fun fromStringList(list: List<String>): String {
        return stringListAdapter.toJson(list)
    }

    @TypeConverter
    fun toStringList(json: String): List<String>? {
        return stringListAdapter.fromJson(json)
    }

    @TypeConverter
    fun fromIntList(list: List<Int>): String {
        return intListAdapter.toJson(list)
    }

    @TypeConverter
    fun toIntList(json: String): List<Int>? {
        return intListAdapter.fromJson(json)
    }
}