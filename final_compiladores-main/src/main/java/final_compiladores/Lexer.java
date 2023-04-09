package final_compiladores;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    
    private char[] input;
    private Token[] tokens;
    private int tokenIndex = 0;
    private char currentChar = '\0';
    private int currentPosition = 0;

    Lexer(String input){
        this.input = input.toCharArray();
    }

    public boolean analyze(){
        
        List<Token> tokens = new ArrayList<Token>();
        String number = "";
        boolean is_parsing_digit = false;
        boolean number_has_point = false;

        for(int i=0; i<this.input.length; i++){
            currentChar = input[i];
            currentPosition++;
            TokenType currentType = null;

            if(Character.isDigit(currentChar)){
                number += currentChar;
                is_parsing_digit = true;
                continue;
            }

            if(currentChar=='.' && is_parsing_digit){
                if(number_has_point){
                    System.out.println("El caracter '"+currentChar+"' en la posicion "+currentPosition+" no es valido");
                    System.out.println("Se esperaba un numero");
                    return false;
                }
                number += currentChar;
                is_parsing_digit = false;
                number_has_point = true;
                continue;
            }

            if(is_parsing_digit){
                NumericToken t = new NumericToken(TokenType.NUMERO, Double.parseDouble(number));
                tokens.add(t);
                is_parsing_digit = false;
                number = "";
            }

            if(number.length()>0 && !is_parsing_digit){
                System.out.println("El caracter '"+currentChar+"' en la posicion "+currentPosition+" no es valido");
                System.out.println("Se esperaba un numero");
                return false;
            }

            currentType = switch (currentChar) {
                case '(' -> TokenType.LPAREN;
                case ')' -> TokenType.RPAREN;
                case '>' -> TokenType.MAYOR;
                case '<' -> TokenType.MENOR;
                case '&' -> TokenType.AND;
                case '|' -> TokenType.OR;
                case '=' -> TokenType.IGUAL;
                case '!' -> TokenType.NEGACION;
                case '+' -> TokenType.MAS;
                case '-' -> TokenType.MENOS;
                case '*' -> TokenType.MULTIPLICA;
                case '/' -> TokenType.DIVIDE;
                case '^' -> TokenType.POTENCIA;
                case '¬' -> TokenType.EOL;
                default -> null;
            };

            if(currentType == null){
                System.out.println("El caracter '"+currentChar+"' en la posicion "+currentPosition+" no es valido");
                return false;
            }

            tokens.add(new Token(currentType));
        }

        if(is_parsing_digit == true){
            NumericToken t = new NumericToken(TokenType.NUMERO, Double.parseDouble(number));
            tokens.add(t);
        }
        this.tokens = tokens.toArray(new Token[tokens.size()]);
        return true;
    }

    public Token getToken(){
        if(tokenIndex >= tokens.length){
            return null;
        }
        return tokens[tokenIndex];
    }

    public void avance(){
        tokenIndex ++;
    }

    public char getErrorCharacter(){
        return currentChar;
    }

    public int getErrorPosition(){
        return currentPosition;
    }

}
