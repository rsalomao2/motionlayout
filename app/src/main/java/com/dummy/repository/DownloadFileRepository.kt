package com.dummy.repository

import com.dummy.domain.Status
import okhttp3.ResponseBody

interface DownloadFileRepository {

    suspend fun download(): Status<ResponseBody>
}
