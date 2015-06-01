package offtopic

import com.google.common.collect.ImmutableMap
import io.dropwizard.Application
import io.dropwizard.Bundle
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment

import io.dropwizard.assets.AssetsBundle
import io.dropwizard.views.ViewBundle

import groovy.transform.TypeChecked
import offtopic.resources.MainResource
import org.joda.time.DateTimeZone
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Primary application instance for Dropwizard
 */
@TypeChecked
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

        environment.healthChecks().register('sanity', new offtopic.health.BasicHealth())
        environment.jersey().register(new MainResource())
    }

    @Override
    String getName() {
        return "Offtopic"
    }

    @Override
    void initialize(Bootstrap<OfftopicConfig> bootstrap) {
        this.logger.info("Initializing application")
        bootstrap.addBundle(new AssetsBundle('/assets/', '/assets'))

        bootstrap.addBundle(new ViewBundle<OfftopicConfig>() {
            @Override
            ImmutableMap<String, ImmutableMap<String, String>> getViewConfiguration(OfftopicConfig config) {
                return config.viewRendererConfiguration
            }
        } as Bundle)

        /*
         * Force our default timezone to always be UTC
         */
        DateTimeZone.setDefault(DateTimeZone.UTC)
        logger.info("Set default timezone to UTC")


    }
}
