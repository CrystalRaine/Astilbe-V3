package internalData;

import java.awt.Color;
import java.io.File;

import javax.swing.JPanel;

import display.Window;

public abstract class TabItem {

	public static Color themeBackground = Color.black;
	public static Color themeForeground = Color.lightGray;
	public static Color themeAccent = new Color(60,10,10);
	
	private Color foreground;
	private Color background;
	
	protected String label;
	protected FolderAbstract parent;
	
	public enum Structure{
		FOLDER, TEXT, IMAGE, DOUBLE_FOLDER, HEADER, TRUTH_TABLE, 
		VERTICAL_DOUBLE_FOLDER, CHESS_BOARD, GO_BOARD, ASSIGNMENT,
		LINK, HIDDEN_FOLDER, VERTICAL_DOUBLE_HIDDEN_FOLDER, DOUBLE_HIDDEN_FOLDER
	}
	
	public TabItem(String label, FolderAbstract parent) {
		this.foreground = themeForeground;
		this.background = themeBackground;
		this.label = label;
		this.parent = parent;
	}
	
	public void delete() {
		File f = new File(parent.getFilepath() + "\\" + getLabel());
		if(f.isDirectory()) {
			deleteDirectory(f.getAbsolutePath());
		} else {
			File deleteme = new File(f.getAbsolutePath() + ((FileAbstract)this).getExtention());
			deleteme.delete();
		}
		parent.removeSubItem(this);
		Window.refresh();
	}
	
	public void deleteDirectory(String filepath) {
		for(File f : new File(filepath).listFiles()) {
			if(f.isDirectory()) {
				deleteDirectory(f.getAbsolutePath());
			} else {
				f.delete();
			}
		}
		new File(filepath).delete();
	}
	
	public Color getForeground() {
		return foreground;
	}
	
	public Color getBackground() {
		return background;
	}
	
	public void setForeground(Color fg) {
		foreground = fg;
	}
	
	public void setBackground(Color bg) {
		background = bg;
	}
	
	public String getLabel() {
		return label;
	}
	
	public abstract void refresh();
	
	public abstract Structure getType();
	
	public abstract void onAction(FolderAbstract parent);
	
	public abstract JPanel onAction();
	
	public abstract void save(String filepath);

}