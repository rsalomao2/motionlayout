package com.dummy.api

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Streaming

interface DownloadService {
    @Streaming
    @GET("doc/modelo_pedido_demissao/para_quem_vai_cumprir_aviso.pdf")
    suspend fun downloadResultPdf(): ResponseBody
}
