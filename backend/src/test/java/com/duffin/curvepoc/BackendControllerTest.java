package com.duffin.curvepoc;

import com.duffin.curvepoc.controller.BackendController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.apache.http.HttpStatus;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = SpringBootVuejsApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class BackendControllerTest {
    @LocalServerPort
    private int port;

    @Test
    public void saysHello() {
//        when()
//                .get("http://localhost:" + port + "/api/hello")
//                .then()
//                .statusCode(HttpStatus.SC_OK)
//                .assertThat()
//                .body(is(equalTo(BackendController.HELLO_TEXT)));
    }
}