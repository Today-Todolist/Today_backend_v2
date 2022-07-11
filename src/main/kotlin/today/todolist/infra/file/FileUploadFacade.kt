package today.todolist.infra.file

import java.io.File

interface FileUploadFacade {
    fun uploadFile(file: File, fileName: String): String
    fun deleteFile(fileUrl: String)
}