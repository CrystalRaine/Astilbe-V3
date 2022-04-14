package truthTableGen;

import java.awt.Dimension;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TableDisplay extends JTable {
	private static final long serialVersionUID = 1L;
	InputParser parser;
    DefaultTableModel model;

    public TableDisplay(InputParser parser){
        this.parser = parser;
        model = new DefaultTableModel(0,0);
        this.setModel(model);
    }

    public void update(){
        int rows = (int) Math.pow(2, parser.getVars().size());
        int cols = parser.getHeaders().size();

        model = new DefaultTableModel(((Object[])(parser.getHeaders().toArray())), rows);
        this.setModel(model);
        model.setColumnCount(cols);

        for(int k = 0; k < parser.getHeaders().size(); k++){
            this.getColumnModel().getColumn(k).setPreferredWidth(parser.getHeaders().get(k).length() * 10);
            this.getColumnModel().getColumn(k).setCellRenderer(new CustomTableRenderer(parser));
            if(parser.getEquations().contains(parser.getHeaders().get(k))){
                this.getColumnModel().getColumn(k).setHeaderRenderer(new CustomHeaderRenderer(parser));
            }
        }
        for(int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {  // iterate across then down
                char colChar = parser.getValue(parser.getHeaders().get(j), i) ? 'T' : 'F';
                this.setValueAt(colChar, i, j);
            }
        }
    }
}
