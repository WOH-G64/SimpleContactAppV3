package com.example.simplecontactappv3.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ContactDao {
    @Query("SELECT * FROM contact")
    fun getMyAllContacts(): List<Contact>

    @Insert(onConflict = OnConflictStrategy.NONE)
    fun addContact(contact: Contact)

    @Query("DELETE FROM contact WHERE id = :id")
    fun deleteContact(id: Int)

    @Query("SELECT * FROM contact WHERE name LIKE :name")
    fun findContactWithName(name: String): List<Contact>

    @Update
    fun updateContact(contact: Contact)

    @Query("Delete FROM contact")
    fun deleteAll()
}