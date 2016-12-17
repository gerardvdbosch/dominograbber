import model.CookieModel;
import model.CouponModel;
import model.Location;

/**
 * Created by Admin on 12/16/2016.
 */
public class main {
    public static void main(String[] args) throws Exception {
        Location location = new Location();
        location.setStreetName("RIETVELDLAAN");
        location.setStreetNumber(19);
        location.setPostcode("9731MJ");
        location.setCity("GRONINGEN");
        location.setCountry("NL");

        CookieModel cookieModel = new CookieModel(location);
        CouponModel couponModel = new CouponModel(cookieModel);

        couponModel.tryRange(10, 20);

        couponModel.tryRange(1900, 2200);
    }
}
