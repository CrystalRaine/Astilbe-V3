package truthTableGen;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

import java.awt.*;

public class CustomHeaderRenderer  extends DefaultTableCellRenderer {

    public static InputParser parser;

    public CustomHeaderRenderer(InputParser parser) {
        this.parser = parser;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
        JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
        if(parser.getEquations().contains((String)value)){  // if it is a full equation
            l.setBackground(new Color(175,175,175));
            boolean taut = true;
            boolean contra = true;
            for(int i = 0; i < Math.pow(2,parser.getVars().size()); i++){
                if(parser.getValue((String) value, i)){
                    contra = false;
                } else {
                    taut = false;
                }
            }
            if(contra){
                l.setBackground(new Color(175,0,0));
            }
            if(taut){
                l.setBackground(new Color(0,175,0));
            }
        }
        l.setHorizontalAlignment(SwingConstants.CENTER);
        return l;
    }
}
