package offtopic.curator

import org.apache.commons.pool2.impl.GenericObjectPool

@Singleton(strict=false)
class CuratorPool extends GenericObjectPool<CuratorClient>{
    private CuratorPool() {
        /** XXX: Figure out how to get ZK from settings */
        super(new CuratorClientObjectFactory('localhost:2181'))
    }
}
