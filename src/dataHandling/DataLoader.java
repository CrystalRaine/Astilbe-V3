package dataHandling;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import display.Window;
import internalData.AssignmentsPage;
import internalData.Chess;
import internalData.DoubleFolder;
import internalData.Folder;
import internalData.FolderAbstract;
import internalData.Header;
import internalData.ImageDocument;
import internalData.Link;
import internalData.LinkedPage;
import internalData.TabItem;
import internalData.TabItem.Structure;
import internalData.TextDocument;
import internalData.TruthTable;
import internalData.VerticallyDividedDoubleFolder;

public class DataLoader {

	public static List<LinkedPage> links;
	
	public static void load(String filepath, Window window) {
		links = new ArrayList<LinkedPage>();
		File f = new File(filepath);
		if(f.exists() && f.isDirectory()) {
			Header h = new Header(f.getName(), f.getAbsolutePath(), null);
			getSubItems(f, h);
			h.getStack();
			window.add(h.getPane(), h);
		}
		for(LinkedPage p : links) {
			p.delay();
		}
	}
	
	private static void getSubItems(File filepath, FolderAbstract item) {
		if(filepath.isDirectory()) {
			for(File subFile : filepath.listFiles()) {
				if(subFile.isDirectory()) {
					Structure type = getTypeFromMetadata(subFile.getAbsolutePath());
					switch(type) {
					case FOLDER:
						Folder f = new Folder(subFile.getName(), subFile.getAbsolutePath(), item);
						getSubItems(subFile, f);
						item.addSubItem(f);
						break;
					case HIDDEN_FOLDER:
						Folder hf = new Folder(subFile.getName(), subFile.getAbsolutePath(), item);
						getSubItems(subFile, hf);
						hf.hide();
						item.addSubItem(hf);
						break;
					case DOUBLE_FOLDER:
						DoubleFolder doubF = new DoubleFolder(subFile.getName(), subFile.getAbsolutePath(), item);
						getSubItems(new File(subFile.getAbsolutePath() + "\\folder1"), doubF.get1());
						getSubItems(new File(subFile.getAbsolutePath() + "\\folder2"), doubF.get2());
						item.addSubItem(doubF);
						break;
					case DOUBLE_HIDDEN_FOLDER:
						DoubleFolder hiddendoubF = new DoubleFolder(subFile.getName(), subFile.getAbsolutePath(), item);
						getSubItems(new File(subFile.getAbsolutePath() + "\\folder1"), hiddendoubF.get1());
						getSubItems(new File(subFile.getAbsolutePath() + "\\folder2"), hiddendoubF.get2());
						hiddendoubF.hide();
						item.addSubItem(hiddendoubF);
						break;
					case VERTICAL_DOUBLE_FOLDER:
						VerticallyDividedDoubleFolder vert = new VerticallyDividedDoubleFolder(subFile.getName(), subFile.getAbsolutePath(), item);
						getSubItems(new File(subFile.getAbsolutePath() + "\\vfolder1"), vert.get1());
						getSubItems(new File(subFile.getAbsolutePath() + "\\vfolder2"), vert.get2());
						item.addSubItem(vert);
						break;
					case VERTICAL_DOUBLE_HIDDEN_FOLDER:
						VerticallyDividedDoubleFolder hiddenVert = new VerticallyDividedDoubleFolder(subFile.getName(), subFile.getAbsolutePath(), item);
						getSubItems(new File(subFile.getAbsolutePath() + "\\vfolder1"), hiddenVert.get1());
						getSubItems(new File(subFile.getAbsolutePath() + "\\vfolder2"), hiddenVert.get2());
						hiddenVert.hide();
						item.addSubItem(hiddenVert);
					default:
						System.err.println("Error: invalid metadata");
					}
				} else {
					int extentionIndex = subFile.getName().lastIndexOf('.');
					String extention = subFile.getName().substring(extentionIndex + 1);
					createTabFile(extention, subFile, extentionIndex, item);
				}
			}
		}
	}
	
	private static Structure getTypeFromMetadata(String filepath){
		if(new File(filepath + "\\folder1").exists() && new File(filepath + "\\folder2").exists()) {
			return Structure.DOUBLE_FOLDER;
		} else if(new File(filepath + "\\vfolder1").exists() && new File(filepath + "\\vfolder2").exists()){
			return Structure.VERTICAL_DOUBLE_FOLDER;
		} else if(filepath.length() > 4 && filepath.substring(filepath.length()-4).equals(".inv")){
			if(new File(filepath + "\\folder1").exists() && new File(filepath + "\\folder2").exists()) {
				return Structure.DOUBLE_HIDDEN_FOLDER;
			} else if(new File(filepath + "\\vfolder1").exists() && new File(filepath + "\\vfolder2").exists()){
				return Structure.VERTICAL_DOUBLE_HIDDEN_FOLDER;
			}
			return Structure.HIDDEN_FOLDER;
		} else {
			return Structure.FOLDER;
		}
	}
	
	private static void createTabFile(String extention, File subFile, int extentionIndex, FolderAbstract item) {
		if(extention.equals("txt")) {
			TextDocument td = new TextDocument(subFile.getName().substring(0, extentionIndex), item);
			td.setText(subFile);
			item.addSubItem(td);
		}
		if(extention.equals("png")) {
			ImageDocument id = new ImageDocument(subFile.getName().substring(0, extentionIndex), subFile.getAbsolutePath(), item);
			item.addSubItem(id);
		}
		if(extention.equals("ttg")) {
			TruthTable td = new TruthTable(subFile.getName().substring(0, extentionIndex), item);
			td.load(subFile);
			item.addSubItem(td);
		}
		if(extention.equals("chs")) {
			Chess td = new Chess(subFile.getName().substring(0, extentionIndex), item);
			td.setMoves(subFile);
			item.addSubItem(td);
		}
		if(extention.equals("asn")) {
			AssignmentsPage td = new AssignmentsPage(subFile.getName().substring(0, extentionIndex), item);
			td.load(subFile);
			item.addSubItem(td);
		}
		if(subFile.getName().lastIndexOf('.') == -1) {
			TextDocument td = new TextDocument(subFile.getName(), item);
			td.setText(subFile);
			item.addSubItem(td);
			subFile.delete();
		}
		if(extention.equals("lnk")) {
			links.add(new LinkedPage() {
				String name = subFile.getName().substring(0, extentionIndex);
				FolderAbstract parent = item;
				File loc = subFile;
				@Override
				public void delay() {
					item.addSubItem(new Link(name, parent));
				}
			});
		}
	}
}
