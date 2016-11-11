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
    Assert.assertEquals("iVBORw0KGgoAAAANSUhEUgAAALoAAABSCAIAAABse1lJAAADyUlEQVR42u2bIW/qUBSAryQEMYlAkAyBqJioQE5UIBCISQSiYgIxgZhATCAmJhCIiYkJ5MRERcUSxCRioqISsR+AqCB5iPcOrwlZNthKoS3cfV+uJKH3nq/nnHvbqr8AkVEsAaALoAugC6ALoAugC0sA6ALoAugC6ALoAujCEgC6ALoAugC6ALoAurAEgC6ALoAugC6ALoAuLAGgC6ALoAugCyTIYrH4LbpMJpNer2dZ1umpKpWUYRj1er3f7/u+n+q090EmAXt7ezs7O8vKmPTmPB6PazVVqaheT7mu8n01nSrPU46jrq6W6og3shYp6vJnt6EyySvVavXu7k7n7CKT7Ha75bIajdT/f1wzFgs1HKpiUaWzFkeqi+u6oovOxUjm1mxK5lCz2UZXVuP9XZmm6nQ66LKW6+vrm5sbnVtdiX2jsUweP7oSjiBYGpN0jjlSXdrt9u3trba6OI4jzYoYENGVVY4pFouJ9jFHqott2/f393rqImXIMJad7FauhEP6GOl80eUTklpSqNTZ6CJ9mWyFYrgSdr6lUim53fXxtrqmaeqpi2TOwSCmLjJkd93v99HlI0EQFAqF+XyuoS6y5fO8+LpIFUuuHh2pLsL5+fnz87OGuuTzWze5H4eoZhgGunxiMBjI/khDXf4vaPwh+6OE2VWXrMjn8xo+BMjlcvN5fF18X0k5I7t8pVarvby86KZLpVKRkMfWxXWVZVno8hXZAVxeXuqmS6vVeniIr0uvJ6O3p5qolS6+75fLZd10eXp6sqz4ukhymkwm+8giuukinJycTKdTrXSZz+dyE7y+xnFlNFJSodfGPvq7JuHP0tEl5fdgpEzL3aiVLsLj46NpbvF8MRyzmRLPxuPxprISJSoff5yOLtGvbXe63W4mzxoTn9jFxYVtb3f8X6/XZTm+N+CX69LpdDJ5kyHxiQVBIGVFjImSY4JANRqNZrO59lzhewM2lYa1NSJRXVIIW7vdHg6HGuoSGiM5xjTN7/sYx1m2t3LfbDqDWvUiW4UkzeySTmoJm8Ld9wEHqsuqj5FJSo8mu2vfV+EJnqQTz1ODwbKxNQzDcZyIgY8elZSLUQoJRspQVs+lU90Nym0h/Xyr1ZIsksvlZFkLhUK1WrVt23XdHw+2Y4Rk025l718CpKbLbDYrFoue5+mvyx4P3LI99sj22sSYzKZ5jLpk+JnPMV7b79XlYINxyNdGMeLa0AXQBdAF0AUAXQBdAF0AXQBdAF0A0AXQBdAF0AXQBdAFAF0AXQBdAF0AXQBdANAF0AXQBQ6ef8TncwW7FfIPAAAAAElFTkSuQmCC", strResult);
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
    Assert.assertEquals("iVBORw0KGgoAAAANSUhEUgAAAO4AAABSCAIAAADchH97AAAECUlEQVR42u2bMUgbURjHv24iGRwDFSrU4aA3CAbqKDTQQKU4OGY4SgYpoVMGKRkcMjgoKDgohWJ3hwwppCBk6JghQ4YMHdwdFAwY0KH9OFux1GiSq/fuvfx+vFny/t/v3vve3VN+AjiBEAGgMgAqA6AyACoDKgOgMgAqA6AyoDIAKgOgMgAqA6AyoDIAKgOgMgAqAyoDoDIAKgOgMqAyACoDoDIAKgOgMqAyACrDWHB1dTUuKjebzXK5nM1mJcT3/VwuV6lUOp0OHtieW6vVmpubM2VzfCo3Gg2RlyLPRT6K1ETaIj9EWiJVkQ8iT7U2mgXWWpqbGux53ubmpsursk6yVCqJPBP5InLZZ1yI7IikDWaRwM3aotzq9bqq7HKDoXMTeSvyWuSkfz1uxrHIfLFYxGPrcltbW1tfX3f52Kf5irwJF4/LwcapVoW12brcgiDY2NhwVuVarRY2eacD1+P3GpNOp8e5b7Yxt0KhsLe356bK4Rb5IjydXA4/dvQ0YyaRvzHUWtiXmy7JZjucRyyVngPCo/flSONieno6/jdN/+obv8025nb9szOZjJsq644jsjVqSXQElUolfpWNdxc25qZ0u91UKtXr9RxU2fO88PXnyCX5FP9emQSVbcztmsXFxWq16qDKIk+GP7jcHl993zfVK5vcKO3MTdne3g6CwEmVJUI9dHyXeLnn8Bc7NuV2m8nJSQc/XE9MTIicRyjJN91qjW/38a/QVue2sLBwdHTkmsqzs7PhhYGRS/I5m82OocpW56YnztXVVddUzufzIvsRSvK+XC7H3PXe2WbE/JwYzy0KnU5nZmbGNZUPDw9FXo1cEl2cms1mREtGEDG2TyT9/r7Z3KIzNTV1fHzslMq9Xk8fUJHGSCX5ol3XPZJZ9HJt2CctCblFQdsbfRqdUlk5ODgQmR/mTsz1ONFaNhqNfl4OXpVkqnx7IsnMLQqlUsnIvaJHn9jKyorIu6E+veZyOY3jfgPcVtl4blEoFotGbns++sS63a5ueWFVBlljTpeWlpaXl+98NzmIAclX+cFX2knILQpBEOzu7jqo8nVVdI3JZDIP9X9VPbLoM93vHftNf+lArzzIzzOYW8QDkpFzZ3yV1v5PJxn+o+V++N70/M+F8ZbIlq5Avu/XarUBC291gzHUzzOS28hoa2HqflysldZHVs+2+XxeV5Hwm5akUinP8wqFQr1ef/CD51AlMX7tePBOI1G5ReHs7CydTrfbbfdV/o9rWMKX27HNTW02Nk0bS5LYhZbcTE7TrpIgMbm51mAAuVmsMgAqAyoDoDIAKgOgMqAyACoDoDIAKgOgMqAyACoDoDIAKgMqA6AyACoDoDKgMgAqA6AyACoDoDKgMkDC+QUb6t2gR3o/NQAAAABJRU5ErkJggg==", strResult);
  }
}