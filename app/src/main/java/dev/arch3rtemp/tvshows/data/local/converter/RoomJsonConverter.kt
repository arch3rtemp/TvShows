package dev.arch3rtemp.tvshows.data.local.converter

import androidx.room.ProvidedTypeConverter
import com.squareup.moshi.Moshi
import javax.inject.Inject

@ProvidedTypeConverter
class RoomJsonConverter @Inject constructor(private val moshi: Moshi) {
}