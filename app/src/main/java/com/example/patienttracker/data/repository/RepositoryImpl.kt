package com.example.patienttracker.data.repository

import com.example.patienttracker.data.local.PatientDao
import com.example.patienttracker.domain.model.Patient
import com.example.patienttracker.domain.repository.PatientRepository
import kotlinx.coroutines.flow.Flow

class RepositoryImpl(
    private val dao : PatientDao
) : PatientRepository {
    override suspend fun addOrUpdate(patient: Patient) {
        dao.addOrUpdate(patient)
    }

    override suspend fun delete(patient: Patient) {
        dao.delete(patient)
    }

    override suspend fun getPatientById(patientId: Int): Patient? {
        return dao.getPatientById(patientId)
    }

    override fun getAllPatients(): Flow<List<Patient>> {
        return dao.getAllPatients()
    }
}