package com.example.patienttracker.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.patienttracker.domain.model.Patient
import kotlinx.coroutines.flow.Flow

@Dao
interface PatientDao {

    @Upsert
    suspend fun addOrUpdate(patient : Patient)

    @Delete
    suspend fun delete(patient: Patient)

    @Query("SELECT * FROM patientTable where id = :patientId")
    suspend fun getPatientById(patientId : Int) : Patient?

    @Query("SELECT * FROM patientTable")
    fun getAllPatients() : Flow<List<Patient>>
}