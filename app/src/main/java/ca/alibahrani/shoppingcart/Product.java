package ca.alibahrani.shoppingcart;

/**
 * Created by aliba on 5/22/2017.
 */

public class Product {

    String m_name;
    double m_price;

    Product(String name, double value){
        m_name = name;
        m_price = value;

    }


    public String getM_name() {
        return m_name;
    }

    public double getM_price() {
        return m_price;
    }
}
