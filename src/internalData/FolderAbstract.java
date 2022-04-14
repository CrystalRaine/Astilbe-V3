package internalData;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import display.Window;

public abstract class FolderAbstract extends TabItem {

	List<TabItem> contained = new ArrayList<TabItem>();
	JPanel contentPane;
	TabItem active;
	String filepath;
	private boolean hidden = false;
	protected Dimension size = null;
	
	public FolderAbstract(String label, String filepath, FolderAbstract parent) {
		super(label, parent);
		this.filepath = filepath;
		contentPane = new JPanel();
		contentPane.setBackground(themeBackground);
		contentPane.setForeground(themeForeground);
		contentPane.setLayout(new BorderLayout());
		getStack();
	}

	public List<TabItem> getContained() {
		return contained;
	}

	public void overrideContent(Component addPanel) {
		if(((BorderLayout)contentPane.getLayout()).getLayoutComponent(BorderLayout.CENTER) != null) {
			contentPane.remove(((BorderLayout)contentPane.getLayout()).getLayoutComponent(BorderLayout.CENTER));
		}
		contentPane.add(addPanel, BorderLayout.CENTER);
		Window.refresh();
	}
	
	@Override
	public JPanel onAction() {
		return contentPane;
	}

	public void addSubItem(TabItem item) {
		if(item.getType() == Structure.FOLDER || item.getType() == Structure.DOUBLE_FOLDER || item.getType() == Structure.VERTICAL_DOUBLE_FOLDER) {
			if(!((FolderAbstract)item).isHidden()) {
				contained.add(item);
				clearStack();
				getStack();
			}
		} else {
			contained.add(item);
			clearStack();
			getStack();
		}
	}
	
	@Override
	public void refresh() {
		contentPane.revalidate();
		contentPane.repaint();
		for(TabItem i : contained) {
			i.refresh();
		}
	}
	
	public TabItem getByName(String name) {
		TabItem found = null;
		for(TabItem ti : contained) {
			if(ti.getLabel().toLowerCase().equals(name)) {
				found = ti;
			} 
			if(ti.getType() == Structure.FOLDER || ti.getType() == Structure.DOUBLE_FOLDER  || ti.getType() == Structure.VERTICAL_DOUBLE_FOLDER || ti.getType() == Structure.HEADER) {
				TabItem tbIt = ((FolderAbstract)ti).getByName(name);
				found = tbIt == null ? found : tbIt;
			}
		}
		return found;
	}
	
	public void setActive(TabItem item) {
		active = item;
	}

	public void removeSubItem(TabItem h) {
		if(h == active) {
			removeContent();
		}
		contained.remove(h);
		clearStack();
		getStack();
	}

	public String getFilepath() {
		return filepath;
	}

	public void removeContent() {
		if(((BorderLayout)contentPane.getLayout()).getLayoutComponent(BorderLayout.CENTER) != null) {
			contentPane.remove(((BorderLayout)contentPane.getLayout()).getLayoutComponent(BorderLayout.CENTER));
		}
	}
	
	public void hide() {
		if(!(label.length() > 4) || !label.substring(label.length()-4).equals(".inv")) {
			label = label + ".inv";
		}
		hidden = true;
		//parent.removeSubItem(this);
		Window.refresh();
		parent.clearStack();
		parent.getStack();
	}
	
	public void unHide() {
		if(label.length() > 4 && label.substring(label.length()-4).equals(".inv")) {
			label = label.substring(0,label.length()-4);
		}
		hidden = false;
		parent.addSubItem(this);
		parent.clearStack();
		parent.getStack();
	}
	
	public boolean isHidden() {
		return hidden;
	}
	
	protected abstract void getStack();
	
	protected abstract void clearStack();

	protected abstract Dimension getContainedSize();

	
}
