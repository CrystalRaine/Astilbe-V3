package internalData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JPanel;

import chessGame.ChessPanel;
import display.Window;

public class Chess extends FileAbstract {

	ChessPanel content;
	
	public Chess(String label, FolderAbstract parent) {
		super(label, parent);
		content = new ChessPanel();
		content.setBackground(TabItem.themeBackground);
		content.setForeground(TabItem.themeForeground);
	}

	public void setMoves(File subFile) {
		try {
			Scanner sc = new Scanner(subFile);
			if(sc.hasNextLine()) {
				String s = sc.nextLine();
				content.setMoves(s);
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.err.println("error reading from chess file");
		}
	}
	
	@Override
	public String getExtention() {
		return ".chs";
	}

	@Override
	public void refresh() {
		content.revalidate();
		content.repaint();
	}

	@Override
	public Structure getType() {
		return Structure.CHESS_BOARD;
	}

	@Override
	public void onAction(FolderAbstract parent) {
		if(parent.getType() == Structure.FOLDER) {
			((Folder)parent).scrollable = true;
		}
		parent.setActive(this);
		content.setSize(parent.getContainedSize());
		parent.overrideContent(content);
		Window.refresh();
	}

	@Override
	public JPanel onAction() {
		content.setSize(parent.getContainedSize());
		return Window.encase(content, -1, -1);
	}

	@Override
	public void save(String filepath) {
		File f = new File(filepath + getExtention());
		try {
			f.createNewFile();
			FileWriter w = new FileWriter(f);
			w.write(content.getMoveSet());
			w.close();
		} catch (IOException e) {
			System.err.println("Error creating text file");
		}
		content.setSize(parent.getContainedSize());
	}

}
