package display;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import internalData.AssignmentsPage;
import internalData.Chess;
import internalData.DoubleFolder;
import internalData.Folder;
import internalData.FolderAbstract;
import internalData.ImageDocument;
import internalData.Link;
import internalData.TabItem;
import internalData.TextDocument;
import internalData.TruthTable;
import internalData.VerticallyDividedDoubleFolder;
import internalData.TabItem.Structure;

public class AddPanel extends JPanel{

	private static final long serialVersionUID = 1L;

	public AddPanel(FolderAbstract parent) {
		if(parent.getType() == Structure.FOLDER) {
			((Folder)parent).scrollable = false;
		}
		this.setBackground(TabItem.themeBackground);
		this.setForeground(TabItem.themeForeground);
		
		JButton text = new JButton("Text Document");
		JButton folder = new JButton("Folder");
		JButton doubleFolder = new JButton("Split Folder");
		JButton image = new JButton("PNG Image");
		JButton truthTable = new JButton("Truth Table");
		JButton verticalDoubleFolder = new JButton("Vertical Split Folder");
		JButton chessboard = new JButton("Chess");
		JButton assignment = new JButton("Assignment Tracker");
		JButton link = new JButton("link");
		
		truthTable.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String newName = CommandLine.getCurrentEntry(false);
				if(newName != null) {
					parent.addSubItem(new TruthTable(newName, parent));
					Window.refresh();
				}
			}
		});
		
		text.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String newName = CommandLine.getCurrentEntry(false);
				if(newName != null) {
					parent.addSubItem(new TextDocument(newName, parent));
					Window.refresh();
				}
			}
		});
		
		folder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String newName = CommandLine.getCurrentEntry(false);
				if(newName != null) {
					File f = new File(parent.getFilepath() + "\\" + newName);
					f.mkdir();
					parent.addSubItem(new Folder(newName, parent.getFilepath() + "\\" + newName, parent));
					Window.refresh();		
				}
			}
		});
		
		doubleFolder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = CommandLine.getCurrentEntry(false);
				if(name != null) {
					File f = new File(parent.getFilepath() + "\\" + name);
					f.mkdir();
					f = new File(parent.getFilepath() + "\\" + name + "\\folder1");
					f.mkdir();
					f = new File(parent.getFilepath() + "\\" + name + "\\folder2");
					f.mkdir();
					parent.addSubItem(new DoubleFolder(name, parent.getFilepath() + "\\" + name, parent));
					Window.refresh();
				}
			}
		});
		
		verticalDoubleFolder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = CommandLine.getCurrentEntry(false);
				if(name != null) {
					File f = new File(parent.getFilepath() + "\\" + name);
					f.mkdir();
					f = new File(parent.getFilepath() + "\\" + name + "\\vfolder1");
					f.mkdir();
					f = new File(parent.getFilepath() + "\\" + name + "\\vfolder2");
					f.mkdir();
					parent.addSubItem(new VerticallyDividedDoubleFolder(name, parent.getFilepath() + "\\" + name, parent));
					Window.refresh();
				}
			}
		});
		
		image.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String newName = CommandLine.getCurrentEntry(false);
				if(newName != null) {
					parent.addSubItem(new ImageDocument(newName, parent.getFilepath() + "\\" + newName + ".png", parent));
					Window.refresh();
				}
			}
		});
		
		chessboard.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String newName = CommandLine.getCurrentEntry(false);
				if(newName != null) {
					parent.addSubItem(new Chess(newName, parent));
					Window.refresh();
				}
			}
		});
		
		assignment.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String newName = CommandLine.getCurrentEntry(false);
				if(newName != null) {
					parent.addSubItem(new AssignmentsPage(newName, parent));
					Window.refresh();
				}
			}
		});
		
		link.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String newName = CommandLine.getCurrentEntry(true);
				if(newName != null && Window.getByName(newName.toLowerCase()) != null) {
					parent.addSubItem(new Link(newName, parent));
					File f = new File(parent.getFilepath() + "\\" + newName + ".lnk");
					try {
						f.createNewFile();
					} catch (IOException e1) {
						System.err.println("Error creating link");
					}
					Window.refresh();
				}
			}
		});
		
		text.setBackground(TabItem.themeAccent);
		text.setForeground(TabItem.themeForeground);
		text.setBorder(BorderFactory.createEmptyBorder(0,10,3,10));
		folder.setBackground(TabItem.themeAccent);
		folder.setForeground(TabItem.themeForeground);
		folder.setBorder(BorderFactory.createEmptyBorder(0,10,3,10));
		doubleFolder.setBackground(TabItem.themeAccent);
		doubleFolder.setForeground(TabItem.themeForeground);
		doubleFolder.setBorder(BorderFactory.createEmptyBorder(0,10,3,10));
		image.setBackground(TabItem.themeAccent);
		image.setForeground(TabItem.themeForeground);
		image.setBorder(BorderFactory.createEmptyBorder(0,10,3,10));
		truthTable.setBackground(TabItem.themeAccent);
		truthTable.setForeground(TabItem.themeForeground);
		truthTable.setBorder(BorderFactory.createEmptyBorder(0,10,3,10));
		verticalDoubleFolder.setBackground(TabItem.themeAccent);
		verticalDoubleFolder.setForeground(TabItem.themeForeground);
		verticalDoubleFolder.setBorder(BorderFactory.createEmptyBorder(0,10,3,10));
		chessboard.setBackground(TabItem.themeAccent);
		chessboard.setForeground(TabItem.themeForeground);
		chessboard.setBorder(BorderFactory.createEmptyBorder(0,10,3,10));
		assignment.setBackground(TabItem.themeAccent);
		assignment.setForeground(TabItem.themeForeground);
		assignment.setBorder(BorderFactory.createEmptyBorder(0,10,3,10));
		link.setBackground(TabItem.themeAccent);
		link.setForeground(TabItem.themeForeground);
		link.setBorder(BorderFactory.createEmptyBorder(0,10,3,10));
		
		this.add(text);
		this.add(folder);
		this.add(doubleFolder);
		this.add(image);
		this.add(truthTable);
		this.add(verticalDoubleFolder);
		this.add(chessboard);
		this.add(assignment);
		this.add(link);
	}
	
}
