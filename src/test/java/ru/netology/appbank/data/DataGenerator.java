package ru.netology.appbank.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.experimental.UtilityClass;

import java.util.Locale;

import static io.restassured.RestAssured.given;

@UtilityClass
public class DataGenerator {

    UserInfo user;

    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    static void setUpAll(UserInfo user) {
        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    @UtilityClass
    public static class User {
        public static UserInfo generateCorrectUser(String locale, String status) {
            Faker faker = new Faker(new Locale(locale));
            user = new UserInfo(faker.internet().emailAddress(), //считаем, что в качестве логинов пользователи используют почту
                    faker.internet().password(),
                    status);
            setUpAll(user);
            return user;
        }

        public static UserInfo generateWrongUser(String locale, String status) {
            Faker faker = new Faker(new Locale(locale));
            return new UserInfo(faker.internet().emailAddress(),
                    faker.internet().password(),
                    status);
        }
    }
}
