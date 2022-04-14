package display;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import internalData.FolderAbstract;
import internalData.Header;
import internalData.TabItem;
import internalData.TabItem.Structure;

public class Window extends JFrame {

	private static final long serialVersionUID = 1L;
	private static Window refresher;
	private static Header topLevel;
	
	public Window() {
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(750,500);
		this.setBackground(TabItem.themeBackground);
		this.setForeground(TabItem.themeForeground);
		
		refresher = this;
		
		this.addWindowListener(new WindowAdapter(){	// save on exit normally
			@Override
            public void windowClosing(WindowEvent e)
            {
                Window.refresh();
                e.getWindow().dispose();
            }
		});
		
		Runtime.getRuntime().addShutdownHook(new Thread() {	// ensure save on exit when computer is shut down
			public void run() {
				Window.refresh();
			}
		});
		
		this.addComponentListener(new ComponentListener() {

			@Override
			public void componentResized(ComponentEvent e) {
				Window.refresh();
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentShown(ComponentEvent e) {
				Window.refresh();
				
			}

			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
	public Component add(Component c, Header h) {
		topLevel = h;
		return super.add(c);
	}
	
	public static void refresh() {
		refresher.revalidate();
		refresher.repaint();
		if(topLevel != null) {
			topLevel.refresh();
			topLevel.save("data");
		}
	}
	
	public static TabItem getByName(String name) {
		return topLevel.getByName(name);
	}
	
	public static JPanel encase(Component b, int maxX, int maxY) {
		
		if(maxX <= 0) {
			maxX = Integer.MAX_VALUE;
		}
		if(maxY <= 0) {
			maxY = Integer.MAX_VALUE;
		}
		
		JPanel p = new JPanel();
		p.setLayout(new GridLayout());
		p.setMaximumSize(new Dimension(maxX, maxY));
		p.add(b);
		return p;
	}
}
