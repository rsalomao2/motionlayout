package com.dummy.pdfexport

import android.os.Environment
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.*
import java.net.MalformedURLException
import java.net.URL


internal class DownloadFileUseCase {

    suspend fun download(fileUrl: String, fileNameWithExtension: String, folderPath:String = Environment.getExternalStoragePublicDirectory(
        Environment.DIRECTORY_DOWNLOADS).path): String? = withContext(Dispatchers.IO){
        try {
            val url = URL(fileUrl)
            val inputStream: InputStream = url.openStream()
            val dis = DataInputStream(inputStream)
            val buffer = ByteArray(1024)
            var length: Int
            val fos = FileOutputStream(File(folderPath, fileNameWithExtension))
            while (dis.read(buffer).also { length = it } > 0) {
                fos.write(buffer, 0, length)
            }
            "${folderPath}+/+${fileNameWithExtension}"
        } catch (mue: MalformedURLException) {
            //TODO: improve LOG
            Log.e("SYNC getUpdate", "malformed url error", mue)
            null
        } catch (ioe: IOException) {
            Log.e("SYNC getUpdate", "io error", ioe)
            null
        } catch (se: SecurityException) {
            Log.e("SYNC getUpdate", "security error", se)
            null
        }
    }
}
