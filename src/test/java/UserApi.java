
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.internal.http.Status;
import io.restassured.response.Response;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static io.restassured.RestAssured.given;

public class UserApi extends Common {

    private static String getUserCsvTemplate() throws URISyntaxException, IOException {
        URL url = UserApi.class.getResource("newUserCsvPayload.csv");
        Path path = Paths.get(url.toURI());
        return Files.readString(path, StandardCharsets.UTF_8);
    }
//    private static String getCsvUserName() throws URISyntaxException, IOException {
//        String csvTemplate = getUserCsvTemplate();
//        String csvUsername = csvTemplate.
//    }
    public static String createUser() throws Exception {
        UUID newUserId = UUID.randomUUID();
        String csvTemplate = getUserCsvTemplate();
        String csvPayload = csvTemplate.replace("{{PUT_UUID_HERE}}", newUserId.toString());

        Response response =
                given()
                        .cookies(getAuthCookies())
                        .header("Content-Type", "multipart/form-data")
                        .multiPart(new MultiPartSpecBuilder(
                                new ByteArrayInputStream(csvPayload.getBytes(StandardCharsets.UTF_8)))
                                .controlName("file")
                                .mimeType("text/plain")
                                .build())
                        .when()
                        .post("/api/public/user/import");

        if (!Status.SUCCESS.matches(response.statusCode())) {
            throw new Exception("User was not created");
        }
        return newUserId.toString();
    }
}
