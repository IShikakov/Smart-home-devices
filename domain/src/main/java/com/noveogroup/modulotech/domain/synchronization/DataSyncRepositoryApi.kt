package com.noveogroup.modulotech.domain.synchronization

interface DataSyncRepositoryApi {
    suspend fun isSyncRequired(): Boolean
    suspend fun syncData()
}