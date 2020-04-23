package utils;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtils {

	public static BufferedImage getImageFromPath(String path) {
		try {
			return ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void saveImage(BufferedImage img, String path) {
		try {
			ImageIO.write(img, "png", new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static BufferedImage getScaledImage(BufferedImage img, int scale) {
		int w = img.getWidth();
		int h = img.getHeight();
		BufferedImage newImg = new BufferedImage(w * scale, h * scale, BufferedImage.TYPE_3BYTE_BGR);
		for (int x = 0; x < w; x++)
			for (int y = 0; y < h; y++) {
				int rgb = img.getRGB(x, y);
				for (int i = 0; i < scale; i++)
					for (int j = 0; j < scale; j++)
						newImg.setRGB(x * scale + i, y * scale + j, rgb);
			}
		return newImg;
	}

	public static BufferedImage getScaledImage2(BufferedImage img, int scale) {
		int w = img.getWidth() * scale;
		int h = img.getHeight() * scale;
		BufferedImage newImg = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
		for (int x = 0; x < w; x++)
			for (int y = 0; y < h; y++) {
				int rgb = img.getRGB(x/scale, y/scale);
				newImg.setRGB(x, y, rgb);
			}
		return newImg;
	}

	public static BufferedImage getCroppedImage(BufferedImage img, Rectangle r) {
		BufferedImage croppedImage = new BufferedImage(r.width, r.height, BufferedImage.TYPE_3BYTE_BGR);
		int limitX = r.x + r.width;
		int limitY = r.y + r.height;
		for (int i = r.x; i < limitX; i++)
			for (int j = r.y; j < limitY; j++)
				croppedImage.setRGB(i - r.x, j - r.y, img.getRGB(i, j));
		return croppedImage;
	}
	
	private static FileFilter getFileFilter(String name) {
		FileFilter ff = new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				String s = pathname.getName();
				if(s.startsWith(name) && s.endsWith(".png")) 
					try {
						Integer.parseInt(s.substring(name.length(), s.length() - 4));
						return true;
					}catch(NumberFormatException e) {
						return false;
					}
				return false;
			}
		};
		return ff;
	}

	public static String getNextImageName(String directory, String name) {
		int index = 0;
		File[] validImages = new File(directory).listFiles(getFileFilter(name));
		for (File f : validImages) {
			String fileName = f.getName();
			int id = Integer.parseInt(fileName.substring(name.length(), fileName.length() - 4));
			if (index < id)
				index = id;
		}
		return directory + "/" + name + (index + 1) + ".png";
	}

}
