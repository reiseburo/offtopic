package offtopic

import io.dropwizard.Application
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Primary application instance for Dropwizard
 */
class OfftopicApp extends Application<OfftopicConfig> {
    /**
     * Primary logger instance for the app
     */
    protected Logger logger = LoggerFactory.getLogger(this.class)

    /**
     * Main entry point for the application
     * @param arguments
     * @throws Exception
     */
    static void main(String[] arguments) throws Exception {
        new OfftopicApp().run(arguments)
    }

    @Override
    void run(OfftopicConfig configuration, Environment environment) throws Exception {
        this.logger.info("Starting the Offtopic application")
    }

    @Override
    String getName() {
        return "Offtopic"
    }

    @Override
    void initialize(Bootstrap<OfftopicConfig> bootstrap) {
        this.logger.info("Initializing application")
    }
}
