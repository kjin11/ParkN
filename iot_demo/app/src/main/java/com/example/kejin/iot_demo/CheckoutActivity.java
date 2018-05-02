package com.example.kejin.iot_demo;

/***
 * This is main payment activity for request payment and UI generation
 * Integrated by WenTing Lee 03/29/2018
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.kejin.iot_demo.data_class.DataRecord;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentMethodToken;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.TransactionInfo;
import com.google.firebase.database.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class CheckoutActivity extends AppCompatActivity {
    // Arbitrarily-picked result code.
    private static final int LOAD_PAYMENT_DATA_REQUEST_CODE = 991;

    private PaymentsClient mPaymentsClient;

    private View mGooglePayButton;
    private TextView mGooglePayStatusText;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    ///private DatabaseReference myRef = database.getReference().getRoot();
    private DatabaseReference myRef = database.getReference();
    private ItemInfo mPlaceHolderItem = new ItemInfo("Your Order", 300 * 1000000, R.drawable.placeholder);
    private long mShippingCost = 90 * 1000000;
    private Toolbar toolbar_detail;

    private TextView start_time;
    private TextView end_time;
    private TextView location;
    private TextView amenity;
    private TextView total_money;
    private TextView owner;
    private TextView distance;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        toolbar_detail = (Toolbar) this.findViewById(R.id.toolbar_detail1);
        // Set up the mock information for our item in the UI.
        initItemUI();

        Bundle bundle = getIntent().getExtras();
        //String location = bundle.get("location").toString();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Log.e ("myApplication", bundle.get("location") + " is a key in the bundle");
        location = (TextView) this.findViewById(R.id.location_detail);
        location.setText(bundle.get("location").toString());
        final String new_location = bundle.get("location").toString();
        start_time = (TextView) this.findViewById(R.id.start_date_txt_detail);
        start_time.setText(bundle.get("start_time").toString());
        final String new_start_time = bundle.get("start_time").toString();
        end_time = (TextView) this.findViewById(R.id.end_date_txt_detail);
        end_time.setText(bundle.get("end_time").toString());
        final String new_end_time   = bundle.get("end_time").toString();
        amenity = (TextView) this.findViewById(R.id.amenity_txt_detail);
        amenity.setText(bundle.get("amenity").toString());
        final String new_amenity = bundle.get("amenity").toString();
        //total_money = (TextView) this.findViewById(R.id.total_money_txt);
        //total_money.setText("$ " + bundle.get("total_money").toString());
        final String new_total_money =  bundle.get("total_money").toString();
        owner = (TextView) this.findViewById(R.id.owner);
        owner.setText(bundle.get("owner").toString());
        final String new_owner = bundle.get("owner").toString();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        String email = currentUser.getEmail().toString();
        email = email.split("\\.")[0];
        System.out.println(new_owner);
        System.out.println(new_owner);
        Log.e("a",new_owner);
        Log.e("a",new_owner);
        Log.e("a",new_owner);
        distance = (TextView) this.findViewById(R.id.distance);
        distance.setText(bundle.get("distance").toString() + " Miles");
        final String new_distance = bundle.get("distance").toString();
        final int new_price    = Integer.parseInt(bundle.get("price").toString());

        mGooglePayButton = findViewById(R.id.googlepay_button);
        mGooglePayStatusText = findViewById(R.id.googlepay_status);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        DataRecord new_record = new DataRecord(new_location,5,new_amenity,"",new_start_time,new_end_time,new_price,new_price*3, new_owner);
        mDatabase.child("Users").child(email).child("Records").child("Renting").push().setValue(new_record);
        mDatabase.child("Users").child(email).child("Records").child("Renting_history").push().setValue(new_record);
        mGooglePayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Bundle match = getIntent().getExtras();
                        DataSnapshot allAvailableLotSnapshot = dataSnapshot.child("Available_Lot");
                        Iterable<DataSnapshot> availableLotSnapshots = allAvailableLotSnapshot.getChildren();
                        for (DataSnapshot availableLotSnapshot : availableLotSnapshots) {
                            DataRecord profile = availableLotSnapshot.getValue(DataRecord.class);
                            if (profile.getLocation().toString().equals(match.get("location").toString())){
                                availableLotSnapshot.getRef().setValue(null);
                                Log.e("asdasd","pay successful");
                            }
                        }
                        DataSnapshot allAvailableLotSnapshot2 = dataSnapshot.child("Users").child(new_owner).child("Sharing");
                        Iterable<DataSnapshot> availableLotSnapshots2 = allAvailableLotSnapshot.getChildren();
                        for (DataSnapshot availableLotSnapshot : availableLotSnapshots) {
                            DataRecord profile = availableLotSnapshot.getValue(DataRecord.class);
                            if (profile.getLocation().toString().equals(match.get("location").toString())){
                                availableLotSnapshot.getRef().setValue(null);
                                Log.e("asdasd","pay successful");
                            }
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.e("TAG", "Failed to read value.", error.toException());
                    }
                });

                requestPayment(view);
            }
        });

        // It's recommended to create the PaymentsClient object inside of the onCreate method.
        mPaymentsClient = PaymentsUtil.createPaymentsClient(this);
        checkIsReadyToPay();

        setSupportActionBar(toolbar_detail);
        toolbar_detail.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void checkIsReadyToPay() {
        // The call to isReadyToPay is asynchronous and returns a Task. We need to provide an
        // OnCompleteListener to be triggered when the result of the call is known.
        PaymentsUtil.isReadyToPay(mPaymentsClient).addOnCompleteListener(
                new OnCompleteListener<Boolean>() {
                    public void onComplete(Task<Boolean> task) {
                        try {
                            boolean result = task.getResult(ApiException.class);
                            setGooglePayAvailable(result);
                        } catch (ApiException exception) {
                            // Process error
                            Log.w("isReadyToPay failed", exception);
                        }
                    }
                });
    }

    private void setGooglePayAvailable(boolean available) {
        // If isReadyToPay returned true, show the button and hide the "checking" text. Otherwise,
        // notify the user that Pay with Google is not available.
        // Please adjust to fit in with your current user flow. You are not required to explicitly
        // let the user know if isReadyToPay returns false.
        if (available) {
            mGooglePayStatusText.setVisibility(View.GONE);
            mGooglePayButton.setVisibility(View.VISIBLE);
        } else {
            mGooglePayStatusText.setText(R.string.googlepay_status_unavailable);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case LOAD_PAYMENT_DATA_REQUEST_CODE:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        PaymentData paymentData = PaymentData.getFromIntent(data);
                        handlePaymentSuccess(paymentData);
                        break;
                    case Activity.RESULT_CANCELED:
                        // Nothing to here normally - the user simply cancelled without selecting a
                        // payment method.
                        break;
                    case AutoResolveHelper.RESULT_ERROR:
                        Status status = AutoResolveHelper.getStatusFromIntent(data);
                        handleError(status.getStatusCode());
                        break;
                }

                // Re-enables the Pay with Google button.
                mGooglePayButton.setClickable(true);
                break;
        }
    }

    private void handlePaymentSuccess(PaymentData paymentData) {
        // PaymentMethodToken contains the payment information, as well as any additional
        // requested information, such as billing and shipping address.
        //
        // Refer to your processor's documentation on how to proceed from here.
        PaymentMethodToken token = paymentData.getPaymentMethodToken();

        // getPaymentMethodToken will only return null if PaymentMethodTokenizationParameters was
        // not set in the PaymentRequest.
        if (token != null) {
            // If the gateway is set to example, no payment information is returned - instead, the
            // token will only consist of "examplePaymentMethodToken".
            if (token.getToken().equals("examplePaymentMethodToken")) {
                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setTitle("Warning")
                        .setMessage("Gateway name set to \"example\" - please modify " +
                                "Constants.java and replace it with your own gateway.")
                        .setPositiveButton("OK", null)
                        .create();
                alertDialog.show();
            }

            String billingName = paymentData.getCardInfo().getBillingAddress().getName();
            Toast.makeText(this, getString(R.string.payments_show_name, billingName), Toast.LENGTH_LONG).show();

            // Use token.getToken() to get the token string.
            Log.d("PaymentData", "PaymentMethodToken received");
            Intent intent = new Intent(CheckoutActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void handleError(int statusCode) {
        // At this stage, the user has already seen a popup informing them an error occurred.
        // Normally, only logging is required.
        // statusCode will hold the value of any constant from CommonStatusCode or one of the
        // WalletConstants.ERROR_CODE_* constants.
        Log.w("loadPaymentData failed", String.format("Error code: %d", statusCode));
    }

    // This method is called when the Pay with Google button is clicked.
    public void requestPayment(View view) {
        // Disables the button to prevent multiple clicks.
        mGooglePayButton.setClickable(false);

        // The price provided to the API should include taxes and shipping.
        // This price is not displayed to the user.
        String price = PaymentsUtil.microsToString(mPlaceHolderItem.getPriceMicros() + mShippingCost);

        TransactionInfo transaction = PaymentsUtil.createTransaction(price);
        PaymentDataRequest request = PaymentsUtil.createPaymentDataRequest(transaction);
        Task<PaymentData> futurePaymentData = mPaymentsClient.loadPaymentData(request);

        // Since loadPaymentData may show the UI asking the user to select a payment method, we use
        // AutoResolveHelper to wait for the user interacting with it. Once completed,
        // onActivityResult will be called with the result.
        AutoResolveHelper.resolveTask(futurePaymentData, this, LOAD_PAYMENT_DATA_REQUEST_CODE);
    }

    private void initItemUI() {
        //TextView itemName = findViewById(R.id.text_item_name);
        //ImageView itemImage = findViewById(R.id.image_item_image);
        //TextView itemPrice = findViewById(R.id.text_item_price);

        //itemName.setText(mPlaceHolderItem.getName());
        //itemImage.setImageResource(mPlaceHolderItem.getImageResourceId());
        //itemPrice.setText(PaymentsUtil.microsToString(mPlaceHolderItem.getPriceMicros()));
    }
}
