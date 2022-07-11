package today.todolist.infra.file.image

import org.apache.commons.io.FilenameUtils
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import today.todolist.infra.file.FileUploadFacade
import today.todolist.infra.file.exception.WrongImageContentTypeException
import today.todolist.infra.file.exception.WrongImageExtensionException
import java.io.File
import java.util.*

@Component
class ImageUploadFacade (
    private val fileUploadFacade: FileUploadFacade,
    private val randomImageProvider: RandomImageProvider
) {
    private val IMAGE_EXTENSION: List<String> = listOf("jpeg", "png", "jpg")
    private val IMAGE_CONTENT_TYPE: List<String?> = listOf("image/png", "image/jpeg")

    fun uploadImage(image: MultipartFile): String {
        val extension = FilenameUtils.getExtension(image.originalFilename)
        val contentType = image.contentType!!
        if (!IMAGE_EXTENSION.contains(extension)) {
            throw WrongImageExtensionException(extension)
        } else if (!IMAGE_CONTENT_TYPE.contains(contentType)) {
            throw WrongImageContentTypeException(contentType)
        }
        return fileUploadFacade.uploadFile(convert(image), createFileName(extension))
    }

    fun uploadRandomImage(): String =
        fileUploadFacade.uploadFile(randomImageProvider.createRandomImage(), createFileName("jpg"))

    fun deleteImage(imageUrl: String) {
        fileUploadFacade.deleteFile(imageUrl)
    }

    private fun convert(file: MultipartFile): File {
        val image = File(System.getProperty("user.dir") + "/image/" + UUID.randomUUID() + file.originalFilename)
        file.transferTo(image)
        return image
    }

    private fun createFileName(extension: String): String = "Image_" + UUID.randomUUID() + "." + extension
}