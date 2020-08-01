package AgileProApi.runner;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/api",
        glue = "AgileProApi/step_definitions",
        dryRun = false,
        strict = true,
        tags = "@add_spartan_with_list",
        plugin = {
                //"html:target/default-report",
                "json:target/cucumber.json",
                "rerun:rerun.txt"
        }

)

public class APITestRunner {
}
