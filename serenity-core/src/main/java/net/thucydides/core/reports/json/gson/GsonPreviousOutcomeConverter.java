package net.thucydides.core.reports.json.gson;

import com.google.common.base.Optional;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import net.serenitybdd.core.history.PreviousTestOutcome;
import net.thucydides.core.ThucydidesSystemProperty;
import net.thucydides.core.util.EnvironmentVariables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class GsonPreviousOutcomeConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(GsonPreviousOutcomeConverter.class);

    private final EnvironmentVariables environmentVariables;

    Gson gson;

    protected Gson getGson() {
        return gson;
    }

    private final String encoding;

    @Inject
    public GsonPreviousOutcomeConverter(EnvironmentVariables environmentVariables) {
        this.environmentVariables = environmentVariables;
        encoding = ThucydidesSystemProperty.THUCYDIDES_REPORT_ENCODING.from(environmentVariables, StandardCharsets.UTF_8.name());
        GsonBuilder gsonBuilder = new GsonBuilder();
        this.gson = (usePrettyPrinting()) ? gsonBuilder.setPrettyPrinting().create() : gsonBuilder.create();
    }

    public Optional<PreviousTestOutcome> fromJson(InputStream inputStream) throws IOException {
        return fromJson(new InputStreamReader(inputStream, encoding));
    }

    public Optional<PreviousTestOutcome> fromJson(Reader jsonReader) {
        PreviousTestOutcome testOutcome = gson.fromJson(jsonReader, PreviousTestOutcome.class);
        return isValid(testOutcome) ? Optional.of(testOutcome) : Optional.<PreviousTestOutcome>absent();
    }

    private boolean isValid(PreviousTestOutcome testOutcome) {
        return !testOutcome.getId().isEmpty();
    }

    public void toJson(PreviousTestOutcome testOutcome, OutputStream outputStream) throws IOException {
        try(Writer out = new OutputStreamWriter(outputStream, encoding)) {
            gson.toJson(testOutcome, out);
        }
    }

    private boolean usePrettyPrinting() {
        return environmentVariables.getPropertyAsBoolean(ThucydidesSystemProperty.JSON_PRETTY_PRINTING, false);
    }
}
