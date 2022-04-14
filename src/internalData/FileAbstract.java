package internalData;

public abstract class FileAbstract extends TabItem {

	public FileAbstract(String label, FolderAbstract parent) {
		super(label, parent);
	}

	public abstract String getExtention();
	
}	
