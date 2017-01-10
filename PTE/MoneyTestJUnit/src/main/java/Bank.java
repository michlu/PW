import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;
import java.util.Hashtable;

/**
 * @author Michlu
 * @sience 2017-01-10
 */
public class Bank {
    private HashMap<String, BigDecimal> rates = new HashMap();

    public Bank(){
        // Waluta wyjsciowa PLN
        rates.put("PLN", BigDecimal.valueOf(1));
        rates.put("EUR", BigDecimal.valueOf(4));
        rates.put("USD", BigDecimal.valueOf(4));
    }

    public Money exchange(Money exchange, String to){
        Money result;
        BigDecimal rate = rates.get(to);
        if(!exchange.getCurrency().equals("PLN")){
            BigDecimal changeToPln = exchange.getAmount().multiply(rates.get(exchange.getCurrency()));
            result = new Money(changeToPln.divide(rate), to);
        }
        else if(exchange.getCurrency().equals("PLN") && to.equals("PLN")){
            result = new Money(exchange.getAmount(), to);
        }
        else{
            result = new Money(exchange.getAmount().divide(rate), to);
        }
        return result;
    }
}
