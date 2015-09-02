package offtopic

import groovy.util.logging.Slf4j

/**
 * class for wrapping our system configuration
 */
@Singleton
@Slf4j
class Configuration extends Properties {
    /**
     * Load defaults, starting in the current working directory, searching for
     * 'offtopic.properties'
     */
    public boolean loadDefaults() {
        File cwdConfiguration = new File('offtopic.properties')
        if (cwdConfiguration.exists()) {
            log.info('Loading `offtopic.properties` for configuration')
            this.load(new FileInputStream(cwdConfiguration))
        }
        else {
            log.warn('Could not load configuration file {}', cwdConfiguration.absolutePath)
        }
    }
}
