package com.example.patienttracker.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.patienttracker.utils.Constants.PATIENT_TABLE

@Entity(tableName = PATIENT_TABLE)
data class Patient(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val doctorAssign: String,
    val age: String,
    val gender: Int,
    val prescription: String

)