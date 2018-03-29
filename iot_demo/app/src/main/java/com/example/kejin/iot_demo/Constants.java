package com.example.kejin.iot_demo;

import android.util.Pair;

import com.google.android.gms.wallet.WalletConstants;

import java.util.Arrays;
import java.util.List;

public class Constants {

    public static final int PAYMENTS_ENVIRONMENT = WalletConstants.ENVIRONMENT_TEST;

    // The allowed networks to be requested from the API. If the user has cards from networks not
    // specified here in their account, these will not be offered for them to choose in the popup.
    public static final List<Integer> SUPPORTED_NETWORKS = Arrays.asList(
            WalletConstants.CARD_NETWORK_AMEX,
            WalletConstants.CARD_NETWORK_DISCOVER,
            WalletConstants.CARD_NETWORK_VISA,
            WalletConstants.CARD_NETWORK_MASTERCARD
    );

    public static final List<Integer> SUPPORTED_METHODS = Arrays.asList(
            // PAYMENT_METHOD_CARD returns to any card the user has stored in their Google Account.
            WalletConstants.PAYMENT_METHOD_CARD,

            // PAYMENT_METHOD_TOKENIZED_CARD refers to EMV tokenized credentials stored in the
            // Google Pay app, assuming it's installed.
            // Please keep in mind tokenized cards may exist in the Google Pay app without being
            // added to the user's Google Account.
            WalletConstants.PAYMENT_METHOD_TOKENIZED_CARD
    );

    // Required by the API, but not visible to the user.
    public static final String CURRENCY_CODE = "USD";

    // Supported countries for shipping (use ISO 3166-1 alpha-2 country codes).
    // Relevant only when requesting a shipping address.
    public static final List<String> SHIPPING_SUPPORTED_COUNTRIES = Arrays.asList(
            "US",
            "GB"
    );

    // The name of your payment processor / gateway. Please refer to their documentation for
    // more information.
    public static final String GATEWAY_TOKENIZATION_NAME = "example";

    // Custom parameters required by the processor / gateway.
    // In many cases, your processor / gateway will only require a gatewayMerchantId.
    // Please refer to your processor's documentation for more information. The number of parameters
    // required and their names vary depending on the processor.
    public static final List<Pair<String, String>> GATEWAY_TOKENIZATION_PARAMETERS = Arrays.asList(
            Pair.create("gatewayMerchantId", "exampleGatewayMerchantId")

            // Your processor may require additional parameters.
    );

    // Only used for DIRECT tokenization. Can be removed when using GATEWAY tokenization.
    public static final String DIRECT_TOKENIZATION_PUBLIC_KEY = "REPLACE_ME";

    private Constants() {
    }

}
