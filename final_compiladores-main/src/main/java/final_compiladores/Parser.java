package final_compiladores;

import java.util.Stack;

public class Parser {
    
    private Stack<String> stack = new Stack<String>();
    private Token t;
    private Lexer lex;

    Parser(Lexer lex){
        this.lex = lex;
    }

    public boolean parse(){
        double a;
        double b;
        boolean c;
        boolean d;
        NumericToken nc;
        stack.push("Z0");
        stack.push("S");

        while ((t=lex.getToken()) != null){
            System.out.print(stack);
            System.out.println(" "+t.getType());
            switch (stack.lastElement()) {
                case "S":
                    switch (t.getType()) {
                        case NUMERO:
                            stack.pop();
                            stack.push("respuesta");
                            stack.push("ELO");
                            break;
                        case LPAREN:
                            stack.pop();
                            //stack.push(respuesta);
                            stack.push("ELO");
                            break;
                        default:
                            rechace();
                            return false;
                    }
                    break;
                case "ELO":
                    switch (t.getType()) {
                        case NUMERO:
                            stack.pop();
                            stack.push("ELO_L");
                            stack.push("EL2");
                            break;
                        case LPAREN:
                            stack.pop();
                            stack.push("ELO_L");
                            stack.push("EL2");
                            break;
                        default:
                            rechace();
                            return false;
                    }
                    break;
                case "ELO_L":
                    switch (t.getType()) {
                        case OR:
                            stack.pop();
                            stack.push("ELO_L");
                            stack.push("|");
                            stack.push("EL2");
                            lex.avance();
                            break;
                        case EOL:
                            stack.pop();
                            swap();
                            break;
                        case RPAREN:
                            stack.pop();
                            swap();
                            break;
                        default:
                            rechace();
                            return false;
                    }
                    break;
                case "EL2":
                    switch (t.getType()) {
                        case NUMERO:
                            stack.pop();
                            stack.push("EL2_L");
                            stack.push("ER");
                            break;
                        case LPAREN:
                            stack.pop();
                            stack.push("EL2_L");
                            stack.push("ER");
                            break;
                        default:
                            rechace();
                            return false;
                    }
                    break;
                case "EL2_L":
                    switch (t.getType()) {
                        case AND:
                            stack.pop();
                            stack.push("EL2_L");
                            stack.push("&");
                            swap();
                            stack.push("ER");
                            lex.avance();
                            break;
                        case EOL:
                            stack.pop();
                            swap();
                            break;
                        case RPAREN:
                            stack.pop();
                            swap();
                            break;
                        case OR:
                            stack.pop();
                            swap();
                            break;
                        default:
                            rechace();
                            return false;
                    }
                    break;
                case "ER":
                    switch (t.getType()) {
                        case NUMERO:
                            stack.pop();
                            stack.push("ER_L");
                            stack.push("E");
                            break;
                        case LPAREN:
                            stack.pop();
                            stack.push("ER_L");
                            stack.push("E");
                            break;
                        default:
                            rechace();
                            return false;
                    }
                    break;
                case "ER_L":
                    switch (t.getType()) {
                        case MAYOR:
                            stack.pop();
                            stack.push(">");
                            stack.push("E");
                            stack.push("OR");
                            break;
                        case MENOR:
                            stack.pop();
                            stack.push("<");
                            stack.push("E");
                            stack.push("OR");
                            break;
                        case IGUAL:
                            stack.pop();
                            //stack.push(pComparar());
                            stack.push("E");
                            stack.push("OR");
                            break;
                        case NEGACION:
                            stack.pop();
                            //stack.push(pComparar());
                            stack.push("E");
                            stack.push("OR");
                            break;
                        case EOL:
                            stack.pop();
                            swap();
                            break;
                        case RPAREN:
                            stack.pop();
                            swap();
                            break;
                        case AND:
                            stack.pop();
                            break;
                        case OR:
                            stack.pop();
                            break;
                        default:
                            rechace();
                            return false;
                    }
                    break;
                case "OR":
                    switch (t.getType()) {
                        case MAYOR:
                            stack.pop();
                            stack.push("MA");
                            lex.avance();
                            break;
                        case MENOR:
                            stack.pop();
                            stack.push("ME");
                            lex.avance();
                            break;
                        case IGUAL:
                            stack.pop();
                            stack.push("IG");
                            lex.avance();
                            break;
                        case NEGACION:
                            stack.pop();
                            stack.push("DI");
                            lex.avance();
                            break;
                        default:
                            rechace();
                            return false;
                    }
                    break;
                case "ME":
                    switch (t.getType()) {
                        case IGUAL:
                            stack.pop();
                            stack.pop();
                            stack.pop();
                            stack.push("<=");
                            stack.push("E");
                            lex.avance();
                            break;
                        case NUMERO:
                            stack.pop();
                            break;
                        case LPAREN:
                            stack.pop();
                            break;
                        default:
                            rechace();
                            return false;
                    }
                    break;
                case "MA":
                    switch (t.getType()) {
                        case IGUAL:
                            stack.pop();
                            stack.pop();
                            stack.pop();
                            stack.push(">=");
                            stack.push("E");
                            lex.avance();
                            break;
                        case NUMERO:
                            stack.pop();
                            break;
                        case LPAREN:
                            stack.pop();
                            break;
                        default:
                            rechace();
                            return false;
                    }
                    break;
                case "IG":
                    switch (t.getType()) {
                        case IGUAL:
                            stack.pop();
                            stack.pop();
                            stack.push("==");
                            stack.push("E");
                            lex.avance();
                            break;
                        default:
                            rechace();
                            return false;
                    }
                    break;
                case "DI":
                    switch (t.getType()) {
                        case IGUAL:
                            stack.pop();
                            stack.pop();
                            stack.push("!=");
                            stack.push("E");
                            lex.avance();
                            break;
                        default:
                            rechace();
                            return false;
                    }
                    break;
                case "E":
                    switch (t.getType()) {
                        case NUMERO:
                            stack.pop();
                            stack.push("E_L");
                            stack.push("T");
                            break;
                        case LPAREN:
                            stack.pop();
                            stack.push("E_L");
                            stack.push("T");
                            break;
                        default:
                            rechace();
                            return false;
                    }
                    break;
                case "E_L":
                    switch (t.getType()) {
                        case MAS:
                            stack.push("suma");
                            swap();
                            stack.push("T");
                            lex.avance();
                            break;
                        case MENOS:
                            stack.push("resta");
                            swap();
                            stack.push("T");
                            lex.avance();
                            break;
                        case EOL:
                            stack.pop();
                            swap();
                            break;
                        case AND:
                            stack.pop();
                            swap();
                            break;
                        case OR:
                            stack.pop();
                            swap();
                            break;
                        case MAYOR:
                            stack.pop();
                            swap();
                            break;
                        case MENOR:
                            stack.pop();
                            swap();
                            break;
                        case IGUAL:
                            stack.pop();
                            swap();
                            break;
                        case NEGACION:
                            stack.pop();
                            swap();
                            break;
                        case RPAREN:
                            stack.pop();
                            swap();
                            break;
                        default:
                            rechace();
                            return false;
                    }
                    break;
                case "T":
                    switch (t.getType()) {
                        case NUMERO:
                            stack.pop();
                            stack.push("T_L");
                            stack.push("P");
                            break;
                        case LPAREN:
                            stack.pop();
                            stack.push("T_L");
                            stack.push("P");
                            break;
                        default:
                            rechace();
                            return false;
                    }
                    break;
                case "T_L":
                    switch (t.getType()) {
                        case MULTIPLICA:
                            stack.push("multiplica");
                            swap();
                            stack.push("P");
                            lex.avance();
                            break;
                        case DIVIDE:
                            stack.push("divide");
                            swap();
                            stack.push("P");
                            lex.avance();
                            break;
                        case MAS:
                            stack.pop();
                            swap();
                            break;
                        case MENOS:
                            stack.pop();
                            swap();
                            break;
                        case EOL:
                            stack.pop();
                            swap();
                            break;
                        case AND:
                            stack.pop();
                            swap();
                            break;
                        case OR:
                            stack.pop();
                            swap();
                            break;
                        case MAYOR:
                            stack.pop();
                            swap();
                            break;
                        case MENOR:
                            stack.pop();
                            swap();
                            break;
                        case IGUAL:
                            stack.pop();
                            swap();
                            break;
                        case NEGACION:
                            stack.pop();
                            swap();
                            break;
                        case RPAREN:
                            stack.pop();
                            swap();
                            break;
                        default:
                            rechace();
                            return false;
                    }
                    break;
                case "P":
                    switch (t.getType()) {
                        case NUMERO:
                            stack.pop();
                            stack.push("P_L");
                            stack.push("F");
                            break;
                        case LPAREN:
                            stack.pop();
                            stack.push("P_L");
                            stack.push("F");
                            break;
                        default:
                            rechace();
                            return false;
                    }
                    break;
                case "P_L":
                    switch (t.getType()) {
                        case POTENCIA:
                            stack.push("exp");
                            swap();
                            stack.push("F");
                            lex.avance();
                            break;
                        case MULTIPLICA:
                            stack.pop();
                            swap();
                            break;
                        case DIVIDE:
                            stack.pop();
                            swap();
                            break;
                        case MAS:
                            stack.pop();
                            swap();
                            break;
                        case MENOS:
                            stack.pop();
                            swap();
                            break;
                        case EOL:
                            stack.pop();
                            swap();
                            break;
                        case AND:
                            stack.pop();
                            swap();
                            break;
                        case OR:
                            stack.pop();
                            swap();
                            break;
                        case MAYOR:
                            stack.pop();
                            swap();
                            break;
                        case MENOR:
                            stack.pop();
                            swap();
                            break;
                        case IGUAL:
                            stack.pop();
                            swap();
                            break;
                        case NEGACION:
                            stack.pop();
                            swap();
                            break;
                        case RPAREN:
                            stack.pop();
                            swap();
                            break;
                        default:
                            rechace();
                            return false;
                    }
                    break;
                case "F":
                    switch (t.getType()) {
                        case LPAREN:
                            stack.pop();
                            stack.push("(");
                            // swap();
                            stack.push("ELO");
                            lex.avance();
                            break;
                        case NUMERO:
                            stack.pop();
                            nc = (NumericToken)t;
                            stack.push(nc.getValue()+"");
                            swap();
                            lex.avance();
                            break;
                        default:
                            rechace();
                            return false;
                    }
                    break;
                case "(":
                    switch (t.getType()) {
                        case RPAREN:
                            stack.pop();
                            swap();
                            lex.avance();
                            break;
                        default:
                            rechace();
                            return false;
                    }
                    break;
                case "suma":
                    stack.pop();
                    b = Double.parseDouble(stack.pop());
                    a = Double.parseDouble(stack.pop());
                    stack.push(suma(a, b)+"");
                    swap();
                    break;
                case "resta":
                    stack.pop();
                    b = Double.parseDouble(stack.pop());
                    a = Double.parseDouble(stack.pop());
                    stack.push(resta(a, b)+"");
                    swap();
                    break;
                case "multiplica":
                    stack.pop();
                    b = Double.parseDouble(stack.pop());
                    a = Double.parseDouble(stack.pop());
                    stack.push(mul(a, b)+"");
                    swap();
                    break;
                case "divide":
                    stack.pop();
                    b = Double.parseDouble(stack.pop());
                    a = Double.parseDouble(stack.pop());
                    stack.push(div(a, b)+"");
                    swap();
                    break;
                case "exp":
                    stack.pop();
                    b = Double.parseDouble(stack.pop());
                    a = Double.parseDouble(stack.pop());
                    stack.push(exp(a, b)+"");
                    swap();
                    break;
                case "|":
                    stack.pop();
                    d = Boolean.parseBoolean(stack.pop());
                    stack.pop();
                    c = Boolean.parseBoolean(stack.pop());
                    stack.push("ELO_L");
                    stack.push(String.valueOf(procOR(c, d)));
                    swap();
                    break;
                case "&":
                    stack.pop();
                    d = Boolean.parseBoolean(stack.pop());
                    c = Boolean.parseBoolean(stack.pop());
                    stack.push(String.valueOf(procAND(c, d)));
                    swap();
                    break;
                case ">":
                    stack.pop();
                    b = Double.parseDouble(stack.pop());
                    a = Double.parseDouble(stack.pop());
                    stack.push(String.valueOf(pComparar(a, b, ">")));
                    swap();
                    break;
                case "<":
                    stack.pop();
                    b = Double.parseDouble(stack.pop());
                    a = Double.parseDouble(stack.pop());
                    stack.push(String.valueOf(pComparar(a, b, "<")));
                    swap();
                    break;
                case ">=":
                    stack.pop();
                    b = Double.parseDouble(stack.pop());
                    a = Double.parseDouble(stack.pop());
                    stack.push(String.valueOf(pComparar(a, b, ">=")));
                    swap();
                    break;
                case "<=":
                    stack.pop();
                    b = Double.parseDouble(stack.pop());
                    a = Double.parseDouble(stack.pop());
                    stack.push(String.valueOf(pComparar(a, b, "<=")));
                    swap();
                    break;
                case "==":
                    stack.pop();
                    b = Double.parseDouble(stack.pop());
                    a = Double.parseDouble(stack.pop());
                    stack.push(String.valueOf(pComparar(a, b, "==")));
                    swap();
                    break;
                case "!=":
                    stack.pop();
                    b = Double.parseDouble(stack.pop());
                    a = Double.parseDouble(stack.pop());
                    stack.push(String.valueOf(pComparar(a, b, "!=")));
                    swap();
                    break;
                case "respuesta":
                    stack.pop();
                    Resultado(stack.pop());
                    break;
                case "Z0":
                    switch (t.getType()) {
                        case EOL:
                            return true;
                        default:
                            rechace();
                    }
                default:
                    rechace();
            }
        }
        rechace();
        return false;
    }

    private void Resultado(String res){
        System.out.println("RESULTADO: "+res);
    }

    private boolean procOR(boolean a, boolean b){
        return a||b;
    }

    private boolean procAND(boolean a, boolean b){
        return a&&b;
    }

    private boolean pComparar(double a, double b, String operacion){
        
        switch (operacion){
            case ">":
                return a>b;
            case "<":
                return a<b;
            case ">=":
                return a>=b;
            case "<=":
                return a<=b;
            case "==":
                return a==b;
            case "!=":
                return a!=b;
            default:
                rechace();
                break;
        }
        return false;
    }

    private double suma(double a, double b){
        return a+b;
    }

    private double resta(double a, double b){
        return a-b;
    }

    private double mul(double a, double b){
        return a*b;
    }

    private double div(double a, double b){
        return a/b;
    }

    private double exp(double a, double b){
        double result = 1;
        for(int i=0;i<b;i++){
            result *= a;
        }
        return result;
    }

    private void rechace(){
        System.out.print(stack);
        System.out.println(" "+t.getType());
        System.out.println("Cadena no valida");
        System.exit(1);
    }

    private void swap(){
        String a = stack.pop();
        String b = stack.pop();
        stack.push(a);
        stack.push(b);
    }

}