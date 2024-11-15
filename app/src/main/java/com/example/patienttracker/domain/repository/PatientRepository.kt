package com.example.patienttracker.domain.repository

import com.example.patienttracker.domain.model.Patient
import kotlinx.coroutines.flow.Flow


interface PatientRepository {

    suspend fun addOrUpdate(patient : Patient)
    suspend fun delete(patient : Patient)
    suspend fun getPatientById(patientId: Int) : Patient?
    fun getAllPatients() : Flow<List<Patient>>
}