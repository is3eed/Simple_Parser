package parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;
import java.util.Hashtable;

public class Parser {

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("input.txt"); // read expressions from a file 
        System.out.println("\nStart Parsing "); 
        Scanner sc = new Scanner(file);
        Hashtable<String, String> h = new Hashtable<>();
        Stack<String> s; 
        int c = 1;
        String terminals[] = {"id", "+", "*", "(", ")", "$"};  // terminal symbols
        boolean first = false;
        boolean second = false;
        // below is the CGF which generate  all infix expressions
        // instead of having a 2D array having for example E (non-terminal) in column head, and id (terminal) in row head
        // hashmap key referd to be terminal symbol + non-terminal symbol with spaces between them
        // for example the key of cell crossed by column E and id row, is 'E id', and so on for other productions
        h.put("E id", "T E`");
        h.put("E (", "T E`");
        h.put("E` +", "+ T E`");
        h.put("E` )", "null");
        h.put("E` $", "null");
        h.put("T id", "F T`");
        h.put("T (", "F T`");
        h.put("T` +", "null");
        h.put("T` *", "* F T`");
        h.put("T` )", "null");
        h.put("T` $", "null");
        h.put("F id", "id");
        h.put("F (", "( E )");
        System.out.println("-----------------------------");
        while (sc.hasNext()) { // if there are more arithmetic expressions to be evaluated
            int ip = 0; // input pointer
            System.out.println("Parsing Expression number (" + c++ + ")");
            String arith = sc.nextLine(); // read whole arithmetic expressions 
            String inputBuffer[] = arith.split(" "); // split tokens of arithmetic expressions by space
            // for eample if an arithmetic expressions id + id $, its saved in array like
            // arr[0] == id arr[1] == + arr[2] == id arr[3] == $
            s = new Stack<>();
            s.add("$"); // push $ to stack, to terminate later if it matches input buffer
            s.add("E"); // push starting symbol
            String x = s.peek(); // Let ‘X’ be the top stack symbol
            System.out.println("The input buffer is : " + arith);
            while (ip < inputBuffer.length || x.equals("$")) {
                String a = inputBuffer[ip]; // ‘a’ the symbol pointed to by ‘ip’
                for (int i = 0; i < terminals.length; i++) {
                    if (x.equals(terminals[i])) { // to check if X is a terminal or $
                        first = true;
                        second = true;
                        if (x.equals(a)) { // if X == a
                            s.pop(); // pop a from stack (a refers to peek) 
                            ip++; // advance ‘ip’ to point to next input symbol
                            System.out.println("");
                            break;
                        } else { // otherwise error
                            System.out.println("Syntax error");
                            ip = inputBuffer.length + 100;
                            break;
                        }
                    }
                }
                String ee = h.get(x + " " + a); // value of hashmap 
                if (ee != null && ee.equals("null")) { // if production of a cross between terminal and non-terminal is null i.e E --> null
                    System.out.println(x + " --> " + ee); // print that X produce null 
                    s.pop(); // pop x from stack 
                    first = true;
                    second = true;
                }
                if (!first && ee != null) {
                    second = true;
                    String toP[]; // array variable will hold elements to be pushed
                    if (ee.contains(" ")) {
                        toP = ee.split(" ");
                    } else {
                        toP = new String[1];
                        toP[0] = ee;
                    }
                    String lhs = s.peek();
                    s.pop();
                    for (int i = toP.length - 1; i > -1; i--) {
                        s.push(toP[i]);
                    }

                    System.out.println(lhs + " --> " + ee); // i.e E --> T E`  || T --> null

                }
                if (!second) {
                    System.out.println("Syntax error");
                    ip = inputBuffer.length + 100;
                }
                if (s.peek().equals("$") && inputBuffer[ip].equals("$")) { // to terminate and stop parsing
                    System.out.println("Parsing successfully halts");
                    System.out.println("-----------------------------");
                    System.out.println("");
                    break;
                }
                x = s.peek();
                first = false;
                second = false;
            }
            if (ip > inputBuffer.length + 1) {
                System.out.println("Stop parsing current expression \n");
              
            }
        }
    }
}
