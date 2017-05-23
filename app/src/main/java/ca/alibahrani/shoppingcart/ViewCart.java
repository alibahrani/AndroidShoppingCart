package ca.alibahrani.shoppingcart;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.Iterator;
import java.util.Set;

public class ViewCart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);

        Cart cart = MainActivity.m_cart;
        LinearLayout cartLayout = (LinearLayout)findViewById(R.id.cart);


        Set<Product> products = cart.getProducts();
        Iterator iterator = products.iterator();
        while (iterator.hasNext()){
            Product product = (Product) iterator.next();
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            TextView name = new TextView(this);
            TextView quantity = new TextView(this);

            name.setText(product.getM_name());
            quantity.setText(Integer.toString(cart.getQuantity(product)));

            //Display both TextView Name,quantity of the products
            name.setTextSize(25);
            quantity.setTextSize(25);

            linearLayout.addView(name);
            linearLayout.addView(quantity);

            LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200, Gravity.CENTER);
            layoutParams.setMargins(20,50,20,50);
            linearLayout.setLayoutParams(layoutParams);

            name.setLayoutParams(new TableLayout.LayoutParams(0, ActionBar.LayoutParams.WRAP_CONTENT,1));
            quantity.setLayoutParams(new TableLayout.LayoutParams(0, ActionBar.LayoutParams.WRAP_CONTENT,1));

            name.setGravity(Gravity.CENTER);
            quantity.setGravity(Gravity.CENTER);

            cartLayout.addView(linearLayout);


        }
    }
}
