import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Grammar {
    private List<String> N; //non-terminals (starting states?)
    private List<String> T; // terminals (check if it is alphabet?)
    private String S; //starting symbol
    //private Map<ArrayList<String>, ArrayList<List<String>>> P; // productions
    private Map<List<String>, List<String>> P; // productions
    private List<String> A;


    public Grammar() {
        this.P = new HashMap<>();
    }

    public void readGrammar(String fileName) throws Exception {
        this.A = new ArrayList<>();
        List<String> stringList;
        String leftHandSide = "";
        String rightHandSide = "";
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            N = getStatesFromLine(br);
            T = getStatesFromLine(br);
            S = getStatesFromLine(br).get(0);
            System.out.println(N);
            System.out.println(T);
            System.out.println(S);
            br.readLine();

            while ((line = br.readLine()) != null) {
                List<String> lineList = Arrays.asList(line.split("->"));
                leftHandSide = lineList.get(0);
                rightHandSide = lineList.get(1);

                List<String> keyList = Arrays.asList(leftHandSide.split(" "));
                List<String> valueList = Arrays.asList(rightHandSide.split("\\|"));

                this.P.put(keyList, valueList);
                this.A.addAll(valueList);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(this.P);
        System.out.println(this.A);
    }

    public void readGrammar1(String fileName) throws Exception {
        List<String> stringList;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            N = getStatesFromLine(br);
            T = getStatesFromLine(br);
            S = getStatesFromLine(br).get(0);
            System.out.println(N);
            System.out.println(T);
            System.out.println(S);
            br.readLine();
            while ((line = br.readLine()) != null) {
                List<String> lineList = Arrays.asList(line.split(" "));
                int i = 0;
                ArrayList<String> key = new ArrayList<>();
                while (!lineList.get(i).contains("->")) {
                    String[] token = lineList.get(i).split("\\|");
                    for (String j : token) {
                        if (!key.contains(j) && !j.equals("|")) {
                            key.add(j);
                        }
                    }
                    i++;
                }
                //System.out.println(key);
                i++;
                //ArrayList<List<String>> value = new ArrayList<>();
                ArrayList<String> value = new ArrayList<>();
                while (i < lineList.size()) {
                    String[] token = lineList.get(i).split("\\|").clone();
                    for (String str : token) {
                        // List<String> prod = Arrays.asList(str.split(" "));
                        value.add(str);
                    }
                    i++;
                }
                P.put(key, value);
            }
            System.out.println(P);
//
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<String> getValues(String key) {
        for (List<String> keys : this.P.keySet()) {
            if (keys.contains(key)) {
                return this.P.get(keys);
            }
        }
        return null;
    }

    private List<String> getStatesFromLine(BufferedReader br) throws IOException {
        String line = br.readLine();
        List<String> list = Arrays.asList(line.split(" "));
        return list.subList(2, list.size());
    }

    public void checkCFG() throws Exception {
        if (!N.contains(S)) {
            throw new Exception("S is not in N");
        }
        for (Map.Entry element : P.entrySet()) {
            List<String> key = (List<String>) element.getKey();
            if (key.size() > 1) {
                throw new Exception("One key has more than one element");
            }
            for (String str : key) {
                if (!N.contains(str)) {
                    throw new Exception(str + " is not in N");
                }
            }
            List<List<String>> value = (List<List<String>>) element.getValue();
            for (List l : value) {
                for (Object o : l) {
                    String str = (String) o;
                    if (!N.contains(str) && !T.contains(str) && !str.equals("E")) { // Check for Epsilon, alphabet
                        throw new Exception(str + " is not found in N or T");
                    }
                }
            }
        }
    }

    public String getProduction(int index){
        index--;
        return this.A.get(index);
    }
}

