package SymbolTable;

import java.util.*;

public class SortedST {
    List<Token> tokens;

    public SortedST() {
        tokens = new ArrayList<>();
    }

    public void print(){
        tokens.forEach(System.out::println);
    }

    public List<Token> getTokens(){
        return tokens;
    }

    public void add(Token symbol){
        if(!find(symbol.getSymbol())){
            tokens.add(symbol);
            tokens.sort(Comparator.comparing(Token::getSymbol));
            tokens.forEach(x -> x.setIndex(getPosition(x.getSymbol())));
        }
    }

    public int getPosition(String symbol){
        List<String> symbols = tokens.stream().map(Token::getSymbol).toList();
        if(symbols.contains(symbol))
            return symbols.indexOf(symbol);
        return -1;
    }

    public Token getToken(String symbol){
        return tokens.get(getPosition(symbol));
    }

    public Boolean exist(String symbol){
        return tokens.stream().map(x->x.getSymbol()).toList().contains(symbol);
    }

    public Boolean find(String symbol){
        List<String> symbols = tokens.stream().map(Token::getSymbol).toList();
        return symbols.contains(symbol);
    }
}
