package com.team4.sportscenter.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Configuration
public class MoMoConfig {
    public static String PARTNER_CODE;
    public static String ACCESS_KEY;
    public static String SECRET_KEY;
    public static String ENDPOINT;
    public static String RETURN_URL;
    public static String IPN_URL;

    @Value("${momo.partner-code:MOMOBKUN20180529}")
    private String injectedPartnerCode;

    @Value("${momo.access-key:klm05TvNBzhg7h7j}")
    private String injectedAccessKey;

    @Value("${momo.secret-key:at67qH6mk8w5Y1nAyMoYKMWACiEi2bsa}")
    private String injectedSecretKey;

    @Value("${momo.endpoint:https://test-payment.momo.vn/v2/gateway/api/create}")
    private String injectedEndpoint;

    @Value("${momo.return-url:http://localhost:5173/member/payment-result}")
    private String injectedReturnUrl;

    @Value("${momo.ipn-url:http://localhost:5173/member/payment-result}")
    private String injectedIpnUrl;

    @PostConstruct
    public void init() {
        PARTNER_CODE = injectedPartnerCode;
        ACCESS_KEY = injectedAccessKey;
        SECRET_KEY = injectedSecretKey;
        ENDPOINT = injectedEndpoint;
        RETURN_URL = injectedReturnUrl;
        IPN_URL = injectedIpnUrl;
    }

    public static String signHmacSHA256(String data, String secretKey) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] hash = sha256_HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
