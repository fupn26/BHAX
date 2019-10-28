/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prologfamily;

import java.util.Map;
import org.jpl7.Query;
import org.jpl7.Term;

/**
 *
 * @author fupn26
 */
public class PrologFamily {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        String s = "consult('family.pl')";
        Query q = new Query(s); //the exception is thrown here
        System.out.println(q.hasSolution());

        String t2 = "apa(gréta)";
        System.out.println(t2 + " is " + (Query.hasSolution(t2) ? "provable" : "not provable"));
        
        String t3 = "nagyapja(X, matyi)";
        System.out.println("each solution of " + t3);
        Query q3 = new Query(t3);
        while (q3.hasMoreSolutions()) {
            Map<String, Term> s3 = q3.nextSolution();
            System.out.println("X = " + s3.get("X"));
        }        

        String t4 = "nagyapa(X)";
        System.out.println("first solution of " + t4 + ": X = " + Query.oneSolution(t4).get("X"));
        
        System.out.println("each solution of " + t4);
        Query q4 = new Query(t4);
        while (q4.hasMoreSolutions()) {
            Map<String, Term> s4 = q4.nextSolution();
            System.out.println("X = " + s4.get("X"));
        }

        String t5 = "szülő(X)";
        System.out.println("first solution of " + t5 + ": X = " + Query.oneSolution(t5).get("X"));

        String t6 = "testvér(matyi, X)";
        Map<String, Term>[] ss6 = Query.allSolutions(t6);
        System.out.println("all solutions of " + t6);
        for (int i = 0; i < ss6.length; i++) {
            System.out.println("X = " + ss6[i].get("X"));
        }
        
        String t7 = "gyereke(X, norbi)";
        System.out.println("each solution of " + t7);
        Query q7 = new Query(t7);
        while (q7.hasMoreSolutions()) {
            Map<String, Term> s7 = q7.nextSolution();
            System.out.println("X = " + s7.get("X"));
        }        
    }

}
