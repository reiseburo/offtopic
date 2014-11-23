package offtopic

import org.apache.curator.retry.ExponentialBackoffRetry
import org.apache.curator.framework.CuratorFrameworkFactory

class CuratorClient {
    private def client = null

    public CuratorClient(String zookeepers) {
        if (zookeepers?.length() <= 0) {
            throw new offtopic.errors.ConfigurationError("Cannot pass an empty string to CuratorClient()")
        }
    }
}
