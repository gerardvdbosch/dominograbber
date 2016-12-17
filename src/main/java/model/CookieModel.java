package model;

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

import java.io.IOException;
import java.util.List;

public class CookieModel {
    private String cookie;
    private String url;

    public CookieModel(Location location) {
        url = "https://bestellen.dominos.nl/eStore/nl/CustomerDetails/SpecifyDeliveryAddress?" +
                "streetNo=" + location.getStreetNumber() +
                "&street=" + location.getStreetName() +
                "&suburb=" + location.getCity() +
                "&state=" + location.getCountry() +
                "&postcode=" + location.getPostcode() +
                "&addressSearchString=" + location.getStreetNumber() +
                "%20" + location.getPostcode();

        cookie = getCookieValue(url);
    }

    public String getCookieValue(String url) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(url);

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

    public String getCookie() {
        return cookie;
    }
}
