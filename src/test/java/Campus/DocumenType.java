package Campus;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.swing.text.Document;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class DocumenType {

    String documentID;

    String documentName;
    Faker  faker=new Faker();
    RequestSpecification recSpec;

    @BeforeClass
    public void Setup()  {
        baseURI="https://test.mersys.io";

        Map<String,String> userCredential=new HashMap<>();
        userCredential.put("username","turkeyts");
        userCredential.put("password","TechnoStudy123");
        userCredential.put("rememberMe","true");

        Cookies cookies=
                given()
                        .contentType(ContentType.JSON)
                        .body(userCredential)

                        .when()
                        .post("/auth/login")

                        .then()
                        //.log().all()
                        .statusCode(200)
                        .extract().response().getDetailedCookies()
                ;

        recSpec= new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addCookies(cookies)
                .build();
    }


    @Test
    public void createDocument(){

//        Map<String,String> document=new HashMap<>();
//        documentName=faker.name()+faker.number().digits(5);
//        document.put("name",documentName);
//        document.put("stage","Employment");
//        document.put("Description","asdasd");

        Map<String,String> document=new HashMap<>();
        documentName=faker.address().fullAddress()+faker.number().digits(5);
        document.put("name",documentName);
      //  document.put("stage","Dismissal");
     //   document.put("Description", faker.number().digits(5));



        documentID=
                given()
                        .spec(recSpec)
                        .body(document)
                        .log().body()

                        .when()
                        .post("/school-service/api/attachments/create")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().path("id");
        ;

        System.out.println("documentID = " + documentID);
    }



    @Test(dependsOnMethods = "createDocument")
    public void createDocumentNegative(){

    }

    @Test(dependsOnMethods = "createDocumentNegative")
    public void updateDocument(){

    }

    @Test(dependsOnMethods = "updateDocument")
    public void deleteDocument(){

    }

    @Test(dependsOnMethods = "deleteDocument")
    public void deleteDocumentNegative(){

    }

}
