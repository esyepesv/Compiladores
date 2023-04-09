/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package final_compiladores;

/**
 *
 * @author jmvalenciz
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String input = "2*(5+3)¬";
        Lexer lex = new Lexer(input);
        Parser p;

        boolean analyze_result = lex.analyze();

        if(!analyze_result){
            System.exit(1);
        }

        p = new Parser(lex);

        System.out.println(p.parse());
    }
    
}