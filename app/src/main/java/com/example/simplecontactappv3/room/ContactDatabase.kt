package com.example.simplecontactappv3.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Contact::class], version = 1)
abstract class ContactDatabase: RoomDatabase() {

    abstract fun contactDao(): ContactDao

    companion object{
        private var INSTANSE: ContactDatabase? = null
        fun getDatabase(context: Context): ContactDatabase {
            if (INSTANSE == null) {
                INSTANSE = Room.databaseBuilder(context, ContactDatabase::class.java, "contacts.db")
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANSE!!
        }
    }
}