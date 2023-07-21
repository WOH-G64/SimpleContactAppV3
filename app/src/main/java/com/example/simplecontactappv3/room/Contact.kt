package com.example.simplecontactappv3.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Contact(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo("name") var contactName: String,
    var surname: String? = null,
    @ColumnInfo("phone") var phoneNumber: String,
    var state: Int = 0
): Parcelable