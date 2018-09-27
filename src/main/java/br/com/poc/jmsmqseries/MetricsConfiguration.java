package br.com.poc.jmsmqseries;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Slf4jReporter;
import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.dropwizard.DropwizardConfig;
import io.micrometer.core.instrument.dropwizard.DropwizardMeterRegistry;
import io.micrometer.core.instrument.util.HierarchicalNameMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class MetricsConfiguration {


    @Bean
    public MetricRegistry metricRegistry() {
        return new MetricRegistry();
    }

    @Bean(destroyMethod = "close")
    public Slf4jReporter metricsReporter(MetricRegistry metricRegistry) {
        Slf4jReporter reporter = Slf4jReporter.forRegistry(metricRegistry)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();

        reporter.start(2, TimeUnit.SECONDS);
        return reporter;
    }

    @Bean
    public MeterRegistry meterRegistry() {
        DropwizardConfig config = new DropwizardConfig() {
            @Override
            public String prefix() {
                return "log";
            }

            @Override
            public String get(String key) {
                return null;
            }
        };

        return new DropwizardMeterRegistry(config, metricRegistry(), HierarchicalNameMapper.DEFAULT, Clock.SYSTEM) {
            @Override
            protected Double nullGaugeValue() {
                return null;
            }
        };

    }
}
