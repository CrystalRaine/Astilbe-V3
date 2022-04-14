package internalData;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import javax.swing.BoxLayout;

import display.Window;

public class VerticallyDividedDoubleFolder extends FolderAbstract {
	
	private Folder folder1;
	private Folder folder2;
	
	public VerticallyDividedDoubleFolder(String label, String filepath, FolderAbstract parent) {
		super(label, filepath, parent);
	}

	@Override
	public Structure getType() {
		return Structure.VERTICAL_DOUBLE_FOLDER;
	}

	@Override
	public void onAction(FolderAbstract parent) {
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));

		contentPane.removeAll();
		contentPane.add(folder1.onAction());
		contentPane.add(folder2.onAction());
		
		parent.overrideContent(contentPane);
		parent.setActive(this);
		
		folder1.setSize(getContainedSize());
		folder2.setSize(getContainedSize());
		Window.refresh();
	}
	
	@Override
	public TabItem getByName(String name) {
		TabItem found = null;
		for(TabItem ti : contained) {
			TabItem tbIt = ((FolderAbstract)ti).getByName(name);
			found = tbIt == null ? found : tbIt;
		}
		return found;
	}
	
	@Override
	protected void getStack() {
		if(folder1 == null) {
			folder1 = new Folder("vfolder1", filepath + "\\vfolder1", this);
			folder2 = new Folder("vfolder2", filepath + "\\vfolder2", this);
			contained.add(folder1);
			contained.add(folder2);
		}
		
		folder1.setSize(getContainedSize());
		folder2.setSize(getContainedSize());
		contentPane.setMaximumSize(getContainedSize());
		contentPane.setPreferredSize(getContainedSize());
		contentPane.revalidate();
		contentPane.repaint();
		folder1.getStack();
		folder2.getStack();
	}

	@Override
	protected void clearStack() {
		folder1.clearStack();
		folder2.clearStack();
	}

	@Override
	public void save(String filepath) {
		folder1.setSize(getContainedSize());
		folder2.setSize(getContainedSize());
		
		File f = new File(filepath);
		if(!f.exists()) {
			f.mkdir();
			if(isHidden()) {
				File g = new File(filepath.substring(filepath.length()-4));
				System.out.println(g.getAbsolutePath());
				g.delete();
			}
		}
		
		folder1.save(filepath + "\\vfolder1");
		folder2.save(filepath + "\\vfolder2");
	}
	
	public Folder get1() {
		return folder1;
	}
	
	public Folder get2() {
		return folder2;
	}

	@Override
	protected Dimension getContainedSize() {
		return new Dimension((contentPane.getSize().width/2), contentPane.getSize().height);
	}

}
