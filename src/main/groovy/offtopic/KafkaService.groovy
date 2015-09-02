package offtopic

import groovy.json.JsonSlurper
import groovy.util.logging.Slf4j
import org.apache.curator.framework.CuratorFramework

/**
 * KafkaService is a helper class to help expose data about Kafka to the
 * Ratpack app
 */
@Slf4j
class KafkaService {
    final static String BROKERS_PATH = "/brokers/ids"
    final static String TOPICS_PATH = "/brokers/topics"

    /**
     * Fetch brokers based on their conventional location in Zookeeper (/brokers/ids)
     * and return a list of maps for the various discovered broker metadata
     *
     * {"jmx_port":9999,"timestamp":"1428168559585","host":"kafka-stage1-1.lasangia.io","version":1,"port":6667}
     *
     * @return List of Map objects containing deserialized metadata
     */
    static List<Map> fetchBrokers(CuratorFramework curator) {
        JsonSlurper parser = new JsonSlurper()
        List<Map> brokers = []

        curator.children.forPath(BROKERS_PATH).each { String id ->
            byte[] buffer = curator.data.forPath("${BROKERS_PATH}/${id}")
            brokers.add(parser.parse(buffer) as Map)
        }

        log.info('Fetched brokers from Zookeeper: {}', brokers)
        return brokers
    }

    static List<String> fetchTopics(CuratorFramework curator) {
        return curator.children.forPath(TOPICS_PATH)
    }
}
