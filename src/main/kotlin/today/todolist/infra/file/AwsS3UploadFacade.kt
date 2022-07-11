package today.todolist.infra.file

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.DeleteObjectRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.File
import java.nio.file.Files
import java.nio.file.Path

@Component
class AwsS3UploadFacade (
    private val amazonS3: AmazonS3,
    @Value("\${cloud.aws.s3.bucket}") private val bucket: String
) : FileUploadFacade {
    override fun uploadFile(file: File, fileName: String): String {
        amazonS3.putObject(bucket, fileName, file)
        cleanUpLocalFile(file.toPath())
        return getFileUrl(fileName)
    }

    override fun deleteFile(fileUrl: String) {
        val fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1)
        amazonS3.deleteObject(DeleteObjectRequest(bucket, fileName))
    }

    private fun getFileUrl(fileName: String): String = amazonS3.getUrl(bucket, fileName).toString()

    private fun cleanUpLocalFile(path: Path) {
        Files.delete(path)
    }
}