package com.dummy.domain

import java.io.File
import java.io.FileOutputStream

interface FileOutputStreamProvider {
    fun provide(destinationFile: File): FileOutputStream
}