package sample;

/**
 * Created by sojer on 20.11.2017.
 */
public class Currency {
    private String currencyName;
    private int converter;
    private String code;
    private double averageRate;
    private double purchaseRate;
    private double sellRate;


    public Currency(String currencyName, int converter, String code, double rateOfExchange) {
        this.currencyName = currencyName;
        this.converter = converter;
        this.averageRate = rateOfExchange;
        this.code = code;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public int GetConverter() {
        return converter;
    }

    public void SetConverter(int converter) {
        this.converter = converter;
    }

    public String GetCode() {
        return code;
    }

    public double GetRateOfExchange() {
        return averageRate;
    }

    public double getPurchaseRate() {
        return purchaseRate;
    }

    public void setPurchaseRate(double purchaseRate) {
        this.purchaseRate = purchaseRate;
    }

    public double getSellRate() {
        return sellRate;
    }

    public void setSellRate(double sellRate) {
        this.sellRate = sellRate;
    }

    @Override
    public String toString() {
        StringBuilder toShow = new StringBuilder();
        toShow.append(code + " - " + currencyName + "\n");
        toShow.append("Kurs średni: " + averageRate);
        toShow.append("  Kurs kupna: ");
        if(purchaseRate != 0)
            toShow.append(purchaseRate);
        else
            toShow.append("nie podano");
        toShow.append("  Kurs sprzedaży: ");
        if(sellRate != 0)
            toShow.append(sellRate);
        else
            toShow.append("nie podano");

        return toShow.toString();
    }
}
