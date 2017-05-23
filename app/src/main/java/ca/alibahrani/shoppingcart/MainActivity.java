package ca.alibahrani.shoppingcart;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import java.math.BigDecimal;

import static android.R.attr.start;

public class MainActivity extends AppCompatActivity {


    TextView m_response;
    PayPalConfiguration m_configration;
    //the id is the link to the paypal account , we have t ocreate an app and get its id
    String m_paypalClientId = "Af-y20Arjr59cOFwS5SenRFRlSaENuxr946040a75wZ7Z6o4Qom3mnH6sCJAsPcsAjzVDsNxxS435iM_";
    Intent m_service;
    int m_paypalRequestCode = 999;
    static Cart m_cart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        LinearLayout list = (LinearLayout) findViewById(R.id.list);
        m_response = (TextView) findViewById(R.id.response);

        m_configration = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX) //Sandbox for test, production for real
                .clientId(m_paypalClientId);


        m_service = new Intent(this, PayPalService.class);
        m_service.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, m_configration); //configration above
        startService(m_service);// Paypal service, listening to calls to paypal app

        m_cart = new Cart();
        Product products[] = {
                new Product("Product 1", 15.20),
                new Product("Product 2", 19.30),
                new Product("Product 3", 8.20),
                new Product("Product 4", 23.24),
                new Product("Product 5", 55.20),
                new Product("Product 6", 15.42),
                new Product("Product 7", 99.20),
        };
        for (int i = 0; i <products.length; i++){
            Button button = new Button(this);
            button.setText(products[i].getM_name() + " --- " + products[i].getM_price() + " $");
            button.setTag(products[i]);

            //Display
            button.setTextSize(25);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,200, Gravity.CENTER);
            layoutParams.setMargins(20,50,20,50);
            button.setLayoutParams(layoutParams);

            button.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    Button button = (Button) v;
                    Product product = (Product) button.getTag();
                    m_cart.addToCart(product);
                    m_response.setText("Total cart Value = " +String.format("%.2f",m_cart.getM_value())+ " $");
                }
            });
            list.addView(button);
        }
    }


    void pay(View view) {
        PayPalPayment cart = new PayPalPayment(new BigDecimal(m_cart.getM_value()), "CAD","Cart",PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PaymentActivity.class); // Its not paypalPayment, its Paymentactivity
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,m_configration);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, cart);
        startActivityForResult(intent, m_paypalRequestCode);
    }
    void reset(View view) {
        m_response.setText("");
        m_cart.empty();
    }

    void viewCart(View v){
        Intent intent = new Intent(this, ViewCart.class);
        m_cart = this.m_cart;
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == m_paypalRequestCode){
            if(resultCode == Activity.RESULT_OK)
            {
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null) {
                    String state = confirmation.getProofOfPayment().getState();
                    if (state.equals("approved")) //if the payment worked , the state equals approved
                        m_response.setText("Payment approved");
                    else
                        m_response.setText("error in the payment");
                }
                else
                    m_response.setText("Confirmation is null ");
            }
        }
    }
}
