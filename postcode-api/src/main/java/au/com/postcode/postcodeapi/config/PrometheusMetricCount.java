package au.com.postcode.postcodeapi.config;

import io.prometheus.client.Counter;
import io.prometheus.client.Histogram;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@Slf4j
@SuppressWarnings("checkstyle:magicNumber")
public final class PrometheusMetricCount {

    private final Counter numberOfPostalCodeLookup = Counter.build()
            .name("postalCode_hits")
            .labelNames("postalCode")
            .help("Count the number of times the postal code lookup hits.")
            .register();


    private final Histogram recordSaveExecutionTime = Histogram.build()
            .name("persistent_hits")
            .labelNames("postalCode")
            .linearBuckets(0, 100, 15)
            .help("metrics to view execution time of save record")
            .register();

    public void numberOfPostalCodeLookupEvent(final String eventType) {
        numberOfPostalCodeLookup.labels(eventType).inc();
    }

    public void recordSaveTimeMetricCollector(final String action, final Duration enrichmentTime) {
        log.info("changeRequestMetricCollector:Duration in seconds {} , millis {}",
                enrichmentTime.getSeconds(), enrichmentTime.toMillis());
        recordSaveExecutionTime.labels(action).observe(enrichmentTime.toMillis());
    }



}
