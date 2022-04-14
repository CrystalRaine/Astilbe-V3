package internalData;

import java.io.File;

import javax.swing.JLabel;
import javax.swing.JPanel;

import display.Window;

public class Link extends FileAbstract {

	TabItem content;
	
	File linkedTo;
	
	public Link(String label, FolderAbstract parent) {
		super(label, parent);
		
		if(recursiveRecursionCheck(label, parent)) {
			content = Window.getByName(label.toLowerCase());
		}
	}
	
	private boolean recursiveRecursionCheck(String label, TabItem parent) {
		if(parent.getLabel().equals(label)) {
			return false;
		} else if(parent.getType() == Structure.HEADER){
			return true;
		} else {
			return recursiveRecursionCheck(label, parent.parent);
		}
	}
	
	@Override
	public void delete() {
		File f = new File(parent.getFilepath() + "\\" + getLabel() + ".lnk");
		f.delete();
		parent.removeSubItem(this);
		Window.refresh();
	}
	
	@Override
	public String getExtention() {
		return "lnk";
	}

	@Override
	public void refresh() {
		if(content != null) {
			content.refresh();
		}
	}

	@Override
	public Structure getType() {
		return Structure.LINK;
	}

	@Override
	public void onAction(FolderAbstract parent) {
		if(content != null) {
			parent.overrideContent(content.onAction());
			parent.setActive(this);
			Window.refresh();
		} else {
			JLabel norec = new JLabel("no recursion for you :(");
			norec.setBackground(themeBackground);
			norec.setForeground(themeForeground);
			norec.setOpaque(true);
			parent.overrideContent(Window.encase(norec, -1, -1));
			parent.setActive(this);
			Window.refresh();
		}
	}

	@Override
	public JPanel onAction() {
		if(content != null) {
			parent.setActive(this);
			return content.onAction();
		} else {
			JLabel norec = new JLabel("no recursion for you :(");
			norec.setBackground(themeBackground);
			norec.setForeground(themeForeground);
			norec.setOpaque(true);
			return Window.encase(norec, -1, -1);
		}
	}

	@Override
	public void save(String filepath) {
		// does not save, there is no content to save
	}

}
