import org.mockito.Mockito;
import org.mockito.Mockito.*;
import org.junit.Test;
import sample.Calculations;
import sample.Currency;
import sample.RateType;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

public class CalculationTest {


    @Test
    public void calculateTest()
    {
        Currency currencyFrom = Mockito.mock(Currency.class);
        Currency currencyTo = Mockito.mock(Currency.class);

        when(currencyFrom.getConverter()).thenReturn(1);
        when(currencyTo.getConverter()).thenReturn(1);
        when(currencyFrom.getRate(RateType.AVERAGE_RATE)).thenReturn(1.0);
        when(currencyTo.getRate(RateType.AVERAGE_RATE)).thenReturn(1.0);

        Calculations calculations = new Calculations();
        assertThat(calculations.calculateTransaction(currencyFrom,currencyTo,1,RateType.AVERAGE_RATE),is(1.0));

    }

}
