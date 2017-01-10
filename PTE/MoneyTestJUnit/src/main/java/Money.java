import java.math.BigDecimal;

/**
 * @author Michlu
 * @sience 2017-01-10
 */
public class Money {
    private BigDecimal amount;
    private String currency;

    public Money(BigDecimal amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public static Money euro(int amount){
        return new Money(BigDecimal.valueOf(amount), "EUR");
    }
    public static Money zloty(int amount){
        return new Money(BigDecimal.valueOf(amount), "PLN");
    }
    public static Money dollar(int amount){
        return new Money(BigDecimal.valueOf(amount), "USD");
    }

    public Money add(Money m){
        Money result = new Money(getAmount().add(m.getAmount()), getCurrency());
        return result;
    }

    public Money add(Money m, Bank bank){
        Money result;
        if(getCurrency().equals(m.getCurrency())){
            result = new Money(getAmount().add(m.getAmount()), getCurrency());
        }
        else{
            BigDecimal exchange = bank.exchange(m , getCurrency()).getAmount();
            result = new Money(getAmount().add(exchange), getCurrency());
        }
        return result;
    }

    public Money subtract(Money m){
        Money result = new Money(getAmount().subtract(m.getAmount()), getCurrency());
        return result;
    }

    public Money subtract(Money m, Bank bank){
        Money result;
        if(getCurrency().equals(m.getCurrency())){
            result = new Money(getAmount().subtract(m.getAmount()), getCurrency());
        }
        else{
            BigDecimal exchange = bank.exchange(m , getCurrency()).getAmount();
            result = new Money(getAmount().subtract(exchange), getCurrency());
        }
        return result;
    }

    // jezeli pierwsza liczba jest wieksza od drugiej daje 1
    // jezeli pierwsza liczba jest mniejsza od drugiej daje 2
    // sa rowne daje 0
    public int compareMoney(Money money){
        int result;
        if(getAmount().compareTo(money.getAmount())>0) {
            result = 1;
        }
        else if(getAmount().compareTo(money.getAmount())<0){
            result = 2;
        }
        else{
            result = 0;
        }
        return result;
    }

    public int compareMoney(Money money, Bank bank){
        int result;
        BigDecimal exchangeThis = bank.exchange(this , "PLN").getAmount();
        BigDecimal exchangeMoney = bank.exchange(money , "PLN").getAmount();
        if(exchangeThis.compareTo(exchangeMoney)>0) {
            result = 1;
        }
        else if(exchangeThis.compareTo(exchangeMoney)<0){
            result = 2;
        }
        else{
            result = 0;
        }
        return result;
    }

    public boolean equals(Object anObject) {
        if (anObject instanceof Money) {
            Money a = (Money) anObject;

            return a.getCurrency().equals(getCurrency()) && getAmount().equals(a.getAmount());
        }
        return false;
    }

    public Money times(int multiplier) {
        Money result = new Money(getAmount().multiply(BigDecimal.valueOf(multiplier)), getCurrency());
        return result;
    }

    @Override
    public String toString() {
        return "Money{" +
                "amount=" + amount +
                ", currency='" + currency + '\'' +
                '}';
    }
}
