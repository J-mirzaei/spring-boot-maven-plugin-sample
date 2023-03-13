package step_definitions

import io.cucumber.core.options.Constants
import org.junit.platform.suite.api.ConfigurationParameter
import org.junit.platform.suite.api.ConfigurationParameters
import org.junit.platform.suite.api.SelectClasspathResource
import org.junit.platform.suite.api.Suite


@Suite
@SelectClasspathResource("features")
@ConfigurationParameters(
    ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = "step_definitions"),
    ConfigurationParameter(key = Constants.PLUGIN_PUBLISH_QUIET_PROPERTY_NAME, value = "true"),
    ConfigurationParameter(key = Constants.PLUGIN_PROPERTY_NAME, value = "pretty, json:target/cucumber/report.json")
)
class RunCucumberIT
