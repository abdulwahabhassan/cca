package com.smartflowtech.cupidcustomerapp.data.repo

import com.smartflowtech.cupidcustomerapp.model.result.RepositoryResult
import com.smartflowtech.cupidcustomerapp.ui.utils.Util
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NotificationsRepository @Inject constructor(
    private val dispatcher: CoroutineDispatcher
) : BaseRepository() {

    suspend fun getNotifications() = withContext(dispatcher) {
        RepositoryResult.Success(
            data = Util.getListOfNotifications()
        )
    }
}