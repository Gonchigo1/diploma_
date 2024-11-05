package mn.astvision.starter.config;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.lang.NonNull;
import lombok.RequiredArgsConstructor;
import org.bson.UuidRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author digz6666
 */
@Configuration
@EnableMongoRepositories(
        basePackages = {"mn.astvision.starter.repository"},
        mongoTemplateRef = "mongoTemplate"
)
@EnableMongoAuditing
@RequiredArgsConstructor
public class DatabaseConfig extends AbstractMongoClientConfiguration {

    @Value("${primary.mongodb.dbName}")
    private String dbName;

    @Value("${primary.mongodb.uri}")
    private String primaryUri;

    private final MongoCustomConversions mongoCustomConversions;

    @Override
    @NonNull
    protected String getDatabaseName() {
        return dbName;
    }

    @Primary
    @Bean
    @Override
    @NonNull
    public MongoClient mongoClient() {
        return MongoClients.create(
                MongoClientSettings.builder()
                        .applyConnectionString(new ConnectionString(primaryUri))
                        .uuidRepresentation(UuidRepresentation.STANDARD)
                        .build());
    }

    @Primary
    @Bean//(name = "mongoTemplate")
    public MongoTemplate mongoTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, getDatabaseName());
    }

//    @Primary
//    @Bean(name = "mongoOperations")
//    public MongoOperations mongoOperations(MongoClient mongoClient) {
//        return new MongoTemplate(mongoClient, getDatabaseName());
//    }

    @Primary
    @Bean
    public MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
        return new MongoTransactionManager(
                dbFactory,
                TransactionOptions.builder()
                        .readConcern(ReadConcern.MAJORITY)
                        .writeConcern(WriteConcern.MAJORITY)
                        .build()
        );
    }

    @Bean
    @Override
    @NonNull
    public MappingMongoConverter mappingMongoConverter(
            @NonNull MongoDatabaseFactory databaseFactory,
            @NonNull MongoCustomConversions customConversions,
            @NonNull MongoMappingContext mappingContext) {
        MappingMongoConverter mappingMongoConverter = super.mappingMongoConverter(databaseFactory,
                customConversions, mappingContext);
//        converter.setTypeMapper(typeMapper());
        mappingMongoConverter.setCustomConversions(mongoCustomConversions);
        mappingMongoConverter.setMapKeyDotReplacement(".");
        return mappingMongoConverter;
    }
}
