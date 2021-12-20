

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception{
        Grammar g = new Grammar();
        g.readGrammar("D:\\Liviu\\Faculta\\3rd year\\1st semester\\FLCD\\RepoUrsaIulian\\fcld\\lab5\\src\\g1.txt");
        //g.checkCFG();
        System.out.println(g.getProduction(3));
    }
}
