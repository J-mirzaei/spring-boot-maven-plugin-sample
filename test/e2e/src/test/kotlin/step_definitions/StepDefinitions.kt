package step_definitions

import io.cucumber.java8.En
import io.restassured.RestAssured
import io.restassured.response.Response
import org.assertj.core.api.Assertions

class StepDefinitions : En {
    private var response: Response? = null

    init {
        RestAssured.baseURI = System.getProperty("cf.route", "http://localhost:8080")
        When("^I call \"([^\"]*)\"$") { url: String -> response = RestAssured.get(url) }
        Then("^I expect \"([^\"]*)\"$") { expectedAnswer: String ->
            Assertions.assertThat(response).isNotNull
            Assertions.assertThat(response!!.asPrettyString()).isEqualTo(expectedAnswer)
        }
        Then("I expect a HTTP {int}") { statusCode: Int ->
            Assertions.assertThat(response).isNotNull
            Assertions.assertThat(response!!.statusCode()).isEqualTo(statusCode)
        }
    }
}
