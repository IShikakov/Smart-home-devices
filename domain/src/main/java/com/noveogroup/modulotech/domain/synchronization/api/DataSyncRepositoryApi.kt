package com.noveogroup.modulotech.domain.synchronization.api

interface DataSyncRepositoryApi {
    suspend fun isSyncRequired(): Boolean
    suspend fun syncData()
}