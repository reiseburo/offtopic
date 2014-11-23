package offtopic

import org.apache.commons.pool2.impl.GenericObjectPool

@Singleton(strict=false)
class CuratorPool {
    GenericObjectPool<CuratorClient> pool

    private CuratorPool() {
    }
}
