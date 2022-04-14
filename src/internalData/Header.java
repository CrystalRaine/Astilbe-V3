package internalData;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import display.CommandLine;
import display.FileStack;
import display.Window;

public class Header extends FolderAbstract {

	public Header(String label, String filepath, FolderAbstract parent) {
		super(label, filepath, parent);
		contentPane.setBackground(themeBackground);
		contentPane.setForeground(themeForeground);
	}

	@Override
	public Structure getType() {
		return Structure.HEADER;
	}

	@Override
	public void onAction(FolderAbstract parent) {
		
	}
	
	public Component getPane() {
		return contentPane;
	}

	@Override
	public void getStack() {
		JPanel jp = new JPanel();
		jp.setBackground(themeForeground.darker().darker().darker().darker());
		jp.setForeground(themeForeground);
		jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
		
		FileStack fs = new FileStack(this, BoxLayout.Y_AXIS);
		jp.add(Window.encase(new CommandLine(), -1, 20));
		jp.add(fs);
		
		contentPane.add(jp, BorderLayout.LINE_START);
	}

	@Override
	protected void clearStack() {
		if(((BorderLayout)contentPane.getLayout()).getLayoutComponent(BorderLayout.LINE_START) != null) {
			contentPane.remove(((BorderLayout)contentPane.getLayout()).getLayoutComponent(BorderLayout.LINE_START));
		}
	}

	@Override
	public void save(String filepath) {
		for(TabItem t : contained) {
			t.save(filepath + "\\" + t.getLabel());
		}
	}
	
	@Override
	protected Dimension getContainedSize() {
		return new Dimension(contentPane.getSize().width, contentPane.getSize().height);
	}
}