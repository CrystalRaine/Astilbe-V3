package display;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import internalData.FolderAbstract;
import internalData.TabItem;

public class FileStack extends JPanel {

	private static final long serialVersionUID = 1L;
	List<JButton> buttons = new ArrayList<JButton>();
	
	public FileStack(FolderAbstract parent, int direction) {
		setDirection(direction);
		this.setBackground(TabItem.themeForeground.darker().darker().darker().darker());
		this.setForeground(TabItem.themeForeground);
		
		JButton addButton = new JButton();
		addButton.addActionListener(new ActionListener() {
			FolderAbstract internalParent = parent;
			@Override
			public void actionPerformed(ActionEvent e) {
				internalParent.overrideContent(new AddPanel(internalParent));
				for(JButton button : buttons) {
					button.setEnabled(true);
				}
				addButton.setEnabled(false);
			}
		});
		addButton.setBackground(TabItem.themeAccent);
		addButton.setForeground(TabItem.themeForeground);
		addButton.setBorder(BorderFactory.createLineBorder(TabItem.themeBackground));
		addButton.setPreferredSize(new Dimension(120, 35));
		addButton.setText("Add Item");
		this.add(Window.encase(addButton, -1, 35));
		
		for(TabItem h : parent.getContained()) {
			JButton b = new JButton(" " + h.getLabel() + " ");
			buttons.add(b);
			b.addActionListener(new ActionListener() {
				private TabItem i = h;
				private FolderAbstract enclose = parent;
				private JButton thisButton = b;
				
				@Override
				public void actionPerformed(ActionEvent e) {
					for(JButton button : buttons) {
						button.setEnabled(true);
						button.setBackground(TabItem.themeBackground);
						b.setBorder(BorderFactory.createLineBorder(TabItem.themeAccent));
					}
					addButton.setEnabled(true);
					i.onAction(enclose);
					thisButton.setEnabled(false);
					b.setBackground(TabItem.themeAccent);
					b.setBorder(BorderFactory.createLineBorder(TabItem.themeBackground));
					Window.refresh();
				}
			});
			
			b.setForeground(TabItem.themeForeground);
			b.setBackground(TabItem.themeBackground);
			b.setBorder(BorderFactory.createLineBorder(TabItem.themeAccent));
			
			this.add(Window.encase(b, -1, 35));
			
		}
	}
	
	public void setDirection(int boxLayoutDirection) {
		this.setLayout(new BoxLayout(this, boxLayoutDirection));
	}
}
