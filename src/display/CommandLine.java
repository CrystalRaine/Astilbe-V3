package display;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

import internalData.FolderAbstract;
import internalData.TabItem;
import internalData.TabItem.Structure;

public class CommandLine extends JTextField {

	private static final long serialVersionUID = 1L;
	private static CommandLine stThis;
	
	public CommandLine() {
		stThis = this;
		this.setForeground(TabItem.themeForeground);
		this.setBackground(TabItem.themeAccent);
		this.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
		
		this.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == 10) {
					parseCommand(getText());
					setText("");
					requestFocus();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				
			}
			
		});
	}
	
	public static String getCurrentEntry(boolean isLink) {
		String ret = stThis.getText();
		if(ret.equals("") || ret.equals("data") || ret.equals("metadata.inf") || ret.equals("metadata")) {
			return null;
		}
		
		if(Window.getByName(ret) != null && !isLink) {	// no duplicate names
			return null;
		}
		
		stThis.setText("");
		stThis.requestFocus();
		return ret;
	}
	
	private static void parseCommand(String command) {
		command = command.toLowerCase();
		Scanner sc = new Scanner(command);
		if(!sc.hasNext()) {
			sc.close();
			return;
		}
		String co = sc.next();
		if(co.equals("save")) {
			Window.refresh();
		}
		if(co.equals("delete") && sc.hasNext()) {
			String fileNameToDelete = sc.nextLine();
			fileNameToDelete = fileNameToDelete.substring(1);
			TabItem toDelete = Window.getByName(fileNameToDelete);
			if(toDelete != null) {
				toDelete.delete();
			}
			while(Window.getByName(fileNameToDelete) != null) {
				Window.getByName(fileNameToDelete).delete();
			}
			Window.refresh();
		}
		if(co.equals("hide") && sc.hasNext()) {
			TabItem type = Window.getByName(sc.next());
			if(type != null) {
				if(type.getType() == Structure.FOLDER || type.getType() == Structure.DOUBLE_FOLDER || type.getType() == Structure.VERTICAL_DOUBLE_FOLDER) {
					((FolderAbstract)type).hide();
				}
			}
			Window.refresh();
		}
		sc.close();
	}
	
}
