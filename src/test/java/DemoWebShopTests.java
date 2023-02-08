import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class DemoWebShopTests {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "https://demowebshop.tricentis.com";
    }

    String cookieValue = "834490656B444025433B0A4C3CE0561862C27D4D8BAB05B4157F57B1CFAC3E6A04BD96C7D00B67F2E3089" +
            "D772949A8A7E8E7914DB9AB5E2B48F6A07BB6D641ED6F103FDE29FA39B21432A289271FF23FB84E80D3DFDCC95657A30F8F" +
            "59DD25EB5CD8E99CEE323028563BD8B8E54D9A208CD3677579D88A5B8EF4E8BCD209484CBDCF248451793EF64B11D34A9C9" +
            "0CBD3;";
    @Test
    void addToCartTest() {
        String cookieValue = "C3B23684816523C3E10934D04BB7269BD98869D9B42CE39E42379171707" +
                "1721A102B8200F60C64202EFF08A8BB31019B281869DCC0A013C1BDDB8A2C040B083CB7EE18743398AB5F22BC4F575" +
                "96FA47732C6ED3EF4505FAC4C44E1324BC72D5D6C434630381EAAD9EF1A5CC8316CAA105B3A34D3709FBA22C343FD" +
                "CB8F4546262AC16884DD69B73F0ECE3F16DFCBAE2A;",
                body = "product_attribute_72_5_18=65" +
                        "&product_attribute_72_6_19=54" +
                        "&product_attribute_72_3_20=57" +
                        "&product_attribute_72_8_30=94" +
                        "&addtocart_72.EnteredQuantity=3";

        given()
//                .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .cookie("NOPCOMMERCE.AUTH", cookieValue) // todo move to file
                .body(body)
                .when()
                .post("/addproducttocart/details/72/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("success", is(true))
                .body("message", is("The product has been added to your <a href=\"/cart\">shopping cart</a>"));
    }

    @Test
    void addToCartAnonymTest() {
        String body = "product_attribute_72_5_18=65" +
                "&product_attribute_72_6_19=54" +
                "&product_attribute_72_3_20=57" +
                "&product_attribute_72_8_30=94" +
                "&addtocart_72.EnteredQuantity=3";

        given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .body(body)
                .when()
                .post("/addproducttocart/details/72/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("success", is(true))
                .body("message", is("The product has been added to your <a href=\"/cart\">shopping cart</a>"))
                .body("updatetopcartsectionhtml", is("(3)"));
    }

    @Test
    void editAddressTest() {

        String body = "Address.Id=2629637&Address.FirstName=Test" +
                "&Address.LastName=Test" +
                "&Address.Email=test%40mail.ru" +
                "&Address.Company=" +
                "&Address.CountryId=88" +
                "&Address.StateProvinceId=0" +
                "&Address.City=city" +
                "&Address.Address1=test" +
                "&Address.Address2=" +
                "&Address.ZipPostalCode=233" +
                "&Address.PhoneNumber=343" +
                "&Address.FaxNumber=";

        given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .body(body)
                .cookie("NOPCOMMERCE.AUTH", cookieValue) // todo move to file
                .when()
                .post("https://demowebshop.tricentis.com/customer/addressedit/2629637")
                .then()
                .log().all()
                .statusCode(302);
    }
}