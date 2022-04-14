package internalData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JPanel;

import display.Window;
import internalData.TabItem.Structure;
import truthTableGen.TruthTableGenerator;

public class TruthTable extends FileAbstract {

	TruthTableGenerator ttg;
	
	public TruthTable(String label, FolderAbstract parent) {
		super(label, parent);
		ttg = new TruthTableGenerator();
		ttg.setBackground(TabItem.themeBackground);
		ttg.setForeground(TabItem.themeForeground);
	}

	@Override
	public String getExtention() {
		return ".ttg";
	}

	@Override
	public void refresh() {
		ttg.revalidate();
		ttg.repaint();
	}

	@Override
	public Structure getType() {
		return Structure.TRUTH_TABLE;
	}

	@Override
	public void onAction(FolderAbstract parent) {
		if(parent.getType() == Structure.FOLDER) {
			((Folder)parent).scrollable = false;
		}
		parent.setActive(this);
		parent.overrideContent(Window.encase(ttg, -1, -1));
		
		Window.refresh();
	}

	@Override
	public JPanel onAction() {
		return Window.encase(ttg, -1, -1);
	}

	public void load(File s) {
		try {
			Scanner sc = new Scanner(s);
			if(sc.hasNextLine()) {
				ttg.textEntry.setText(sc.nextLine());
				ttg.inputChanged(ttg.textEntry.getText());
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void save(String filepath) {
		File f = new File(filepath + getExtention());
		try {
			f.createNewFile();
			FileWriter w = new FileWriter(f);
			w.write(ttg.textEntry.getText());
			w.close();
		} catch (IOException e) {
			System.err.println("Error creating Truth Table file");
		}
	}

}
