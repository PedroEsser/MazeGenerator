package utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import dataStructure.BitArray;
import dataStructure.Grid;

public class MazeUtils {

	private static int WHITE = Color.WHITE.getRGB();
	private static int BLACK = Color.BLACK.getRGB();
	
	public static void printGrid(Grid m) {
		int nBits = m.getWidth() * m.getHeight();
		for (int i = 0; i < nBits; i++) {
			System.out.print(m.isWall(i) ? '1' : '0');
			if ((i + 1) % m.getWidth() == 0)
				System.out.println();
		}
	}

	public static void setRandomWalls(Grid m, int percentage) {
		int nCells = m.getWidth() * m.getHeight();
		for (int i = 0; i < nCells; i++)
			if (Math.random() * 100 < percentage)
				m.setWall(i);
	}

	public static BufferedImage convertToImage(Grid g) {
		int w = g.getWidth();
		int h = g.getHeight();
		BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
		
		for (int x = 0; x < w; x++) 
			for (int y = 0; y < h; y++) 
				if (!g.isWall(x, y))
						img.setRGB(x, y, WHITE);
		
		return img;
	}

	public static Grid convertToGrid(BufferedImage img) {	//Also works for scaled mazes
		int w = img.getWidth();
		int h = img.getHeight();
		int scale = detectScale(img);
		w /= scale;
		h /= scale;
		Grid g = new Grid(w, h);
		for (int i = 0; i < w; i++)
			for (int j = 0; j < h; j++)
				if (img.getRGB(i * scale, j * scale) != BLACK)
					g.setPath(i, j);
		return g;
	}
	
	public static int detectScale(BufferedImage img) {
		int w = img.getWidth();
		int h = img.getHeight();
		int m = w < h ? w : h;
		int scale = 0;
		for(; scale < m ; scale++)
			if (img.getRGB(scale, scale) != BLACK)
				break;
		return scale;
	}

	public static void saveGrid(Grid g, String path) {
		ImageUtils.saveImage(convertToImage(g), path);
	}

}
