import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.Mockito.*;

import sample.Calculations;
import sample.Currency;
import sample.RateType;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

public class CalculationTest {


    @Test
    public void calculationTestWithOneValue()
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

    @Test
    public void calculationTestWithDifferentValues()
    {
        Currency currencyFrom = Mockito.mock(Currency.class);
        Currency currencyTo = Mockito.mock(Currency.class);

        when(currencyFrom.getConverter()).thenReturn(1);
        when(currencyTo.getConverter()).thenReturn(1);
        when(currencyFrom.getRate(RateType.AVERAGE_RATE)).thenReturn(2.0);
        when(currencyTo.getRate(RateType.AVERAGE_RATE)).thenReturn(1.0);

        Calculations calculations = new Calculations();
        assertThat(calculations.calculateTransaction(currencyFrom,currencyTo,1,RateType.AVERAGE_RATE),is(2.0));
    }

    @Test
    public void calculationsTestWithRealValues()
    {
        Currency currencyFrom = Mockito.mock(Currency.class);
        Currency currencyTo = Mockito.mock(Currency.class);

        when(currencyFrom.getConverter()).thenReturn(1);
        when(currencyTo.getConverter()).thenReturn(1);
        when(currencyFrom.getRate(RateType.AVERAGE_RATE)).thenReturn(0.1075);
        when(currencyTo.getRate(RateType.AVERAGE_RATE)).thenReturn(3.4366);

        Calculations calculations = new Calculations();
        assertThat(calculations.calculateTransaction(currencyFrom,currencyTo,100,RateType.AVERAGE_RATE),is(3.13));
    }

}
