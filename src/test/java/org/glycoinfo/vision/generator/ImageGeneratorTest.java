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
    Assert.assertEquals("iVBORw0KGgoAAAANSUhEUgAAALoAAABSCAIAAABse1lJAAAD0ElEQVR42u2bIW/qUBSAb6bIgpisQCAQiIqJCuQEAoFATCLIgphATCAmKiYQExMIxMSWbAlyYqKiS0gQExOIiYpKxPsBiAqSh3jvMBKybLBBoe24+75cSULvPV/POfe2Vf8AVkaxBIAugC6ALoAugC6ALiwBoAugC6ALoAugC6ALSwDoAugC6ALoAugC6MISALoAugC6ALoAugC6sASALoAugC6ALhAhk8nkt+gyGAxs2y4Wi+oN0zRLpVKr1fJ9P9Zpb4NEAvb6+np4eJiUMfHNud/vFwoql1O2rVxX+b4aDpXnKcdRZ2cqk1HijaxFjLr83WyoRPJKPp+/urrSObvIJJvNZjarul319o8LxmSiOh1lGCqetdhRXVzXFV10LkYyt0pFMocajZa6Mh9//ijLUo1GA10Wcn5+fnFxoXOrK7Evl6fJ41tXZiMIpsZEnWN2VJdarXZ5eamtLo7jSLMiBqzoyjzHGIYRaR+zo7rU6/Xr62s9dZEyZJrTTnYtV2ZD+hjpfNHlA5JaYqjUyegifZlshUK4Mut8M5lMdLvr3W11LcvSUxfJnO12SF1knJyoVquFLu8JgiCdTo/HYw11kS2f54XX5fY2wnq0o7oIR0dHj4+PGuqyt7d2k/t+PD1Nz3zR5QPtdlv2Rxrq8rag4cfLi4qYTXVJiv39fQ0fAqRSqfE4vC69npJyRnb5TKFQ6PV6uumSy+V8P7wu9/eqWCyiy2dkB3B6eqqbLtVq9eYmvC6NhrJte0s1UStdfN/PZrO66fLw8FAshtdFktNgMNhGFtFNF+Hg4GA4HGqly3g8lpvg+TmMK92ukgq9MParv2sy+1k8usT8HoyUabkbtdJFuLu7s6w1ni/OxmikxLN+v7+srKwSlfc/jkeX1a9tc5rNZiLPGiOf2PHxcb2+3vF/qVSS5fjagF+uS6PRSORNhsgnFgSBlBUxZpUcEwSqXC5XKpWF5wpfG7CsNCysEZHqEkPYarVap9PRUJeZMZJjLMv6uo9xnGl7K/fNsjOoeS+yVkjizC7xpJZZU7j5PuCH6jLvY2SS0qPJ7tr31ewET9KJ56l2e9rYmqbpOM6KgV89KjEXoxgSjJShpJ5Lx7oblNtC+vlqtSpZJJVKybKm0+l8Pl+v113X/fZgO0RIlu1Wtv4lQGy6jEYjwzA8z9Nfly0euCV77JHstYkxiU1zF3VJ8DOfXby236vLjw3GT742ihHXhi6ALoAugC4A6ALoAugC6ALoAugCgC6ALoAugC6ALoAuAOgC6ALoAugC6ALoAoAugC6ALvDj+Q9rU3E93BTPUwAAAABJRU5ErkJggg==", strResult);
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
    Assert.assertEquals("iVBORw0KGgoAAAANSUhEUgAAAO4AAABSCAIAAADchH97AAAEEUlEQVR42u2bMUgbURjHv24iGRwDFSrU4aA3CA3UUWiggUpxcMxwlAxSQqcMUjI4ZHBQsOBgKRS7O2RIIQUhQ8cMGRwydHB3UGigAR3aj7Mtlhq93NV7915+P94ocvf/fvfe995d5AeAEwgRACoDoDIAKgOgMqAyACoDoDIAKgMqA6AyACoDoDIAKgMqA6AyACoDoDKgMgAqA6AyACoDKgOgMgAqA6AyACoDKgOgMkwEFxcXk6Jyt9ut1+vFYlFCfN8vlUqNRqPf7+OB7bn1er2FhQVTNqencqfTEXki8lDkjUhL5Ejkq0hPpCnyWuS+1kazwFpLc1ODPc/b2tpyeVbWm6zVaiIPRD6KnI8Y30XeiuQNZpHBxdqi3NrttqrscoOh9ybyQuSZyMnoevwZxyKPq9UqHluX2/r6+sbGhsvbPs1X5Hk4eZxHG6daFeZm63ILgmBzc9NZlVutVtjknUaux685Jp/PT3LfbGNulUplb2/PTZXDJfJRuDs5H3+81d2MmUT+xlBrYV9uOiWb7XDusFS6Dwi33uexxvfZ2dn0T5r+1Td9m23M7fKyC4WCmyrriiOyHbckOoJGo5G+ysa7CxtzUwaDQS6XGw6HDqrseV54/Bm7JO/TXyuzoLKNuV2ytLTUbDYdVFnk3vgbl6vjk+/7pnplkwulnbkpOzs7QRA4qbIkqIeOL5IuN2z+Usem3K4yPT3t4IvrqakpkW8JSvJZl1rjy336M7TVuS0uLh4eHrqm8vz8fPjBQOySfCgWixOostW56Y5zbW3NNZXL5bLIuwQleVWv11N28do2I+XnJCO5xaPf78/Nzbmm8sHBgcjT2CXRyanb7aa/h0vtFcmo/5+F3JIwMzNzfHzslMrD4VAfUJFOrJJ81K7rBsmiz3lZOF8b69qM55YQbW/0aXRKZWV/f1/k8TjfxFyOE61lp9MZtRxHqcrVP86mxzdcm8HcklOr1Yx8V3TnN7a6uirycqxXr6VSSeO42QC3VTaYW3Kq1aqRrz3v/MYGg4EueWFVoswxp8vLyysrK9eeTUa389ZjY7MeR7k2I7n9F4Ig2N3ddVDly6roHFMoFG7r/5q6ZdFnetQZ+5/+MsmhRDan5wzmlmSDZGTfmV6Ztf/Tmwx/aPkuPDf99vuD8Z7Its5Avu+3Wq2IhY9eFXtVNptbPLS1MPV9XKpl1kdW97blcllnkfCdluRyOc/zKpVKu92+9YVnjJJkrbuId23p5xaPs7OzfD5/dHTkvsr/cQ7L8lw7ybmpzcZu08aSZHaiJTeTt2lXSZCY3FxrMIDcLFYZAJUBlQFQGQCVAVAZUBkAlQFQGQCVAVAZUBkAlQFQGQCVAZUBUBkAlQFQGVAZAJUBUBkAlQFQGVAZIOP8BLkvy7K+rLh9AAAAAElFTkSuQmCC", strResult);
  }
}