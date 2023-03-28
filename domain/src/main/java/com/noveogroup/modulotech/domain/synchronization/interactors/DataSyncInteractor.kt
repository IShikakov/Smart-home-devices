package com.noveogroup.modulotech.domain.synchronization.interactors

import com.noveogroup.modulotech.domain.synchronization.api.DataSyncRepositoryApi

class DataSyncInteractor(
    private val dataSyncRepository: DataSyncRepositoryApi,
) {
    suspend fun isSyncRequired(): Boolean = dataSyncRepository.isSyncRequired()
    suspend fun syncData() {
        dataSyncRepository.syncData()
    }
}
