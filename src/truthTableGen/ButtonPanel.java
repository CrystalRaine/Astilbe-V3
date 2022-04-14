package truthTableGen;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonPanel extends JPanel {

    JPanel solutionPanel;
    JScrollPane truthTable = null;
    JScrollPane columnProof = null;


    public ButtonPanel(JPanel solutionPanel){
        this.solutionPanel = solutionPanel;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //this.add(getSolveButtons());  // temporarily gone
        this.add(getCopyButtons());
    }

    public void setTruthTable(JScrollPane sp){
        truthTable = sp;
    }

    public void setColumnProof(JScrollPane sp){
        columnProof = sp;
    }

    public JPanel getSolveButtons(){
        JPanel solveButtons = new JPanel();
        solveButtons.setLayout(new BoxLayout(solveButtons, BoxLayout.X_AXIS));

        JButton truthTableButton = new JButton("Create Truth-Table");
        truthTableButton.setPreferredSize(new Dimension(300,25));
        truthTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                solutionPanel.removeAll();
                solutionPanel.add(truthTable, SwingConstants.CENTER);
                revalidate();
                repaint();
            }
        });

        JButton tCProof = new JButton("Create Two Column Proof");
        tCProof.setPreferredSize(new Dimension(300,25));
        tCProof.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                solutionPanel.removeAll();
                solutionPanel.add(columnProof, SwingConstants.CENTER);
                revalidate();
                repaint();
            }
        });

        solveButtons.add(truthTableButton);
        //solveButtons.add(tCProof);

        return solveButtons;
    }

    public JPanel getCopyButtons(){
        JPanel copyButtonPanel = new JPanel();

        JButton copyButton = new JButton("Copy LaTeX");
        copyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String myString = OutputTextGenerator.generateLaTeX();
                StringSelection stringSelection = new StringSelection(myString);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
            }
        });

        JButton symbolEquation = new JButton("Copy Symbols Equation");
        symbolEquation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String copy = OutputTextGenerator.getEquationWithSymbols();
                StringSelection stringSelection = new StringSelection(copy);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
            }
        });

        copyButtonPanel.add(copyButton);
        copyButtonPanel.add(symbolEquation);

        return copyButtonPanel;
    }
}
