import java.awt.AlphaComposite
import java.io.File
import javax.imageio.ImageIO
import org.imgscalr.Scalr

object Main extends App {

  // Create a simple profile helper to time our execution.
  import System.{currentTimeMillis => _time}
  def profile[R](code: => R, t: Long = _time) = (code, _time - t)

  // profile returns the expression result, and execution time.
  // We don't care about doing anything with the result in this case.
  val (_, time) = profile {

    // Load up our test source image and watermarks.
    val image = ImageIO.read(Main.getClass.getResource("LEAF.jpg"))
    val watermark = ImageIO.read(Main.getClass.getResource("watermark.png"))

    // Generate a thumbnail based on the source image.
    // Since we only pass one dimension and use AUTOMATIC scaling,
    // our thumbnail will maintain it's aspect ratio and be
    // constrained to a square 160px bounding box.
    //
    // Using QUALITY without AntiAliasing seems to produce
    // the most consistently good results while being very fast.
    val thumbnail = Scalr.resize(image,
      Scalr.Method.QUALITY,
      Scalr.Mode.AUTOMATIC,
      160)

    // Now we need to watermark our thumbnail, so we'll start
    // by getting a Graphics2D drawing surface for it.
    val graphics = thumbnail.createGraphics

    // Set the transparency level of our thumbnail for images composited over it.
    graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.95f))

    // We want to tile our watermark, so we'll loop over it's size
    // and draw it in each tile position until we reach the edge
    // of the image.

    // First lets get the actual width/height of the thumbnail.
    val (thumbnailWidth, thumbnailHeight) = (thumbnail.getWidth, thumbnail.getHeight)

    // Now the width/height of the watermark.
    val (watermarkWidth, watermarkHeight) = (watermark.getWidth, watermark.getHeight)

    // Then we iterate over each tile position.
    for {
      x <- 0 to thumbnailWidth - watermarkWidth by watermarkWidth
      y <- 0 to thumbnailHeight - watermarkHeight by watermarkHeight
    } graphics.drawImage(watermark, x, y, null)

    // Not sure that this is necessary, but the examples have it.
    graphics.dispose

    // Finally write our new watermarked thumbnail out to a file.
    ImageIO.write(thumbnail, "jpg", new File("leaf-thumbnail-quality-noAA-watermarked.jpg"))
  }

  // Print out the execution time.
  println(s"Finished in ${time}ms")
}