import org.junit.Before;
import org.junit.Test;
import sample.Currency;
import sample.RateType;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class CurrencyTest {

    Currency currency;

    @Before
    public void init()
    {
        currency = new Currency("PLN", 1,"PLN", 1,2,3);
    }

    @Test
    public void getAverageTest()
    {
        assertThat(1.0, is(currency.getRate(RateType.AVERAGE_RATE)));
    }

    @Test
    public void getSellRateTest()
    {
        assertThat(3.0,is(currency.getRate(RateType.SELL_RATE)));
    }

    @Test
    public void getPurchaseRate()
    {
        assertThat(2.0,is(currency.getRate(RateType.PURCHASE_RATE)));
    }
}
