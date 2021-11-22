import Scanner.LexicalAnalyzer;
import FiniteAutomata.FiniteAutomata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args){
        //LexicalAnalyzer sc = new LexicalAnalyzer();
        //sc.Scan("p4");
        FiniteAutomata fa = new FiniteAutomata();
        try {
            fa.runMenu();
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
