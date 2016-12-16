import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.util.List;

/**
 * Created by Gerard van den Bosch on 12/8/2016.
 */
public class DominoPizzaCouponGrabber {

    private static String getCookieString() {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet("https://bestellen.dominos.nl/eStore/nl/CustomerDetails/SpecifyDeliveryAddress?streetNo=19&" +
                "street=RIETVELDLAAN&suburb=GRONINGEN&state=NL&postcode=9731MJ&addressSearchString=19%209731MJ");

        System.out.println("Requesting : " + httpget.getURI());

        try {
            //Create a local instance of cookie store
            CookieStore cookieStore = new BasicCookieStore();

            //Create local HTTP context
            HttpContext localContext = new BasicHttpContext();

            //Bind custom cookie store to the local context
            localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

            //Get Response
            HttpResponse response = httpclient.execute(httpget, localContext);

            //Get Headers
            Header[] headers = response.getAllHeaders();
            for (Header header : headers) {
                System.out.println(" --> " + header.getName() + ":" + header.getValue());
            }

            //Get Cookies
            System.out.println("\n\nCookies : ");
            List<Cookie> cookies = cookieStore.getCookies();
            for (int i = 0; i < cookies.size(); i++) {
                System.out.println("Cookie: " + cookies.get(i));
            }

            return cookies.get(0).getValue();

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {

        String cookie = getCookieString();

        File file = new File("output.txt");

        for (int i = 0; i < 400; i++) {
            StringBuilder urlAddon = new StringBuilder();
            urlAddon.append(Integer.toString(i));
            if (urlAddon.length() < 6) {
                for (int j = 0; j < 6 - urlAddon.length(); j++) {
                    urlAddon.insert(0, "0");
                }
                if (urlAddon.length() == 4) {
                    urlAddon.insert(0, "0");
                }
            }

            String url = "https://bestellen.dominos.nl/eStore/nl/Basket/ApplyVoucher?voucherCode=" + urlAddon.toString();
            URL obj = new URL(url);
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

            //add reuqest header
            con.setRequestMethod("POST");
            String USER_AGENT = "Mozilla/5.0";
            con.setRequestProperty("Cookie", "ASP.NET_SessionId=" + cookie + "; session_id=1cef59bd-ade4-4fc7-acec-37223a43608d; visitor_id=8b171f59-2f8f-440e-abf8-1b32c307f4f1; Language=nl; _vwo_uuid_v2=3462A6B4D3F3536274288A70DEAF6909|308c6a66e1d61aba82debdf9dd75e336; _ga=GA1.3.1957957846.1481210233; _ga=GA1.2.1957957846.1481210233; _gat_martijn_dominos=1; domino_customer_state=NC; utag_main=v_id:0158df02814c001a7bef97d6159f05072002b06a00bd0$_sn:1$_ss:0$_st:0; origin-server=AWS; _gali=apply_voucher");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();


            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
                if (inputLine.contains("wordt niet geaccepteerd") || inputLine.contains("verlopen")) {
                    System.out.println(urlAddon + ": Invalid coupon");
                } else {
                    BufferedWriter output = null;
                    try {
                        output = new BufferedWriter(new FileWriter(file, true));
                        output.write(urlAddon.toString() + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (output != null) {
                            output.close();
                        }
                    }
                }
            }
            System.out.println(response.toString());
            in.close();
        }
    }
}
