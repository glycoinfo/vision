package org.glycoinfo.vision.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;

public class Encoding {
  
  /**
   * Decode string to image
   * @param imageString The string to decode
   * @return decoded image
   */
  public static BufferedImage decodeToImage(String imageString) {

      BufferedImage image = null;
      byte[] imageByte;
      try {
          imageByte = Base64.decodeBase64(imageString);
          ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
          image = ImageIO.read(bis);
          bis.close();
      } catch (Exception e) {
          e.printStackTrace();
      }
      return image;
  }

  
  public static String encodeToString(BufferedImage image, String type) {
    String imageString = null;
    ByteArrayOutputStream bos = new ByteArrayOutputStream();

    try {
      ImageIO.write(image, type, bos);
      byte[] imageBytes = bos.toByteArray();

      imageString = Base64.encodeBase64String(imageBytes);

      bos.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return imageString;
  }
}
