/**
 * @author Michlu
 * @sience 2017-01-10
 */
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class MoneyTest {
    Money m12PLN;
    Money m14PLN;
    Money m5EUR;
    Money m15EUR;
    Bank bank;

    @Before
    public void setUp(){
        bank = new Bank();
        m12PLN = Money.zloty(12);
        m14PLN = Money.zloty(14);
        m5EUR = Money.euro(5);
        m15EUR = Money.euro(15);
    }

    @Test
    public void testGetAmount() throws Exception {
        assertTrue(BigDecimal.valueOf(12).equals(m12PLN.getAmount()));
    }

    @Test
    public void testGetCurrency() throws Exception {
        assertTrue("PLN".equals(m12PLN.getCurrency()));
    }

    @Test
    public void testSimpleAdd() {
        //given
        Money expected = Money.zloty(26);
        //when
        Money result = m12PLN.add(m14PLN);
        //then
        assertTrue(expected.equals(result));
    }
    @Test
    public void testTimes(){
        assertEquals(Money.zloty(24), m12PLN.times(2));
        assertEquals(Money.zloty(42), m14PLN.times(3));
    }
    @Test
    public void testEquals() {
        assertFalse(m12PLN.equals(null));
        assertEquals(m12PLN, m12PLN);
        assertEquals(m12PLN, Money.zloty(12));
        assertFalse(m12PLN.equals(m14PLN));
    }
    @Test
    public void testExchangeMoney(){
        Money m10EUR = bank.exchange(Money.euro(10), "USD");
        Money m15PLN = bank.exchange(Money.zloty(15), "PLN");
        Money m40PLN = bank.exchange(Money.zloty(40), "EUR");

        assertEquals(Money.dollar(10), m10EUR);
        assertEquals(Money.zloty(15), m15PLN);
        assertEquals(Money.euro(10), m40PLN);
    }

    @Test
    public void testAdditionDifferentCurrency(){

        assertEquals(Money.euro(20), Money.euro(10).add(Money.zloty(40), bank));
        assertEquals(Money.dollar(20), Money.dollar(10).add(Money.euro(10), bank));
        assertEquals(Money.zloty(20), Money.zloty(10).add(Money.zloty(10), bank));
    }

    @Test
    public void testSimpreSubstract(){
        Money expected = Money.zloty(2);
        Money result = m14PLN.subtract(m12PLN);
        assertTrue(expected.equals(result));
    }

    @Test
    public void testSubstracDifferentyCurrency(){
        assertEquals(Money.euro(30), Money.euro(40).subtract(Money.zloty(40), bank));
        assertEquals(Money.dollar(20), Money.dollar(30).subtract(Money.euro(10), bank));
        assertEquals(Money.dollar(20), Money.dollar(40).subtract(Money.dollar(20), bank));
    }

    @Test
    public void testSimpleCompareMoney(){
        int one = 1; //pierwsza suma walut jest wieksza od drugiej
        int two = 2; //pierwsza suma walut jest mniejsza od drugiej
        int zero = 0; //sa rowne

        assertEquals(one, Money.euro(20).compareMoney(Money.euro(10)));
        assertEquals(two, Money.dollar(5).compareMoney(Money.dollar(30)));
        assertEquals(zero, Money.zloty(50).compareMoney(Money.zloty(50)));
    }

    @Test
    public void testCompareMoney(){
        int one = 1; //pierwsza suma walut jest wieksza od drugiej
        int two = 2; //pierwsza suma walut jest mniejsza od drugiej
        int zero = 0; //sa rowne
        assertEquals(one, Money.euro(10).compareMoney(Money.zloty(10), bank));
        assertEquals(two, Money.euro(5).compareMoney(Money.dollar(30), bank));
        assertEquals(zero, Money.zloty(40).compareMoney(Money.dollar(10), bank));
    }

    @Test
    public void testToString(){
        String expected = "Money{amount=10, currency='PLN'}";
        assertEquals(expected, Money.zloty(10).toString());
    }
}
