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
        this.state = "s";
        this.position = 1;
        this.workingStack = new LinkedList<>();
        this.inputStack = new LinkedList<>();
    }

    private void expand() {
        String nonTerminal = this.inputStack.getFirst();
        String firstProduction = this.grammar.getValues(nonTerminal).get(0);
        this.workingStack.addLast(firstProduction);
        this.inputStack.removeFirst();
        this.inputStack.addFirst(firstProduction);
    }

    private void advance() {
        this.position += 1;
        this.workingStack.addLast(this.inputStack.getFirst());
        this.inputStack.removeFirst();
    }

    private void momentaryInsuccess() {
        this.state = "b";
    }

    private void back() {
        this.position -= 1;
        this.inputStack.addFirst(this.workingStack.getLast());
        this.workingStack.removeLast();
    }

    private void anotherTry() {
        String last = this.workingStack.getLast();

        List<String> productions = this.grammar.getValues(last);

    }

    private void success() {
        this.state = "f";
    }

    public void buildStringOfProd(LinkedList<String> wotkingStack){

    }

    public void parse(List<String> w) {
        //configuration  = (q, 1, epsilon, S)
        this.state = "q";
        this.position = 1;

        while (!this.state.equals("f") && !this.state.equals("e")) {
            if (this.state.equals("q")) {
                if (this.position==w.size()+1 && this.inputStack.isEmpty()){
                    this.success();
                }
                else {
                    if (this.inputStack.getFirst().equals("A")){
                        this.expand();
                    }
                    else {
                        if(this.inputStack.getFirst().equals(w.get(this.position))){
                            this.advance();
                        }
                        else{
                            this.momentaryInsuccess();
                        }
                    }
                }
            }
            else {
                if (this.state.equals("b")){
                    if(this.workingStack.getFirst().equals("a")){
                        this.back();
                    }
                    else{
                        this.anotherTry();
                    }
                }
            }
        }

        if(this.state.equals("e")){
            System.out.println("Error!");
        }
        else{
            System.out.println("Sequence accepted!");
            this.buildStringOfProd(this.workingStack);
        }

    }

}
