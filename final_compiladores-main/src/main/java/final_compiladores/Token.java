package final_compiladores;

public class Token {
    
    protected TokenType type;

    Token(TokenType type){
        this.type = type;
    }

    public TokenType getType(){
        return this.type;
    }

    public boolean isNumber(){
        return false;
    }
}
