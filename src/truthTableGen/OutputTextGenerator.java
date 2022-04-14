package truthTableGen;

public class OutputTextGenerator {
    private static InputParser parser;

    public static void setParser(InputParser p){
        parser = p;
    }

    public static String generateLaTeX(){
        String header = "\\begin{tabular}{|";
        String headerLabels = "";
        String content = "";
        String tail = "\\end{tabular}";

        for(int i = 0; i < parser.getVars().size(); i++){
            header+= "c";
        }
        header += "|";
        for(int i = 0; i < parser.getHeaders().size() - parser.getVars().size(); i++){
            header += "c|";
        }
        header += "}\n\\hline\n";
        for(String head : parser.getHeaders()){
            if(headerLabels.length() > 0){
                headerLabels += " & ";
            }
            headerLabels += "$" + head + "$";
        }
        headerLabels += "\\\\\n\\hline\\hline\n";

        headerLabels = headerLabels.replaceAll("¬", " \\\\neg ");
        headerLabels = headerLabels.replaceAll("⊼", " nand ");
        headerLabels = headerLabels.replaceAll("⊽", " nor ");
        headerLabels = headerLabels.replaceAll("∧", " \\\\wedge ");
        headerLabels = headerLabels.replaceAll("⊕", " \\\\oplus ");
        headerLabels = headerLabels.replaceAll("∨", " \\\\vee ");
        headerLabels = headerLabels.replaceAll("→", " \\\\rightarrow ");
        headerLabels = headerLabels.replaceAll("↔", " \\\\leftrightarrow ");

        for(int j = 0; j < (int) Math.pow(2, parser.getVars().size()); j++) {
            for (int i = 0; i < parser.getHeaders().size(); i++) {
                if (i != 0) {
                    content += " & ";
                }
                content += parser.getValue(parser.getHeaders().get(i), j) ? 'T' : 'F';
            }
            content += "\\\\" + "\n\\hline\n";
        }
        return header + headerLabels + content + tail;
    }

    public static String getEquationWithSymbols(){
        return InputParser.peel(parser.getInput(), 0);

    }
}
