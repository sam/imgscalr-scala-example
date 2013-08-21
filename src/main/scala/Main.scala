import java.io.File
import javax.imageio.ImageIO
import org.imgscalr.Scalr

object Main extends App {

  import System.{currentTimeMillis => _time}
  def profile[R](code: => R, t: Long = _time) = (code, _time - t)

  val (_, time) = profile {
    val image = ImageIO.read(Main.getClass.getResource("CRV.jpg"))

    val thumbnail = Scalr.resize(image,
      Scalr.Method.QUALITY,
      Scalr.Mode.AUTOMATIC,
      120,
      Scalr.OP_ANTIALIAS)

    ImageIO.write(thumbnail, "jpg", new File("crv-thumbnail.jpg"))
  }

  println(s"Finished in ${time}ms")
}