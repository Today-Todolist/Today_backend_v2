package today.todolist.infra.file.image

import org.springframework.stereotype.Component
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.util.*
import javax.imageio.ImageIO

@Component
class RandomImageProvider {
    private val random: Random = Random()

    fun createRandomImage(): File {
        val IMAGE_SIZE = 200
        val BLOCK_SIZE = 40
        val bufferedImage = BufferedImage(IMAGE_SIZE, IMAGE_SIZE, BufferedImage.TYPE_INT_RGB)
        val graphics = bufferedImage.createGraphics()
        graphics.color = Color.WHITE
        graphics.fillRect(0, 0, IMAGE_SIZE, IMAGE_SIZE)
        graphics.color = getRandomColor()

        var x: Int = 0
        var y: Int
        while (x <= IMAGE_SIZE) {
            y = 0
            while (y <= IMAGE_SIZE) {
                if (getRandomBoolean()) graphics.fillRect(x, y, BLOCK_SIZE, BLOCK_SIZE)
                y += BLOCK_SIZE
            }
            x += BLOCK_SIZE
        }
        val image = File(System.getProperty("user.dir") + "/" + UUID.randomUUID() + ".jpg")
        ImageIO.write(bufferedImage, "jpg", image)
        return image
    }

    private fun getRandomBoolean(): Boolean = random.nextBoolean()
    private fun getRandomInt(start: Int, end: Int): Int = random.nextInt(end - start) + start
    private fun getRandomColor(): Color = Color(getRandomInt(100, 180), getRandomInt(100, 180), getRandomInt(100, 180))
}