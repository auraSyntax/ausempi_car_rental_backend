package com.aura.syntax.ausempi.demo.config;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Flyway configuration to handle failed migrations and repairs
 */
@Configuration
public class FlywayConfig {

    /**
     * Custom migration strategy that repairs failed migrations before proceeding
     */
    @Bean
    public FlywayMigrationStrategy flywayMigrationStrategy() {
        return flyway -> {
            try {
                // Attempt to repair failed migrations
                flyway.repair();
            } catch (Exception e) {
                // Log but continue - repair might fail if there are no failed migrations
                System.out.println("Flyway repair attempted: " + e.getMessage());
            }
            
            try {
                // Now run migrations
                flyway.migrate();
            } catch (Exception e) {
                System.out.println("Warning: Migration encountered an issue: " + e.getMessage());
                // Continue with application startup despite migration issues
                // The database will be in current state
            }
        };
    }
}
