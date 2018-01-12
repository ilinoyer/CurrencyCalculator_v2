import org.junit.Test;
import sample.Currency;
import sample.CurrencyCollection;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * Created by sojer on 13.10.2017.
 */
public class CurrencyCollectionTest {


    @Test
    public void creatingObjectTest()
    {
        CurrencyCollection collection = new CurrencyCollection();
        assertThat(collection.getCurrencyList(),not(nullValue()));
    }

    @Test
    public void gettingSizeOfCollectionTest()
    {
        CurrencyCollection collection  = new CurrencyCollection();
        collection.addElementToCollection(new Currency());
        collection.addElementToCollection(new Currency());
        assertThat(2, is(collection.getCollectionSize()));
    }

    @Test
    public void addingElementToCollectionTest()
    {
        CurrencyCollection collection = new CurrencyCollection();
        collection.addElementToCollection(new Currency("Złoty", 1, "PLN", 0.50));
        assertThat(1, is(collection.getCollectionSize()));
    }

    @Test
    public void removingElementFromCollectionTest()
    {
        Currency currency = new Currency();
        CurrencyCollection collection = new CurrencyCollection();
        collection.addElementToCollection(currency);
        collection.removeElementFromCollection(currency);
        assertThat(collection.getCollectionSize(), is(0));
    }

    @Test
    public void gettingElementByCodeTest()
    {
        CurrencyCollection collection = new CurrencyCollection();
        collection.addElementToCollection(new Currency("Złoty", 1, "PLN", 0.50));
        assertThat("PLN", is(collection.getCurrencyElementByCode("PLN").getCode()));
    }

    @Test
    public void gettingElementByPositionTest()
    {
        CurrencyCollection collection = new CurrencyCollection();
        Currency currency = new Currency("Złoty", 1, "PLN", 0.50);
        collection.addElementToCollection(currency);
        assertThat(currency, is(collection.getCollectionElementByPosition(0)));
    }
}