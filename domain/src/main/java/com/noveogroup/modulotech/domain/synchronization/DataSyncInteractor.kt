package com.noveogroup.modulotech.domain.synchronization

class DataSyncInteractor(
    private val dataSyncRepository: DataSyncRepositoryApi,
) {
    suspend fun isSyncRequired(): Boolean = dataSyncRepository.isSyncRequired()
    suspend fun syncData() {
        dataSyncRepository.syncData()
    }
}