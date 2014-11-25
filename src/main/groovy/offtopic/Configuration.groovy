package offtopic

/**
 * class for wrapping our system configuration
 */
@Singleton
class Configuration extends Properties {
    /**
     * Load defaults, starting in the current working directory, searching for
     * 'offtopic.properties'
     */
    public boolean loadDefaults() {
        File cwdConfiguration = new File('offtopic.properties')
        if (cwdConfiguration.exists()) {
            this.load(new FileInputStream(cwdConfiguration))
        }
    }
}
