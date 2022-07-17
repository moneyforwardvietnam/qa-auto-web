package runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        plugin = {"pretty", "json:target/cucumber_json/cucumber.json"},
        features = "src/test/resources/features",
        glue = "steps",
        tags = "@ARM-2129"

)
public class CucumberTestSuite {
}
