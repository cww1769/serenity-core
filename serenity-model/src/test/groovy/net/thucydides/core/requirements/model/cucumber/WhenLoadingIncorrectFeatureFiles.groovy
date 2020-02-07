package net.thucydides.core.requirements.model.cucumber

import cucumber.runtime.io.MultiLoader
import cucumber.runtime.model.CucumberFeature
import spock.lang.Specification

import static net.thucydides.core.requirements.model.cucumber.ScenarioDisplayOption.WithTitle

class WhenLoadingIncorrectFeatureFiles extends Specification {

    def invalidFeatureFile = "src/test/resources/features/maintain_my_todo_list/invalid.feature"

    def "Should display a meaningful error message if there is a Gherkin syntax error"() {
        when:
            CucumberParser parser = new CucumberParser()
            parser.loadFeature(new File(invalidFeatureFile))
        then:
            InvalidFeatureFileException ex = thrown()
            ex.message.contains("Parser errors")
    }
}