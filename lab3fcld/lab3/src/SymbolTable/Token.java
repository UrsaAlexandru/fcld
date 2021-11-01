package SymbolTable;

public class Token {
    String symbol;
    int index;

    @Override
    public String toString() {
        return symbol + ' ' + index;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Token(String symbol){
        this.symbol = symbol;
    }
}
