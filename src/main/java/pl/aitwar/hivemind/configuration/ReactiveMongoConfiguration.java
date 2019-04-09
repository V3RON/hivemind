package pl.aitwar.hivemind.configuration;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import java.util.List;

/**
 * Configuration class for Reactive Mongo Repositories
 *
 * @author Szymon Chmal
 */
@EnableReactiveMongoRepositories
public class ReactiveMongoConfiguration extends AbstractReactiveMongoConfiguration {
    @Value("${spring.data.mongodb.database}")
    private String databaseName;

    @Override
    protected String getDatabaseName() {
        return databaseName;
    }

    @Override
    public MongoClient reactiveMongoClient() {
        return MongoClients.create();
    }

    @Override
    @Bean
    public CustomConversions customConversions() {
        return new MongoCustomConversions(List.of(
                Jsr310Converters.getConvertersToRegister()
        ));
    }

    @Bean
    public MongoTransactionManager transactionManager(MongoDbFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }
}
