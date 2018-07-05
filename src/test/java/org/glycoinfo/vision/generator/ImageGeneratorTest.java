package org.glycoinfo.vision.generator;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eurocarbdb.application.glycanbuilder.BuilderWorkspace;
import org.eurocarbdb.application.glycanbuilder.renderutil.GlycanRenderer;
import org.eurocarbdb.application.glycanbuilder.renderutil.GlycanRendererAWT;
import org.eurocarbdb.resourcesdb.Config;
import org.eurocarbdb.resourcesdb.io.MonosaccharideConversion;
import org.eurocarbdb.resourcesdb.io.MonosaccharideConverter;
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
    Assert.assertEquals("iVBORw0KGgoAAAANSUhEUgAAALoAAABSCAIAAABse1lJAAADRElEQVR42u2bIbcpURSAT5hwgx8wURAEQZjgBwiCIAiCIIiCIGrijYIg3CAIgiAIoiAIguBHCBNusNYT7tvPvGVZ3mAe5ow5vm+ddO+sZZzz2WfvM3vUD0BgFFMA6ALoAugC6ALoAujCFAC6ALoAugC6ALoAujAFgC6ALoAugC6ALoAuTAGgC6ALoAugC6ALoAtTAOgC6ALoAugCIeK67lvost/vZ7NZo9FwHOfjQ3mk0+l6vS5/l//q+9rPIJIFW61Wtm1HZYy+7zwcDlMplc2qTkfN52q3k49W+71aLtXnp8rlVCqVkms06vLrsRGNLvJL63a7JkeX3W5XrVbTaTWdqsMn+o/ZTGUyqlKpyPXo4stkMslkMjrDsG5dZO2LRZXPq+32mive+P5Wh4vzYRsTU106nU6r1TI51ZW4Iq6IBzdd8YZsT2KMxBh0+Zdms9nr9YzVRXIR2YOCxJWzGCMhN9Q8Jr7Rpd1um6mLbLGS217PV67kMZL5hrdJx1SX0Wgk0dpMXaQ2ljroDle8kcvlptMpupyy2Wwk7pqpS6PRkJr5bl2kuq7X6+hyRjKZ3G63BuriOM58fr8uy+WfEzx0OUOKgH6/b6AulvX3LO6+ISVSeEsSX12kAigUCgbqcpjQh0bIPKpLhES1H4UaXSyiSxiUSqXBYEDuQu4SCHGlXC5TGYVSGfmua6x1cV03kUhoeLL2duculzoNYq2LF7nX67V5p7qpJ57q/m+viXeZHl0098FI3NXW7KFJl59nPzM6rkSQVTm9WI8uwe/tcaJ6ePSaT6SLvk+k0eXI19dXrVYzUJdDv0tRjHHdgP0uxUv9LtcNuLQ1+O4RoeqiYdkktETS+KKzmy4doJsuc6Wb7r78wMjcRSZqPB6bqcsxj5HsNZvNXujVzd3s1T1dieCronkz0hBg+v2+6GJaIe1bKx3fBLAsy/stiiWS50vNfLO75Y4lufSLf/qbANp0cV3Xtu3FYmHaMV0YR7HafsGvfG/v8p7Rs5Ykwtd84nhv76vLyy7GK98bmxH3hi7ogi6ALoAuAOgC6ALoAugC6ALoAoAugC6ALoAugC6ALgDoAugC6ALoAugC6AKALoAugC7w8vwGTiRhdKB7mtMAAAAASUVORK5CYII=", strResult);
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
    Assert.assertEquals("iVBORw0KGgoAAAANSUhEUgAAAO4AAABSCAIAAADchH97AAADlklEQVR42u2cIU8jQRiGP4HsD6hscpuwyTU5BMnVg2hCRUUFAoGorEAgKy6pwSMQFYiKioqKihUrkJUIBP/gxJFcEwQkreC+294RIIVuu2VnZ/Z5sqpsyPLO05lvZmeQJwAnECIAVAZAZQBUBkBlQGUAVAZAZQBUBlQGQGUAVAZAZQBUBlQGQGUAVAZAZUBlAFQGQGUAVAZUBkBlAFQGQGUAVAZUBkBlyAWTySQXKs9mszAMW63W7u6u/Mf3/WazqZ/rT1HB6tyur6+LxaIpm9NTud/vi3wR+SbyQ+RK5F5kKvIgMhY5E/nueZ7eg7j25qbftPPzc5d75cfHx6OjI5FtkVHUDO9dgcjXw8NDvR+DrcttNBqVy2WDQ4Sk0B4iByJ7Ij8/bI/59Vtv3t/fx2brcut0Oqenpy5P+6J+ZS/Kehrv0qHzQPuYnKtsXW4nJycXFxfOqhzVedvx+pVXfYwOVXmum23MTXvldrvtpspaNkXzldGK7fGv/tPZjJHCS15jZL3CxtwGg4GOJG6qHIZhNO+erndVKpUgCIx4/OaTlJ/BxtyU29tbHRPcVLnVakXrR9N1r7Nms5m+ysarCxtzm1Mqle7u7hxUOVrPv0rQJGPf93Ooso25zdFJZ7fbdVDlaKy+T9AkD0bEMlUi257bfLZarVZdVXma7EqVDyZ/qWNTbm8wVWN8ospbW1uW9i5mSw6rc6vX671ej1o5KzWfWZWtzk09bjQarGBseCa+RtW7sMxI+Xti7wrGU7TJs1AoGHl/7uy68tySNURM7RXJe7/f0nXll6PKzc2NUyrPZjPP8zb41moNw7JQba/6TctCbknQMcHIy3Ob9mA8t0T8Vsmmyi//kGzmlgRTmzGyuDOuVqst3OGVH5WN55aEy8vL4+NjB1XWGYBGHLXKrzj9it783r7bOAZkX+WlS9pZyC0J2iUb2bic3ikS3/eXnobQ8fGD0xAu1cpxHs9gbknQhxkOh26q/Fz/6YxkZ2dn4Rk1nXcvPaP2siWsLjBWejwjua1Nt9tVlV1bjFs4N38+ORy90/qLtoTOeYMgWLrLdqUmMb7tOH6lkanckjCZTIrF4ng8NpPnkz2k2bu4RJq55eX/YGxw4o/K5Ga3ykhMbq4VGEBuqIzKqAyAygCoDIDKgMoAqAyAygCoDIDKgMoAqAyAygCoDKgMgMoAqAyAyoDKAKgMgMoAqAyAyoDKAKgM8Fn8AYORxMEo6qHvAAAAAElFTkSuQmCC", strResult);
  }
  
  
public static void main(String[] args) throws Exception {
	ImageGenerator ig = new ImageGenerator();
	GlycanRenderer glycanRenderer = new GlycanRendererAWT();
	BuilderWorkspace glycanWorkspace = new BuilderWorkspace(glycanRenderer );
	ig.setGlycanWorkspace(glycanWorkspace);
	MonosaccharideConversion monosaccharideConverter =     new MonosaccharideConverter(new Config());

	ig.setMonosaccharideConverter(monosaccharideConverter);
    String sequence = "WURCS=2.0/1,2,1/[a2122h-1b_1-5]/1-1/a4-b1*OSO*/3=O/3=O";

    logger.debug("sequence:>" + sequence);

    byte[] result = ig.getImage(sequence, "png", "snfg", "extended");
    BufferedImage img = ImageIO.read(new ByteArrayInputStream(result));
    String strResult = Encoding.encodeToString(img, "png");
    logger.debug("strResult:>" + strResult);
    logger.debug("<html><img src=\"data:image/png;base64," + strResult + "\"></html>");

}
}