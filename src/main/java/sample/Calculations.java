package sample;

public class Calculations {

    public Calculations(){}

    public double calculateTransaction(Currency currFrom,Currency currTo, double amount, RateType rateType)
    {
        double result = 0.0;

        result = (amount * currFrom.getRate(rateType) * currFrom.getConverter() * currTo.getConverter()) / (currTo.getRate(rateType));
        result *= 100;
        result = Math.round(result);
        result /= 100;

        return result;
    }
}
