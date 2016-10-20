package org.glycoinfo.vision.generator;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.glycoinfo.vision.generator.ImageGenerator;
import org.glycoinfo.vision.generator.config.ImageGeneratorConfig;
import org.glycoinfo.vision.util.Encoding;
import org.junit.Assert;
//import org.glycoinfo.convert.wurcs.WurcsToIupacConverterTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ImageGeneratorTest.class, ImageGeneratorConfig.class})
@Configuration
public class ImageGeneratorTest {
  
  private static final Log logger = LogFactory.getLog(ImageGeneratorTest.class);
  
  @Autowired
  ImageGenerator imageGenerator;
  
  @Test
  public void testWurcsImage() throws Exception {
    String sequence = "WURCS=2.0/2,2,1/[a2122h-1b_1-5_2*NCC/3=O][a2112h-1b_1-5]/1-2/a4-b1";

    String image = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAASIAAABSCAIAAAB/iU6UAAAFWElEQVR42u2cMWgjRxhGv1xljIorBTHEEBeCqDCcICoNEUQQE1y4dCGMChNEuEKFCSpcqHDhAx24cIgv2ODShQsdyGBQ4eIKFy5UqEih4rorLIjgBHaRjKQkGEda7a60s2vpPabcO3/Mzpt/ZjSS/gKAgBFdAIBmAGgGAGgGgGYAaAYAaAaAZgCAZgBoBoBmAIBmAGgGgGYAgGYAaAYAaAaAZgBoBgBoBoBmAGgGAGgGgGYAgGYAaAaAZgCAZgBoBmCVh4eHGdRM08BmX9zc3JRKpUwmM/jTyWQym82Wy+Vmsxn6ECHbhNze3q6uroZlWrCa9f9//82aZvV6PZ3WyopKJdVqajbVaqnRULWq16+1tCQzbsx7CuUNRTyb9K30tfSLVJUa0h/SrXQh/Sx9GWK2J3UskUgcHBzMZjWLvmbmBRSLxeVlnZ2NjPHwoMNDxeOy/J6in036SjqV7ke0z9JbKR7i+B5Qq9WMZjO7aIy4ZqbfNzZMNVC7PT7Mx49KpVQoFKyN4yhnk36Uvpc+jXbsv9aSXlnLNpTd3d29vb3ZPAKJvmbm3a+v9wqCyzydTm8025mbI55N+qFfrO7dtTtjWog1LZfL7e/vo1kImlWrVbPhMaPTUyRTN+LxeND7jYhn62/G7lw79k9Ns5BtFPl8/ujoCM1sa2aWPclk7xTBRyqzFzI7+0CXZFHOJn3TP+G4997eBprNAVPKwl21zqlmZk+cTvtMZUba0tJScKfVEc/WP1q899U+B5rNOXYqlUIz25qZVUSl4j/Y9rbK5fJ8ZpPe+NXMtFxw2RzodDqxWKzb7aKZVc0SiUSj4T/Yu3cBrs0inq3/sZhvzX4La924trZ2cXGBZlY1e/HC8wHD43Z52bvrMIfZpC+8H348bu+Dy+ZMpVLJ5XJoNlyz4Jgk2IcP85ttAsdMu1Z4LC4uctnKajVbWFjodv0Hu7qSWT7NZzbpzwk0uwwu21jS6fTV1RWa2dNsZWWl2fQf7PRUmUxmPrP1Ly761uz34LKNpVwu7+zsoJk9zba2to6P/QcrFFQqlWY729DON9mkXyfQ7Kfg+m0szWZzeXkZzaapmfO3ac7PzzMZ/8HMpH5zczOT2R4nHJpN+s63ZlPJNgkvX75stVpo5qSZ+++h/f8fPnmg2+2aie362k+qszOZVf60qoSFbF6/vzd4bOjDg2xS3Zdmp5P021QwS1YzU6DZGM2c51pP65+Tk5NUysPd3EFrt2XGWb1edx7K7uPZyea+3x4/PPQBk0165eXe8KB9GprNMsViMZQ7xPOrmWFzczOf93aVKZvNmlfl/Ccim20qmg2ySduerlmNymaZQqEQyjdinqtmnrZAox7odDpmGWNGs5u60elofX19Y2Nj6GcvXjULJZsbx9z8TsQgW980NzXtziGbZXK53OHhIZqN18xHKXMezWZuTqVSznuharV3tGDmwlFjxV81s5ltWkuAJ9nG7dMunLPZZLCxDOUM5llWs2ktzB7vN8wLMPvj4+Pej20MPh02JaLRUKXSO1RIJpPVatXlWiuy2SbfNI7K1j/lb/z7yfVd/97jGzfZbGKWi2Hd039Ov2zlfriMPc0bOtWdn59vbW2Z2bd/10GxWCyRSOTz+Vqt5mYyfnIEMuqP2s/mQzP3J5OT95sd2u12PB5vNBqzplkw5dHtcAnrV+i8TkBR67cZxpgW2ht/jppFUx76DWZEMwYK/YZm9hY/QL+hGQCgGQCaAaAZAKAZAJoBoBkAoBkAmgEAmgGgGQCaAQCaAaAZAJoBAJoBoBkAoBkAmgGgGQCgGQCaAaAZAKAZAJoBwFP+BvUCvvHguHARAAAAAElFTkSuQmCC";
    // given().body(output2).with().contentType(JSON).then().statusCode(HttpStatus.SC_OK).and().expect().body(equalTo(image)).when().post("/glycans/image/glycan?format=png&notation=cfg&style=extended");
    logger.debug("sequence:>" + sequence);

    byte[] result = imageGenerator.getImage(sequence, "png", "cfg", "extended");
    BufferedImage img = ImageIO.read(new ByteArrayInputStream(result));
    String strResult = Encoding.encodeToString(img, "png");
    
    logger.debug("strResult:>" + strResult);
    logger.debug("<html><img src=\"data:image/png;base64," + strResult + "\"></html>");
    Assert.assertEquals("iVBORw0KGgoAAAANSUhEUgAAALoAAABSCAIAAABse1lJAAADQklEQVR42u2bLZPiQBBARyD5AZGICAQCgcgPQCAQCAQCgUAiEEgcciUCgUAgEAgEAoFEIBAIBD8CEbGCqkPs9ZE6iuL4yAUyIcN7NWo3VYSZR0/3pKN+AHyjmAJAF0AXQBdAF0AXQBemANAF0AXQBdAF0AXQhSkAdAF0AXQBdAF0AXRhCgBdAF0AXQBdAF0AXZgCQBdAF0AXQBcIEdd1P0KXw+Ewn88bjUYul1N/SafT9Xpd/i7/1fe1X0EkC7Zery3LisoYfd95NBrZtspmVaejFgu138tHq8NBrVbq60s5jrJtW67RqMuv50Y0usgvrdvtmhxd9vt9tVpNp9Vspo6feH3M5yqTUZVKRa5Hl6tMp9NMJqMzDOvWRda+WFT5vNrt7rnije9vdbw4H7YxMdWl0+m0Wi2TU12JK+KKePDQFW/I9iTGSIxBl39pNpu9Xs9YXSQXkT3IT1y5iDESckPNY+IbXdrttpm6yBYrue39fOVOHiOZb3ibdEx1GY/HEq3N1EVqY6mDArjiDcdxZrMZupyz3W4l7pqpS6PRkJo5sC5SXdfrdXS5IJVK7XY7A3XJ5XKLRXBdVqs/J3jocoEUAf1+30BdZEK9s7hgQ0qk8JYkvrpIBVAoFMzUJbAr3giZZ3WJkKj2oxB1SSQSRJcwKJVKw+GQ3IXcxRfiSrlcpjIKpTK6uq6x1sV13WQyqeHJ2sedu9zqNIi1Ll7k3mw25p3q2i881f3fXhPvMj26aO6DkbirrdlDky4/r35mdFoJP6tyfrEeXfzf2/NE9fDoPZ9IF68+kUaXE4PBoFarGajLsd+lKMa4rs9+l+Ktfpf7BtzaGq7uEaHqomHZJLRE0viis5su7aObLnOnmy5YfmBk7iITNZlMzNTllMdI9prNZm/06joPe3XPV8L/qmjejDQEmH6/L7qYVkhfrZVObwIkEgnvtyiWSJ4vNfPD7pYAS3LrF//yNwG06eK6rmVZy+XStGO6MI5itf2C3/nePuU9o1ctSYSv+cTx3j5Xl7ddjHe+NzYj7g1d0AVdAF0AXQDQBdAF0AXQBdAF0AUAXQBdAF0AXQBdAF0A0AXQBdAF0AXQBdAFAF0AXQBd4O35DWGSYVx8hOnGAAAAAElFTkSuQmCC", strResult);
  }
  
  @Test
  public void testWurcsSulfateImage() throws Exception {
    String sequence = "WURCS=2.0/1,2,1/[a2122h-1b_1-5]/1-1/a4-b1*OSO*/3=O/3=O";

    logger.debug("sequence:>" + sequence);

    byte[] result = imageGenerator.getImage(sequence, "png", "cfg", "extended");
    BufferedImage img = ImageIO.read(new ByteArrayInputStream(result));
    String strResult = Encoding.encodeToString(img, "png");
    
    logger.debug("strResult:>" + strResult);
    logger.debug("<html><img src=\"data:image/png;base64," + strResult + "\"></html>");
    Assert.assertEquals("iVBORw0KGgoAAAANSUhEUgAAAO4AAABSCAIAAADchH97AAADmUlEQVR42u2cIW8iQRiGP4HsD0CSHEk3OZJDkBy+FSStQCAqKhBIRAUScQmmvgKBqKhAICoQKxCVyIqK/oMTR3IkFSUB0ftuuSO0obAs7Q4z+zxZBZtm8s7DzDezs5UXACcQIgBUBkBlAFQGQGVAZQBUBkBlAFQGVAZAZQBUBkBlAFQGVAZAZQBUBkBlQGUAVAZAZQBUBlQGQGUAVAZAZQBUBlQGQGVIBOPxOBEqz2azwWBQr9cLhYL8x/O8Wq2mn+u3qGB1bvf39+l02pTN8anc7XZFvoh8E/khcifyJDIVeRYZilyKfM9ms3oP4tqbm/7Srq6uXB6VJ5PJ+fm5yKFIP+iG9y5f5OvZ2Znej8HW5dbv93O5nMEpQmLoD5ETkSORn2v7Y3791puPj4+x2brcWq1Wo9FwedkXjCtHQdbTcJdOnSc6xiRcZetyu7i4aLfbzqoc1HmH4caVV2OMTlVJrpttzE1H5Waz6abKWjYF65X+lv3xr/7T1YyRwkteY2S/wsbcer2eziRuqjwYDIJ19zTaVSwWfd834vGbT2Jug425KY+PjzonuKlyvV4P9o+mUa/LWq0Wv8rGqwsbc5uTyWRGo5GDKgf7+Xc7dMnQ87wEqmxjbnN00dnpdBxUOZirn3bokmcjYpkqkW3Pbb5aLZVKrqo83e2KlTWLv9ixKbc3mKoxPlHlVCpl6ehituSwOrdyuXxzc0OtvC81n1mVrc5NPa5UKuxgfMpKfCsXV5YZMf9O7N3BeAkOeR4cHBh5fu74vnIEF2N7RPLe37d0X3l5Vnl4eHBK5dlsls1mP/Cp1baGzW/bh4J7q7YZz21HdE4w8vDcpjMYi54I0yvLN++nx2vaZjC33TF1GGMfT8adnp6uPOGVHJUN5rY719fX1WrVQZV1BaARB73yK8y4oje/d+42vJ0bt43NehymbUZy+xB0SDZycDm+t0g8z9v4NoTOj2vehojm5X7WymHaZja3yGhjbm9v3VR5Uf/piiSfz698R03X3RvfUVvuifC9Yq/KZnOLRqfTUZVd24xbuTZfvDkcPNP6i/aErnl93994yjZCl+xbdRGtbfHnFo3xeJxOp4fDoZk8X+whztHFJeLMLSn/B+MDF/6oTG52q4zE5OZagQHkhsqojMoAqAyAygCoDKgMgMoAqAyAygCoDKgMgMoAqAyAyoDKAKgMgMoAqAyoDIDKAKgMgMoAqAyoDIDKAJ/FHyDlstN6rwSqAAAAAElFTkSuQmCC", strResult);
  }
}