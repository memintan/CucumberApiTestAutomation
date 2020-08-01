package AgileProApi.step_definitions;

import AgileProApi.Utilities.ConfigurationReader;
import AgileProApi.pojo.Spartan;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;

import java.io.File;
import java.util.List;
import java.util.Map;


public class APIStepDefinition {

    private String username;
    private String password;
    private ContentType contentType;
    private Response response;
    private Spartan spartan;

    @Given("authorization credentials are provided for {string}")
    public void authorization_credentials_are_provided_for(String userType) {
        if (userType.equals("admin")) {
            username = ConfigurationReader.getProperty("username");
            password = ConfigurationReader.getProperty("password");
        }
    }

    @Given("user accepts content type {string}")
    public void user_accepts_content_type(String type) {
        if (type.toLowerCase().contains("json")) {
            contentType = ContentType.JSON;
        } else if (type.toLowerCase().contains("xml")) {
            contentType = ContentType.HTML;
        } else if (type.toLowerCase().contains("html")) {
            contentType = ContentType.HTML;
        }
    }

    @When("user sends GET request to {string}")
    public void user_sends_GET_request_to(String endPoint) {
        response =  given().
                        accept(contentType).auth().basic(username,password).
                    when().
                        get(endPoint).prettyPeek();
    }

    @Then("user verifies that response status code is {int}")
    public void user_verifies_that_response_status_code_is(int statusCode) {
        Assert.assertEquals(statusCode, response.getStatusCode());
    }

    @Then("user verifies that response has {int} Spartans")
    public void user_verifies_that_response_has_Spartans(int numberOfSpartan) {
        response.then().
                    assertThat().statusCode(200).
                and().
                    header("Content-Type", "application/json;charset=UTF-8").
                //and().
                    //contentType(ContentType.JSON).
                and().
                    body("size()", is(numberOfSpartan));

    }

    /*******************************************************************************
     *
     *
     * Add multiple spartans
     * @param dataTable
     */

    @When("user create Spartan POJO Object with List information")
    public void user_create_Spartan_POJO_Object_with_List_information(List<Map<String, String>> dataTable) {
        String name = dataTable.get(0).get("name");
        String gender = dataTable.get(0).get("gender");
        Integer mobilePhoneNumber = Integer.valueOf(dataTable.get(0).get("phone"));

        spartan = new Spartan(name, gender,mobilePhoneNumber);

    }

    @Then("user sends POST request to {string}")
    public void user_sends_POST_request_to(String endPoint) {
        response =  given().
                        auth().basic(username,password).contentType(contentType).accept(contentType).body(spartan).
                    when().
                        post(endPoint).prettyPeek();
    }

    @Then("user verifies that Spartan Born")
    public void user_verifies_that_Spartan_Born() {
        response.   then().
                        assertThat().
                        body("success", is("A Spartan is Born!")).
                    and().
                        body("data.name", is(spartan.getName())).
                        body("data.gender", is(spartan.getGender())).
                        body("data.phone", is(spartan.getMobilePhoneNumber()));
    }

    /*******************************************************************************
     *
     *
     * Add just one spartan
     * @param dataTable
     */

    @When("user create Spartan POJO Object with following information")
    public void user_create_Spartan_POJO_Object_with_following_information(List<String> dataTable) {
        String name = dataTable.get(0);
        String gender = dataTable.get(1);
        Integer mobilePhoneNumber = Integer.valueOf(dataTable.get(2));

        spartan = new Spartan(name, gender, mobilePhoneNumber);
    }

    /***********************************************
     *
     *
     * Add Spartan with JSON Object
     *
     */

    @Then("user sends POST request to {string} with following JSON Object")
    public void user_sends_POST_request_to_with_following_JSON_Object(String endPoint, List<String> dataTable) {

        //String jsonObject = "{ \"name\": \"Furkan\", \"gender\": \"Male\", \"phone\": 1234567890 }";
        String jsonObject = "{ \"name\": \""+dataTable.get(0)+"\", \"gender\": \""+dataTable.get(1)+"\", \"phone\": "+dataTable.get(2)+" }";

        response = given().
                auth().basic(username,password).
                accept(contentType).
                contentType(contentType).
                body(jsonObject).
                when().
                post(endPoint).prettyPeek();
    }

    @And("user verifies that Spartan Born in Json Response")
    public void userVerifiesThatSpartanBornInJsonResponse() {
        response.then().
                assertThat().
                body("success", is("A Spartan is Born!"));
    }

    /***********************************
     *
     * Add Spartan from JSON file
     * @param endOfUrl
     */
    @Then("user sends POST request to {string} from JSON file")
    public void user_sends_POST_request_to_from_JSON_file(String endOfUrl) {
        File jsonFile = new File(System.getProperty("user.dir") + File.separator + "SpartanToAdd.json");
        response =
                given().
                        auth().basic(username,password).
                        accept(contentType).
                        contentType(contentType).
                        body(jsonFile).
                        when().
                        post(endOfUrl).prettyPeek();
    }
//    @Then("user sends POST request to {string} from JSON file")
//    public void userSendsPOSTRequestToFromJSONFile(String endPoint) {
//
//
//        //System.getProperty("file.separator")
//        File jsonFile = new File(System.getProperty("user.dir") + File.separator + "SpartanToAdd.json");
//
//        response =
//                given().
//                        auth().basic(username,password).
//                        accept(contentType).
//                        contentType(contentType).
//                        body(jsonFile).
//                        when().
//                        post(endPoint).prettyPeek();
//
//    }

    @Then("user sends delete request to {string} with last id")
    public void userSendsDeleteRequestToWithLastId(String endPoint) {
        Response response1 = given().auth().basic(username,password).
                accept(contentType).contentType(contentType).
                when().get(endPoint).prettyPeek();


        //get first spartan's id
        Integer id = response1.jsonPath().get("max{it.id}.id");



        //delete multiple spartans
//        for (int i = 1; i < 20 ; i++) {
//            response = given().
//                    auth().basic(username, password).
//                    accept(contentType).
//                    when().
//                    delete(endPoint+"/"+i).prettyPeek();
//        }

        response = given().
                auth().basic(username, password).
                accept(contentType).
                when().
                delete(endPoint+"/"+id).prettyPeek();


    }
}