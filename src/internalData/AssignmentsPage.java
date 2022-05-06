package internalData;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import display.Window;

public class AssignmentsPage extends FileAbstract {

	JPanel content;
	List<Assignment> assns;
	
	public AssignmentsPage(String label, FolderAbstract parent) {
		super(label, parent);
		content = new JPanel();
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
		content.setBackground(themeBackground);
		content.setForeground(themeForeground);
		assns = new ArrayList<Assignment>();
	}
	
	public void load(File subFile) {
		try {
			Scanner sc = new Scanner(subFile);
			while(sc.hasNextLine()) {
				assns.add(new Assignment(sc.nextLine()));
			}
			sc.close();
			refresh();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String getExtention() {
		return ".asn";
	}

	@Override
	public void refresh() {
		content.removeAll();
		
		JPanel center = new JPanel();
		center.setBackground(themeBackground);
		center.setForeground(themeForeground);
		center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
		for(Assignment a : assns) {
			JPanel p = new JPanel();
			p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
			p.setBorder(BorderFactory.createMatteBorder(0,0,3,0,themeAccent));
			JLabel l = new JLabel(a.text);
			l.setOpaque(true);
			l.setBackground(themeBackground);
			l.setForeground(themeForeground);
			p.add(Window.encase(l,-1,30), BorderLayout.CENTER);
			l.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
			JPanel sideData = new JPanel();
			sideData.setLayout(new BoxLayout(sideData, BoxLayout.X_AXIS));
			sideData.setBackground(themeBackground);
			sideData.setForeground(themeForeground);
			sideData.setBorder(BorderFactory.createMatteBorder(0,1,0,0,themeAccent));
			JLabel l2 = new JLabel(a.date);
			l2.setBackground(themeBackground);
			l2.setForeground(themeForeground);
			l2.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
			l2.setOpaque(true);
			sideData.add(l2);
			JButton rmv = new JButton();
			rmv.setBackground(themeAccent);
			rmv.setForeground(themeBackground);
			rmv.setText("Remove");
			rmv.addActionListener(new ActionListener() {
				Assignment asgn = a;
				@Override
				public void actionPerformed(ActionEvent e) {
					assns.remove(asgn);
					refresh();
				}
				
			});
			sideData.add(rmv);
			p.add(sideData);
			center.add(Window.encase(p, -1, 25));
		}
		
		center.setBorder(BorderFactory.createLineBorder(themeAccent, 2));
		JScrollPane jsp = new JScrollPane(center);
		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsp.setBackground(themeBackground);
		jsp.setForeground(themeForeground);
		jsp.setBorder(null);
		content.add(jsp, BorderLayout.CENTER);	// location that displays assignments
		
		JPanel add = new JPanel();
		add.setBackground(themeBackground);
		add.setForeground(themeForeground);
		add.setLayout(new BoxLayout(add, BoxLayout.X_AXIS));
		add.setBorder(BorderFactory.createMatteBorder(2,2,2,0,themeAccent));
		
		JTextField text = new JTextField();
		text.setBackground(themeBackground);
		text.setForeground(themeForeground);
		text.setBorder(BorderFactory.createEmptyBorder(0,5,0,5));
		
		add.add(text);
		add.add(Window.encase(text,-1,30), BorderLayout.CENTER);
		
		JPanel sideData = new JPanel();
		sideData.setLayout(new BoxLayout(sideData, BoxLayout.X_AXIS));
		sideData.setBackground(themeBackground);
		sideData.setForeground(themeForeground);
		sideData.setBorder(BorderFactory.createMatteBorder(0,2,0,0,themeAccent));
		sideData.setMaximumSize(new Dimension(100, 100));
		
		JTextField date = new JTextField();
		int m = java.time.LocalDate.now().getDayOfMonth();
		int d = java.time.LocalDate.now().getMonthValue();
		date.setText((m < 10 ? "0" + m : m) + "/" + (d < 10 ? "0" + d : d) + "/" + java.time.LocalDate.now().getYear());
		
		date.setBackground(themeBackground);
		date.setForeground(themeForeground);
		date.setBorder(BorderFactory.createEmptyBorder(0,5,0,5));
		sideData.add(Window.encase(date,-1,300));
		
		JButton btn = new JButton();
		btn.setBackground(themeAccent);
		btn.setForeground(themeBackground);
		btn.setText(" Add ");
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Assignment a = new Assignment(text.getText() + "" + date.getText());
				if(a.isValid()) {
					assns.add(a);
					refresh();
				}
			}
			
		});
		sideData.add(btn, BorderLayout.LINE_END);
		add.add(sideData);
		content.add(add, BorderLayout.PAGE_END);
		content.revalidate();
		content.repaint();
	}

	@Override
	public Structure getType() {
		return Structure.ASSIGNMENT;
	}

	@Override
	public void onAction(FolderAbstract parent) {
		parent.overrideContent(content);
	}

	@Override
	public JPanel onAction() {
		return content;
	}

	@Override
	public void save(String filepath) {
		File f = new File(filepath + getExtention());
		try {
			f.createNewFile();
			FileWriter w = new FileWriter(f);
			for(Assignment a : assns) {
				w.write(a.getSaveText() + "\n");
			}
			w.close();
		} catch (IOException e) {
			System.err.println("Error creating text file");
		}
		content.setSize(parent.getContainedSize());
	}
	
}
