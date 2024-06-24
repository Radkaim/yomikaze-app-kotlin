package com.example.yomikaze_app_kotlin.Data.DataSource.DB.Helpers

import androidx.room.TypeConverter


class Converters {
    @TypeConverter
    fun fromStringList(value: List<String>?): String? {
        return value?.joinToString(",")
    }

    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        return value?.split(",")?.map { it.trim() }
    }

//    @TypeConverter
//    fun fromTagList(value: List<Tag>?): String? {
//        val gson = Gson()
//        return gson.toJson(value)
//    }
//
//    @TypeConverter
//    fun toTagList(value: String?): List<Tag>? {
//        val gson = Gson()
//        val type = object : TypeToken<List<Tag>>() {}.type
//        return gson.fromJson(value, type)
//    }
//    @TypeConverter
//    fun listToJson(value: List<Tag>?) = Gson().toJson(value)
//    @TypeConverter
//    fun jsonToList(value: String) = Gson().fromJson(value, Array<Tag>::class.java).toList()
//
//    var gson = Gson()
//
//    @TypeConverter
//    fun stringToSomeObjectList(data: String?): List<Tag?>? {
//        if (data == null) {
//            return emptyList<Tag>()
//        }
//        val listType: Type = object : TypeToken<List<Tag?>?>() {}.type
//        return gson.fromJson<List<Tag?>>(data, listType)
//    }
//
//    @TypeConverter
//    fun someObjectListToString(someObjects: List<Tag?>?): String? {
//        return gson.toJson(someObjects)
//    }
//    @TypeConverter
//    fun fromByteBuffer(byteBuffer: ByteBuffer?): ByteArray? {
//        return byteBuffer?.array()
//    }
//
//    @TypeConverter
//    fun toByteBuffer(byteArray: ByteArray?): ByteBuffer? {
//        return byteArray?.let { ByteBuffer.wrap(it) }
//    }
}