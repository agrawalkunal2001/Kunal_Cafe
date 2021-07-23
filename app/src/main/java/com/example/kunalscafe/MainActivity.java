package com.example.kunalscafe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kunalscafe.R;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    private TextView quantity, orderSummary, topping;
    private TextView quantityValue, orderSummaryDetails;
    private Button button;
    private CheckBox whippedCream, chocolate;
    private EditText name;
    int num = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        quantity = findViewById(R.id.quantity);
        quantityValue = findViewById(R.id.quantityValue);
        name=findViewById(R.id.name);
        topping=findViewById(R.id.topping);
        whippedCream=findViewById(R.id.whippedCream);
        chocolate=findViewById(R.id.chocolate);
        button = findViewById(R.id.button);
        orderSummary = findViewById(R.id.orderSummary);
        orderSummaryDetails = findViewById(R.id.orderSummaryDetails);
    }

    public void submitOrder(View view) {
        int pricePerCup=5;
        String nameValue = name.getText().toString();
        String cream, choc;
        boolean isWhippedCream = whippedCream.isChecked();

        if(isWhippedCream==true){
            cream = "Yes";
            pricePerCup=pricePerCup+1;
        }
        else{
            cream = "No";
        }

        boolean isChocolate = chocolate.isChecked();

        if(isChocolate==true){
            choc = "Yes";
            pricePerCup=pricePerCup+2;
        }
        else{
            choc = "No";
        }

        int price = calculatePrice(num, pricePerCup);
        String message = orderSummary(price, cream, choc, nameValue);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Order for " + nameValue);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

        displayMessage(message);
    }

    public int calculatePrice(int quantity, int pricePerCup) {
        int finalPrice = quantity * pricePerCup;
        return finalPrice;
    }

    public String orderSummary(int price, String cream, String choc, String nameValue) {
        String message = "Name: " + nameValue;
        message = message + "\nQuantity: " + num;
        message = message + "\nWhipped Cream? " + cream;
        message = message + "\nChocolate? " + choc;
        message = message + "\nTotal: $" + price;
        message = message + "\nThank You";
        return message;
    }

    public void increment(View view) {
        if(num>99){
            Toast.makeText(this, "You cannot order more than 100 cups of coffee", Toast.LENGTH_LONG).show();
            return;
        }
        num = num + 1;
        display(num);
    }

    public void decrement(View view) {
        if(num<2){
            Toast.makeText(this, "You cannot order less than 1 cup of coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        num = num - 1;
        display(num);
    }

    private void display(int number) {

        quantityValue.setText("" + number);
    }

    private void displayMessage(String message) {
        orderSummaryDetails.setText(message);
    }
}