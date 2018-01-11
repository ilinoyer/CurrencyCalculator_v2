package sample;

import java.util.ArrayList;

/**
 * Created by sojer on 20.11.2017.
 */
public class CurrencyCollection {
    private ArrayList<Currency> currencyList;

    public CurrencyCollection()
    {
        this.currencyList = new ArrayList<Currency>();
    }

    public void addElementToCollection(Currency newCurrency)
    {
        this.currencyList.add(newCurrency);
    }

    public int getCollectionSize()
    {
        return this.currencyList.size();
    }

    public Currency getCollectionElementByPosition(int position)
    {
        return currencyList.get(position);
    }

    public Currency getCurrencyElementByCode(String code)
    {
        Currency result =  null;
        for(int i = 0 ; i < currencyList.size(); ++i)
        {
            if( currencyList.get(i).getCode().equals(code))
            {
                result  = currencyList.get(i);
                break;
            }
        }

        return result;
    }


    public void modifyElementOfCollection(String code, double purchaseRate, double sellRate)
    {
        for(int i = 0; i < currencyList.size(); ++i)
        {
            if(currencyList.get(i).getCode().equals(code))
            {
                currencyList.get(i).setPurchaseRate(purchaseRate);
                currencyList.get(i).setSellRate(sellRate);
                break;
            }
        }
    }

    public ArrayList<Currency> getCurrencyList() {
        return currencyList;
    }
}
