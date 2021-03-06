package ca.alibahrani.shoppingcart;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by aliba on 5/22/2017.
 */

public class Cart {

    Map<Product, Integer> m_cart;
    double m_value = 0;

    Cart(){
        m_cart = new LinkedHashMap<>();
    }

    void addToCart(Product product) {
        if(m_cart.containsKey(product))
            m_cart.put(product,m_cart.get(product) +1);
        else
            m_cart.put(product,1);

        m_value += product.getM_price();
    }

    int getQuantity(Product product) {
        return  m_cart.get(product);
    }

    Set getProducts(){
        return m_cart.keySet();

    }

    void empty(){
        m_cart.clear();
        m_value = 0;
    }

    double getM_value(){
        return m_value;
    }
}
