package offtopic.curator

import org.apache.curator.framework.CuratorFramework
import org.apache.curator.retry.ExponentialBackoffRetry
import org.apache.curator.framework.CuratorFrameworkFactory

class CuratorClient {
    CuratorFramework client

    public CuratorClient(String zookeepers) {
        if (zookeepers?.length() <= 0) {
            throw new offtopic.errors.ConfigurationError("Cannot pass an empty string to CuratorClient()")
        }
        def retry = new ExponentialBackoffRetry(1000, 3)
        this.client = CuratorFrameworkFactory.newClient(zookeepers, retry)
        this.client.start()
    }
}
