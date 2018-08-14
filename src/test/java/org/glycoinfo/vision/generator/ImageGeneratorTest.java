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
    Assert.assertEquals("iVBORw0KGgoAAAANSUhEUgAAAO4AAABSCAIAAADchH97AAAECUlEQVR42u2bMUgbURjHv24iGRwDFSrU4aA3CAbqKDTQQKU4OGY4SgYpoVMGKRkcMjgoKDgohWJ3hwwppCBk6JghQ4YMHdwdFAwY0KH9OFux1GiSq/fuvfx+vFny/t/v3vve3VN+AjiBEAGgMgAqA6AyACoDKgOgMgAqA6AyoDIAKgOgMgAqA6AyoDIAKgOgMgAqAyoDoDIAKgOgMqAyACoDoDIAKgOgMqAyACrDWHB1dTUuKjebzXK5nM1mJcT3/VwuV6lUOp0OHtieW6vVmpubM2VzfCo3Gg2RlyLPRT6K1ETaIj9EWiJVkQ8iT7U2mgXWWpqbGux53ubmpsursk6yVCqJPBP5InLZZ1yI7IikDWaRwM3aotzq9bqq7HKDoXMTeSvyWuSkfz1uxrHIfLFYxGPrcltbW1tfX3f52Kf5irwJF4/LwcapVoW12brcgiDY2NhwVuVarRY2eacD1+P3GpNOp8e5b7Yxt0KhsLe356bK4Rb5IjydXA4/dvQ0YyaRvzHUWtiXmy7JZjucRyyVngPCo/flSONieno6/jdN/+obv8025nb9szOZjJsq644jsjVqSXQElUolfpWNdxc25qZ0u91UKtXr9RxU2fO88PXnyCX5FP9emQSVbcztmsXFxWq16qDKIk+GP7jcHl993zfVK5vcKO3MTdne3g6CwEmVJUI9dHyXeLnn8Bc7NuV2m8nJSQc/XE9MTIicRyjJN91qjW/38a/QVue2sLBwdHTkmsqzs7PhhYGRS/I5m82OocpW56YnztXVVddUzufzIvsRSvK+XC7H3PXe2WbE/JwYzy0KnU5nZmbGNZUPDw9FXo1cEl2cms1mREtGEDG2TyT9/r7Z3KIzNTV1fHzslMq9Xk8fUJHGSCX5ol3XPZJZ9HJt2CctCblFQdsbfRqdUlk5ODgQmR/mTsz1ONFaNhqNfl4OXpVkqnx7IsnMLQqlUsnIvaJHn9jKyorIu6E+veZyOY3jfgPcVtl4blEoFotGbns++sS63a5ueWFVBlljTpeWlpaXl+98NzmIAclX+cFX2knILQpBEOzu7jqo8nVVdI3JZDIP9X9VPbLoM93vHftNf+lArzzIzzOYW8QDkpFzZ3yV1v5PJxn+o+V++N70/M+F8ZbIlq5Avu/XarUBC291gzHUzzOS28hoa2HqflysldZHVs+2+XxeV5Hwm5akUinP8wqFQr1ef/CD51AlMX7tePBOI1G5ReHs7CydTrfbbfdV/o9rWMKX27HNTW02Nk0bS5LYhZbcTE7TrpIgMbm51mAAuVmsMgAqAyoDoDIAKgOgMqAyACoDoDIAKgOgMqAyACoDoDIAKgMqA6AyACoDoDKgMgAqA6AyACoDoDKgMkDC+QUb6t2gR3o/NQAAAABJRU5ErkJggg==", strResult);
  }
  
//  G48558GR
//  WURCS=2.0/4,5,4/[a2122h-1b_1-5][a2112h-1b_1-5][Aad21122h-2a_2-6_5*NCC/3=O][a2112h-1b_1-5_2*NCC/3=O]/1-2-3-4-2/a4-b1_b3-c2_b4-d1_d3-e1

  @Test
  public void testWurcsG48558GR() throws Exception {
    String sequence = "WURCS=2.0/4,5,4/[a2122h-1b_1-5][a2112h-1b_1-5][Aad21122h-2a_2-6_5*NCC/3=O][a2112h-1b_1-5_2*NCC/3=O]/1-2-3-4-2/a4-b1_b3-c2_b4-d1_d3-e1";

    logger.debug("sequence:>" + sequence);

    byte[] result = imageGenerator.getImage(sequence, "png", "cfg", "extended");
    BufferedImage img = ImageIO.read(new ByteArrayInputStream(result));
    String strResult = Encoding.encodeToString(img, "png");
    logger.debug("strResult:>" + strResult);
    logger.debug("<html><img src=\"data:image/png;base64," + strResult + "\"></html>");
    Assert.assertEquals("iVBORw0KGgoAAAANSUhEUgAAASIAAACGCAIAAAD2JhCtAAAHvklEQVR42u3cMWhbRxyA8X+TxQQTDAlBoYIKqkFQDYEIqjFQQQU1xUOGDCaIoCEUUTxoCEUGD4JkSEEGDwl1ig1eAiJ4UEEBgwcPCXjwoEGDBw8ZAhksqCAGa0hPT67q2tLTeyed3tn6Pt7SJpCzfT/fe+fnky9EZDjhU0AEMyKYERHMiGBGBDMighkRzIgIZkQwI4IZEcGMCGZEMCMimBHBjIhgRgQzIpgREcyIYEYEMyKCGRHMiAhmRDAjghkRwYwIZkQwM9Pu7m6hUEilUuIUj8fT6XSxWKzX62P9sEcRs4esY7a9vZ1MSjQqhYJUq1Kvy8GB1GpSqcjCgoTDorzt7e2NjZnzsetfMCO7mLVarXw+H4nIxkbfWdtqycqKhELy/PlzmBHMfBubm1MrlTQag+fuhw+SSEgul4MZwcxHyszsbHux8jh9m822NNNrGszo8jCrVCrqYUzJ8TWD1ZoWCoWMPqfBjC4JM3W7GI+3dzg0JrF6Tkun0zAjmA2oWq0mk5qTWN1khsNhc7v8MKNLwiybzZZK+vP40SMpFoswI5i5FYvFajX9efzqlcH7RpjRJWF25YrvzY/T19u37XdEYEYwMziV370To8GMLgOzqampoyP9eby1Jeq2k9WMYOZWNBqt1/Xn8fq6pFIpmBHM3Jqfn19d1Z/HuZwUCgWYEczcKpfLqZT+PFaL4e7u7jCQXH5jBWZ0SZgdHR1FIpGdHZ1JvLEhyWTSl5xzivr+56iYqQ+QCUQBM1Otra0lEj7eG+5cjYYon9vb2/3w+P2tSkPMwuFwqVQCGwXMTHX//v1s1t9rVul0Op/Pu2ixhNn+/n4mkwEbBc+s2Wyq2z8lzcua1mzK7Ozs3Nxcq9VyZ+br8czosxnYKHhmHWlqTUskEu7PaZVKe9sjl8v1NNbVpbGUmVjNev5bYKPAmHWf09QTVyqVWl1tHwTS+cm1Wr5qNSmV2hse8Xi8Uql4vPcL/Kax5z/EykYBM/vi7D2Wy+X5+Xm1ak1NTam5Pj09HYvFstlstVrtt4hpMPOy02juWCuwUZDMhh2rn9Us8JPewEYXm5klxyR6kQw2unjMrDqH1Dt7sNnQwKcSmPne9rBwdR0GmyWnNV+4sXXb29u7c+dOUNJ4MW80zMw9s1l1WvP5sYl8L/KtyG8iFZGayL7InsimyK8iXwc4tjPrWCwWG89JuzAzwkzvPtYLNgtPaz4zNpFvRNZFjvtcn0WWRUIBzu9O1WpVMeOm8WKvZto3tC7Y7DytuTs2kZ9FfhT51N9Y9zoQuTu2sfXsyZMnS0tLbIFMFrMzf7knNjtPa+6OTeQnZ7E69nYdKmkBrmnq0/vs2TOYTRCzfjeZp7G9efPGztOavzgnSTsPY4eejZ2saWMYW7+y2eyLFy9gduG3QLy/zexuUmF7+PChtac1O7eL3zk7HMf+r2WjY3NJLWXB3rXCbChmvvY/PO5M2nxasxqbs7V4rHV9Njo292EnEgmYXfibxhEys/m0ZjU2kd91makrY25sLjWbzenp6QBfDIDZuJe+ga9o2Xxasxqb82MxbWZ/BHXfeO/evc3NTZixDJ5k92nNX/nf/Dh9/WVubO6VSqVMJgMzmJ3+U3tPax7CmLp2JLiuXbvGy1ZsnJxk82nNzm8J/j0Es7fmxjawZDK5tbUFM2pn82nNamzOi4vazP40N7aBFYvFx48fw4zaWXJac8/1Vo1N5OUQzH4xd5L0wOr1eiQSgRm1C/a05tN3tj3HJvKDNrORjG2YZmZmDg4OYDbpNRoN9f3+6tWrgZzWfHod6/mXOydJi2xrMVsffmxDpm5Z1XcKmE00sKWlpVAotLCwsLy8HNRpzQN/hr62tiZy1897w53r02hPktYrn88H8g4xzOwC9vHjx87/DOq0Zi+vqqixiTzy9ZrVyE+S1iuXywXyGzEwsw5Yp0BOa/b4qkpnbI40L2va4QhPkh6yTCazsrICM4D9bzYHe1qzy592xzboOW3TxNj06jxYBrIHAzMbgZ15FgrktGYvf7M7NmeXv/bvT64Pnfcefzd6krRG6tMe1Hv6MLMX2Olvw+M5rfn83aOFY9P+5KvPfK1Wg9kkAhvDjvY4Vwybx6a+CoF9mBgIcAUbeNb/aKeyVYfJ2j82mBlv+Fe5NW4RzTGzdhLbPDaYmW1nZ8d5f7c+NmDjWc3sZDYJMwpmPYzdkBtZyWocXKEHbEK+o09yfGl7GHspL9/L+6fy1Ls07RXsDDa+BDCbIGOdy4u04YFN2h0UzDD2n7GB0kaygsEMZpNurJ+00a5gPJvBbNKNnZE2QmAEM4z1kHb9+vWbN2+6ADuzNLFAEcx8GOtKu337tsuOiM2vNRHMLoAxL3uPMCOYDWtsoDSYEcxOajQaV+TKoixqGOtcD+RBNBo9/97jhLwISzDzVLlcviW3XstrDWNqDQyFQmo9dN8FARvxbKYpzd0YEcyGlYYxgplZaRgjmJmVhjGCmVlpGCOYmZWGMYKZWWkYI5iZlYYxgplZaYuyiDGCmVlpMzMzGCOYmS3AE2oJZkQEMyKYEcGMiGBGBDMighkRzIhgRkQwI4IZEcyICGZEMCMimBHBjAhmRAQzIpgRwYyIYEYEMyKCGRHMiGBGRDAjghkRzIjIVP8AZ/+dEfjCBGgAAAAASUVORK5CYII=", strResult);
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