package internalData;

import java.awt.Insets;
import java.io.*;
import java.util.Scanner;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import display.Window;
import internalData.TabItem.Structure;

public class TextDocument extends FileAbstract {

	JTextArea content;
	
	public TextDocument(String label, FolderAbstract parent) {
		super(label, parent);
		content = new JTextArea();
		content.setMargin(new Insets(3,3,3,3));
		content.setLineWrap(true);
		content.setBackground(TabItem.themeBackground);
		content.setForeground(TabItem.themeForeground);
	}
	
	@Override
	public Structure getType() {
		return Structure.TEXT;
	}

	@Override
	public void onAction(FolderAbstract parent) {
		if(parent.getType() == Structure.FOLDER) {
			((Folder)parent).scrollable = true;
		}
		parent.setActive(this);
		content.setSize(parent.getContainedSize());
		content.setTabSize(3);
		parent.overrideContent(content);
		Window.refresh();
	}

	@Override
	public void refresh() {
		content.revalidate();
		content.repaint();
	}

	@Override
	public void save(String filepath) {
		File f = new File(filepath + getExtention());
		try {
			f.createNewFile();
			FileWriter w = new FileWriter(f);
			w.write(content.getText());
			w.close();
		} catch (IOException e) {
			System.err.println("Error creating text file");
		}
		content.setSize(parent.getContainedSize());
	}

	public void setText(File subFile) {
		try {
			StringBuilder sb = new StringBuilder();
			Scanner sc = new Scanner(subFile);
			while(sc.hasNextLine()) {
				sb.append(sc.nextLine());
				sb.append("\n");
			}
			sc.close();
			content.setText(sb.toString());
		} catch (FileNotFoundException e) {
			System.err.println("Error reading file");
		}
	}

	@Override
	public String getExtention() {
		return ".txt";
	}

	@Override
	public JPanel onAction() {
		content.setSize(parent.getContainedSize());
		return Window.encase(content, -1, -1);
	}
}
