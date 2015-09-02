package offtopic

import groovy.json.JsonSlurper
import groovy.util.logging.Slf4j
import offtopic.curator.CuratorPool
import org.apache.curator.framework.CuratorFramework

/**
 * KafkaService is a helper class to help expose data about Kafka to the
 * Ratpack app
 */
@Slf4j
class KafkaService {
    final static String BROKERS_PATH = "/brokers/ids"
    final static String TOPICS_PATH = "/brokers/topics"

    public static List<String> fetchBrokers() {
        JsonSlurper parser = new JsonSlurper()
        List<String> brokers = []

        CuratorPool.withCurator { CuratorFramework c ->
            c.children.forPath(BROKERS_PATH).each { String id ->
                // Pulling this into a String buffer since parse(byte[]) is
                // throwing a stackoverflow error
                String buffer = new String(c.data.forPath("${BROKERS_PATH}/${id}"))
                brokers.add(parser.parseText(buffer))
            }
        }

        log.info('Fetched brokers from Zookeeper: {}', brokers)
        return brokers
    }

    public static List<String> fetchTopics() {
        List brokers = []
        CuratorPool.withCurator { c ->
            brokers = c.children.forPath(TOPICS_PATH)
        }
        return brokers
    }
}
