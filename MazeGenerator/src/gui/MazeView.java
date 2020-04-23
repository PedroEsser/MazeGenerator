package gui;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import colorSchemes.ColorScheme;
import costumIterators.FillIterator;
import costumIterators.StepInterface;
import guiUtils.ColorSchemeChooser;
import guiUtils.SliderValueChooser;
import guiUtils.StepIterator;
import utils.ImageUtils;
import utils.MazeUtils;

public class MazeView extends JPanel implements KeyListener{

	protected final StepInterface si;
	protected final StepIterator sit;
	protected MazeImageHolder mih;
	protected ColorSchemeChooser csc;
	
	public MazeView(StepInterface si) {
		this.si = si;
		FillIterator fi = new FillIterator(si);
		mih = new MazeImageHolder(si.getImage());
		mih.addKeyListener(this);
		this.sit = new StepIterator(fi, mih);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(mih.getPane());
		add(sit);
	}
	
	public void setColorScheme(ColorScheme cs) {
		if(cs==null)
			remove(csc);
		si.setColorScheme(cs);
		this.csc = new ColorSchemeChooser(cs);
		add(csc);
	}
	
	private void saveImg() {
		JFileChooser chooser = new JFileChooser();
	    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
           File folder =  chooser.getSelectedFile();
           ImageUtils.saveImage(si.getImage(), ImageUtils.getNextImageName(folder.getAbsolutePath(), "Maze"));
        }
	}

	@Override
	public void keyPressed(KeyEvent e) {
		char c = e.getKeyChar();
		if(c == 'n')
			sit.next();
		else if(c == 'p')
			sit.previous();
		else if(c == 's')
			saveImg();
	}	

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
	
}
