package tools;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class ImageManipulation {

	private ImageManipulation(){}
	
	public static BufferedImage lighten(BufferedImage image) {

		final int WIDTH = image.getWidth();
		final int HEIGHT = image.getHeight();
		BufferedImage resultingImage = new BufferedImage(WIDTH, HEIGHT, image.getType());

		byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		byte[] result = ((DataBufferByte) resultingImage.getRaster().getDataBuffer()).getData();

		int increment = 55; // lighten by this amount

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

		
		/**
		old, slower way
		ColorModel cm = image.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = image.copyData(null);
		BufferedImage bi = new BufferedImage(cm, raster, isAlphaPremultiplied, null);
		for (int i = 0; i < bi.getWidth(); i++){
			for (int j = 0; j < bi.getHeight(); j++){
				
				int pixel = bi.getRGB(i, j);
				int alpha = (pixel>>24) & 0xff;
				Color color = new Color(pixel);
				Color lightened = lighten(color);
				bi.setRGB(i, j, lightened.getRGB());
				
			}
		}
		graphics.drawImage(bi, x, y, null);
		**/
		
		
	}

}
