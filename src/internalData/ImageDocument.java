package internalData;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

import display.Window;

public class ImageDocument extends FileAbstract {
	
	JFileChooser fc;
	String filepath;
	JLabel content;
	
	public ImageDocument(String label, String filepath, FolderAbstract parent) {
		super(label, parent);
		this.filepath = filepath;
	}

	@Override
	public String getExtention() {
		return ".png";
	}
	
	@Override
	public void refresh() {
		if(new File(filepath).exists() && content == null) {
			ImageIcon icon = new ImageIcon(filepath);
			content = new JLabel(icon);
			content.setBackground(themeBackground);
			content.setForeground(themeForeground);
		} else if(new File(filepath).exists()){
			content.revalidate();
			content.repaint();
		} else if(fc == null) {
			fc = new JFileChooser();
			fc.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					File f = fc.getSelectedFile();
					f.renameTo(new File(filepath));
					ImageIcon ic = new ImageIcon(filepath);
					content = new JLabel(ic);
					content.setBackground(themeBackground);
					content.setForeground(themeForeground);
					parent.overrideContent(Window.encase(content, -1, -1));
					Window.refresh();
				}
			});
		} else {
			fc.revalidate();
			fc.repaint();
		}
	}

	@Override
	public Structure getType() {
		return Structure.IMAGE;
	}

	@Override
	public void onAction(FolderAbstract parent) {
		if(parent.getType() == Structure.FOLDER) {
			((Folder)parent).scrollable = true;
		}
		parent.setActive(this);
		if(content == null) {
			parent.overrideContent(fc);	
		} else {
			parent.overrideContent(content);	
		}
		Window.refresh();
	}

	@Override
	public JPanel onAction() {
		return Window.encase(fc, -1, -1);
	}

	@Override
	public void save(String filepath) {
		
	}
}