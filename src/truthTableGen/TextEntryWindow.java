package truthTableGen;

import javax.swing.*;
import java.awt.*;

public class TextEntryWindow extends JTextField {
    public TextEntryWindow(){
        this.setText("");
        this.setPreferredSize(new Dimension(300,20));
        this.setMargin(new Insets(0,5,0,5));
        this.setMaximumSize(new Dimension(1000000,25));
    }

}
