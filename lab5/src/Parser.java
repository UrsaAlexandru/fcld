import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Parser {
    private final Grammar grammar;
    private String state;
    private int position;
    private LinkedList<String> workingStack;
    private LinkedList<String> inputStack;

    public Parser(Grammar grammar) {
        this.grammar = grammar;
        this.state="s";
        this.position=1;
        this.workingStack = new LinkedList<>();
        this.inputStack = new LinkedList<>();
    }

    private void expand() {

    }

    private void advance() {
        this.position+=1;
        this.workingStack.addLast(this.inputStack.getFirst());
        this.inputStack.removeFirst();
    }

    private void momentaryInsuccess() {
        this.state="b";
    }

    private void back() {
        this.position-=1;
        this.inputStack.addFirst(this.workingStack.getLast());
        this.workingStack.removeLast();
    }

    private void anotherTry() {

    }

    private void success() {
        this.state="f";
    }

    public void parse() {
        //configuration  = (q, 1, epsilon, S)
        this.state="q";
        this.position=1;

        while (this.state!="f" && this.state!="e"){

        }

    }

}
