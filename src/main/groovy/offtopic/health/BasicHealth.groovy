package offtopic.health

import com.codahale.metrics.health.HealthCheck
import com.codahale.metrics.health.HealthCheck.Result

/**
 * A basic healthcheck to ensure that the application is at least executing properly
 */
class BasicHealth extends HealthCheck {
    @Override
    protected Result check() {
        return Result.healthy()
    }
}
