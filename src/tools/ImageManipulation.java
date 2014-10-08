package tools;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class ImageManipulation {

	private ImageManipulation(){}

	public static BufferedImage lighten(BufferedImage image, int intensity) {

		final int WIDTH = image.getWidth();
		final int HEIGHT = image.getHeight();
		BufferedImage resultingImage = new BufferedImage(WIDTH, HEIGHT, image.getType());

		byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		byte[] result = ((DataBufferByte) resultingImage.getRaster().getDataBuffer()).getData();

		int increment = intensity; // lighten by this amount

		// every 4 bytes is the (alpha,r,g,b) of a pixel
		for (int pixel = 0; pixel < pixels.length; pixel += 4) {

			// alpha
			result[pixel] = pixels[pixel];
			// argb += alpha << 24;

			// blue
			int blue = pixels[pixel + 1] & 0xff;
			blue = Math.min(255, blue + increment);
			result[pixel + 1] = (byte)blue;
			// argb += blue;

			// green
			int green = (pixels[pixel + 2] & 0xff);
			green = Math.min(255, green + increment);
			result[pixel + 2] = (byte)green;
			// argb += green << 8;

			// red
			int red =(pixels[pixel + 3] & 0xff);
			red = Math.min(255, red + increment);
			result[pixel + 3] = (byte)red;
			// argb += red << 16;

		}

		return resultingImage;

	}

}
