package Scanner;

import SymbolTable.SortedSymbolTable;
import SymbolTable.Token;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class LexicalAnalyzer {
    String delimiters = "[]{}()+-*/%,;>=<!^ ";
    List<Token> PIF = new ArrayList<>();
    SortedSymbolTable symbolTable = new SortedSymbolTable();
    List<String> reservedWords;

    public LexicalAnalyzer(){
        reservedWords = readFromFile("lab3/src/reserved.txt");
    }

    public List<String> tokensOperators(List<String> tokens){
        int keep_index = 0;
        boolean found = false;
        for(int i = 0; i < tokens.size(); i++){
            if("!<=>".contains(tokens.get(i))){ // the pattern is (< | = | >) =
                if(found){
                    found = false;
                    String newToken = tokens.get(keep_index) + tokens.get(i);
                    tokens.remove(i);
                    i--;
                    tokens.set(keep_index, newToken);
                    continue;
                }
                found = true;
                keep_index = i;
            }
            else{
                if(!tokens.get(i).equals(" ")){
                    found = false;
                }
            }
        }
        return tokens;
    }

    public List<String> tokensArray(List<String> tokens){
        boolean found = false;
        int keep_index = 0;
        for(int i = 0; i < tokens.size(); i++){
            if(found){
                if(!tokens.get(i).equals(" ")) {
                    String newToken = tokens.get(keep_index) + tokens.get(i);
                    if ("]".contains(tokens.get(i))){
                        found = false;
                    }
                    tokens.remove(i);
                    i--;
                    tokens.set(keep_index, newToken);
                    if(tokens.get(keep_index).equals("[]")) {
                        tokens.set(keep_index - 1, tokens.get(keep_index - 1) + newToken);
                        tokens.remove(keep_index);
                        i--;
                    }
                    continue;
                }
            }
            if(tokens.get(i).equals("[")) {
                keep_index = i;
                found = true;
            }
            if(tokens.get(i).equals("]") && !found){
                //something wrong
                String newToken = tokens.get(i-1) + tokens.get(i);
                if ("=]".contains(tokens.get(i))){
                    found = false;
                }
                tokens.set(i-1, newToken);
                tokens.remove(i);
                i--;
            }
        }
        return tokens;
    }

    public List<String> tokensStrings(List<String> tokens){
        boolean found = false;
        int keep_index = 0;
        for(int i = 0; i < tokens.size(); i++){
            if(found){
                String newString = tokens.get(keep_index) + ' ' + tokens.get(i);
                if(tokens.get(i).trim().charAt(tokens.get(i).trim().length()-1) == '"'){
                    found = false;
                }
                tokens.remove(i);
                i--;
                tokens.set(keep_index, newString);
                continue;
            }
            if(tokens.get(i).charAt(0) == '"'){
                found = true;
                keep_index = i;
            }
        }
        return tokens;
    }

    public List<String> tokensNumbers(List<String> tokens){
        boolean found = false;
        boolean found1 = false;
        int keep_index = 0;
        for(int i = 0; i < tokens.size(); i++){
            if(found1){
                String newToken = tokens.get(keep_index) + tokens.get(i);
                tokens.remove(i);
                i--;
                tokens.set(keep_index, newToken);
                found1 = false;
                found = false;
                continue;
            }
            if(found){
                if("+-".contains(tokens.get(i))){
                    found1 = true;
                    keep_index = i;
                }
                continue;
            }
            if(tokens.get(i).equals("=")){
                found = true;
            }
        }
        return tokens;
    }
    public void Scan(String programName){
        boolean lexicallyCorrect = true;
        String programFile = "lab3/src/" + programName + ".txt";
        List<String> program;
        program = readFromFile(programFile);
        int lineNumber = 1;
        for (String line : program) {
            //split the line and add the tokens to a list of tokens
            //detect tokens
            StringTokenizer st = new StringTokenizer(line, delimiters, true);
            List<String> tokens = new ArrayList<>();
            while (st.hasMoreTokens()) {
                String token = st.nextToken();
                if (!token.equals(" ")) {
                    tokens.add(token);
                }
            }

            //OPERATORS
            tokens = tokensOperators(tokens);
            //STRINGS
            tokens = tokensStrings(tokens);
            //CONSTANTS
            tokens = tokensNumbers(tokens);
            //ARRAYS
            tokens = tokensArray(tokens);


            //check if the token is reserved word/operator/separator/constant/identifier
            for(String token: tokens){
                //check if is a reserved word
                if(reservedWords.contains(token)){
                    Token reserved = new Token(token);
                    reserved.setIndex(-1);
                    PIF.add(reserved);
                }
                else{
                    //check if is a identifier or constant
                    if(isIdentifierOrConstant(token)){
                        if(symbolTable.find(token)){
                            PIF.add(symbolTable.getToken(token));
                        }
                        else{
                            Token newToken = new Token(token);
                            symbolTable.add(newToken);
                            PIF.add(newToken);
                        }
                    }
                    //lexical error here
                    else{
                        lexicallyCorrect = false;
                        System.out.println(token);
                        System.out.println("LEXICAL ERROR ON LINE " + lineNumber);
                    }
                }
            }
            lineNumber++;
        }
        if(lexicallyCorrect)
            System.out.println("LEXICALLY CORRECT");
        writeToFile("lab3/src/"+programName+"/pif", PIF);
        writeToFile("lab3/src/"+programName+"/st", symbolTable.getTokens());
    }

    public Boolean isIdentifierOrConstant(String token) {
        // matches identifier
        if (token.matches("^[a-zA-Z_]([a-zA-Z0-9_])*")) {
            return true;
        }
        //matches array
        if(token.matches("^[a-zA-Z_]([a-zA-Z0-9_])*[\\[][\\]]")){
            return true;
        }
        if(token.matches("^\\[(([+-])?(([1-9][0-9]*)|0)([\\],])?)*")){
            return true;
        }
        //matches integer
        if (token.matches("^([+-])?([1-9][0-9]*)|0")) {
            return true;
        }

        //matches boolean
        if (token.matches("true|false")) {
            return true;
        }
        //matches char
        if (token.matches("^'[a-zA-Z0-9_~`!@#$%^&*()_ +-{}]'$")) {
            return true;
        }
        //matches string
        if (token.matches("^\"([a-zA-Z0-9 _])*+\"$")){
            return true;
        }
        return false;
    }

    public List<String> readFromFile(String fileName){
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))){
            return reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>();
    }

    public void writeToFile(String fileName, List<Token> tokens){
        try(PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName)))){
            for(var token: tokens)
                writer.println(token);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
