

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception{
        Grammar g = new Grammar();
        g.readGrammar("src/g2.txt");
        g.checkCFG();
    }
}
