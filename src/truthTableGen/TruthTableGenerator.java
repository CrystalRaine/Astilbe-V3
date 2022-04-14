package truthTableGen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class TruthTableGenerator extends JPanel {
	private static final long serialVersionUID = 1L;

	InputParser parser;

    TableDisplay tableDisplay;
    public TextEntryWindow textEntry;

    JPanel solutionPanel = new JPanel();

    public TruthTableGenerator(){
        parser = new InputParser();

        textEntry = new TextEntryWindow();
        textEntry.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }
            @Override
            public void keyPressed(KeyEvent e) {
            }
            @Override
            public void keyReleased(KeyEvent e) {
                inputChanged(textEntry.getText());
            }
        });

    	tableDisplay = new TableDisplay(parser);
    	
        JScrollPane sp = new JScrollPane(tableDisplay); // truth table shown in scroll pane
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        OutputTextGenerator.setParser(parser);
        ButtonPanel buttonPanel = new ButtonPanel(solutionPanel);
        buttonPanel.setTruthTable(sp);

        solutionPanel.setLayout(new BorderLayout());
        solutionPanel.add(display.Window.encase(sp, -1, -1), BorderLayout.CENTER);  // default display

        this.add(textEntry, BorderLayout.NORTH);
        this.add(solutionPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

    public void inputChanged(String input){
        parser.setInputText(input);
        if(parser.update()) {
            tableDisplay.update();
        }
    }
    
}
