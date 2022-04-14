package truthTableGen;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.List;

public class CustomTableRenderer extends DefaultTableCellRenderer {

    public static InputParser parser;

    public CustomTableRenderer(InputParser parser) {
        this.parser = parser;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        List<String> equ = parser.getEquations();

        JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
        l.setHorizontalAlignment(SwingConstants.CENTER);
        l.setOpaque(true);

        boolean allTrue = true;
        boolean allFalse = true;

        for(String s : equ){
            if(!parser.getValue(s, row)){
                allTrue = false;
            } else {
                allFalse = false;
            }
        }
        if (allTrue) {
            l.setBackground(new Color(230, 255, 230));
        } else if(allFalse){
            l.setBackground(new Color(255, 230, 230));
        } else {
            l.setBackground(Color.WHITE);
        }
        return l;
    }
}
