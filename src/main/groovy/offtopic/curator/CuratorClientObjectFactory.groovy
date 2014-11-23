package offtopic.curator

import org.apache.commons.pool2.*
import org.apache.commons.pool2.impl.DefaultPooledObject;


/**
 * Object factory class for keeping an object pool of CuratorClient objects
 *
 * @author R. Tyler Croy
 */
class CuratorClientObjectFactory extends BasePooledObjectFactory<CuratorClient> {

    private String zookeepers = null

    public CuratorClientObjectFactory(String zks) {
        this.zookeepers = zks
    }

    @Override
    CuratorClient create() {
        return new CuratorClient(this.zookeepers)
    }

    @Override
    PooledObject<CuratorClient> wrap(CuratorClient client) {
        return new DefaultPooledObject<CuratorClient>(client)
    }
}
