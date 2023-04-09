package final_compiladores;

public class NumericToken extends Token{

    private double value;

    NumericToken(TokenType type, double value){
        super(type);
        this.value = value;
    }

    public double getValue(){
        return this.value;
    }

    @Override
    public boolean isNumber(){
        return true;
    }
    
}
