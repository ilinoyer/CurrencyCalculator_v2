package sample;

public class Calculations {
    CurrencyCollection collection;

    Calculations(CurrencyCollection collection)
    {
        this.collection = collection;
    }

    double calculateTransaction(String currencyFrom, String currencyTo, double amount, RateType rateType)
    {
        double result = 0.0;

        Currency currFrom = collection.getCurrencyElementByCode(currencyFrom);
        Currency currTo = collection.getCurrencyElementByCode(currencyTo);

        result = (amount * currFrom.getRate(rateType) * currFrom.getConverter() * currTo.getConverter()) / (currTo.getRate(rateType));
        result *= 100;
        result = Math.round(result);
        result /= 100;

        return result;
    }
}
