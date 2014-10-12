package tools;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.awt.image.IndexColorModel;
import java.io.File;

public final class KCImage{

	public final String name;
	public final String filepath;
	public final BufferedImage image;

	public KCImage(BufferedImage img, String nm, String fp) {
		this.image = img;
		this.filepath = fp;
		this.name = nm;
	}

}
