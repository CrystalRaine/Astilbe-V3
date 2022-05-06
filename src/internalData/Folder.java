package internalData;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JScrollPane;

import display.FileStack;
import display.Window;

public class Folder extends FolderAbstract {

	public boolean scrollable = true;
	
	public Folder(String label, String filepath, FolderAbstract parent) {
		super(label, filepath, parent);
	}

	@Override
	public void onAction(FolderAbstract parent) {
		contentPane.setMaximumSize(getContainedSize());
		contentPane.setPreferredSize(getContainedSize());
		contentPane.revalidate();
		contentPane.repaint();
		parent.overrideContent(contentPane);
		parent.setActive(this);
		Window.refresh();
	}
	
	@Override
	public Structure getType() {
		return Structure.FOLDER;
	}

	@Override
	protected void getStack() {
		FileStack fs = new FileStack(this, BoxLayout.Y_AXIS);
		contentPane.add(fs, BorderLayout.LINE_START);
		contentPane.setMaximumSize(getContainedSize());
		contentPane.setPreferredSize(getContainedSize());
		contentPane.revalidate();
		contentPane.repaint();
	}

	@Override
	protected void clearStack() {
		if(((BorderLayout)contentPane.getLayout()).getLayoutComponent(BorderLayout.LINE_START) != null) {
			contentPane.remove(((BorderLayout)contentPane.getLayout()).getLayoutComponent(BorderLayout.LINE_START));
		}
	}

	@Override
	public void overrideContent(Component addPanel) {
		if(((BorderLayout)contentPane.getLayout()).getLayoutComponent(BorderLayout.CENTER) != null) {
			contentPane.remove(((BorderLayout)contentPane.getLayout()).getLayoutComponent(BorderLayout.CENTER));
		}
		if(scrollable) {
			JScrollPane sp = new JScrollPane(addPanel);
			sp.setBackground(themeBackground);
			sp.setForeground(themeForeground);
			sp.setBorder(null);
			contentPane.add(sp, BorderLayout.CENTER);
		} else {
			contentPane.add(addPanel, BorderLayout.CENTER);
		}
		Window.refresh();
	}
	
	@Override
	public void save(String filepath) {
		contentPane.setMaximumSize(getContainedSize());
		contentPane.setPreferredSize(getContainedSize());
		contentPane.revalidate();
		contentPane.repaint();
		
		File f = new File(filepath);
		if(!filepath.substring(filepath.length()-4).equals(".inv")) {
			if(isHidden()) {
				File g = new File(filepath + ".inv");
				f.renameTo(g);
			}
		}
		
		for(TabItem t : contained) {
			t.save(filepath + "\\" + t.getLabel());
		}
	}
	
	public void setSize(Dimension size) {
		this.size = size;
		contentPane.setMaximumSize(size);
		contentPane.setPreferredSize(getContainedSize());
	}
	
	@Override
	protected Dimension getContainedSize() {
		if(size == null) {
			return new Dimension(contentPane.getSize().width, contentPane.getSize().height);
		} else {
			return size;
		}
	}

	public int getstackWidth() {
		return ((BorderLayout)contentPane.getLayout()).getLayoutComponent(BorderLayout.LINE_START).getWidth();
	}

}
