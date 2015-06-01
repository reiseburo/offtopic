package offtopic

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.common.collect.ImmutableMap
import io.dropwizard.Configuration

/**
 *
 */
class OfftopicConfig extends Configuration {
    @JsonProperty
    private ImmutableMap<String, ImmutableMap<String, String>> \
                            viewRendererConfiguration = ImmutableMap.of()
}
