package SymbolTable;

import java.util.ArrayList;
import java.util.List;

/*
    mainprogram(){
        defINT a, b, c, maximum;
        defSTRING message;
        input(a);
        input(b);
        input(c);
        maximum = a;
        message = "first number is the biggest number";
        if(b > maximum){
            maximum = b;
            message = "second number is the biggest number";
        }
        if(c > maximum){
        maximum = c;
        message = "third number is the biggest number";
        }
        output(message);
        output(maximum);
    }

 */

public class Main {
    public static void main(String[] args) {
        List<Token> tokens = new ArrayList<>();
        tokens.add(new Token("a"));
        tokens.add(new Token("b"));
        tokens.add(new Token("c"));
        tokens.add(new Token("maximum"));
        tokens.add(new Token("message"));
        tokens.add(new Token("a"));
        tokens.add(new Token("b"));
        tokens.add(new Token("c"));
        tokens.add(new Token("maximum"));
        tokens.add(new Token("message"));
        tokens.add(new Token("0"));
        tokens.add(new Token("a"));
        tokens.add(new Token("b"));
        tokens.add(new Token("c"));
        tokens.add(new Token("maximum"));
        tokens.add(new Token("message"));
        tokens.add(new Token("a"));
        tokens.add(new Token("maximum"));
        tokens.add(new Token("message"));
        tokens.add(new Token("b"));
        tokens.add(new Token("maximum"));
        tokens.add(new Token("c"));
        tokens.add(new Token("message"));
        tokens.add(new Token("-3"));
        SortedSymbolTable symbolTable = new SortedSymbolTable();
        List<Token> pif = new ArrayList<>();
        for(var token:tokens) {
            symbolTable.add(token);
            pif.add(symbolTable.getToken(token.getSymbol()));
        }
        pif.forEach(System.out::println);
        System.out.println(' ');
        for(var x :symbolTable.tokens) {
            System.out.println(x.getSymbol() + ' ' + x.getIndex());
        }

    }
}
