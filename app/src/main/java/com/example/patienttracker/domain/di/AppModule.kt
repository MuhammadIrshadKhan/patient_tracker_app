package com.example.patienttracker.domain.di

import android.app.Application
import androidx.room.Room
import com.example.patienttracker.data.local.PatientDatabase
import com.example.patienttracker.data.repository.RepositoryImpl
import com.example.patienttracker.domain.repository.PatientRepository
import com.example.patienttracker.utils.Constants.PATIENT_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(
        application: Application // Use the Application type here
    ): PatientDatabase {
        return Room.databaseBuilder(
            application,
            PatientDatabase::class.java,
            PATIENT_DATABASE
        ).build()
    }

    @Provides
    @Singleton
    fun providePatientRepository(
        db: PatientDatabase
    ): PatientRepository {
        return RepositoryImpl(db.patientDao)
    }
}
