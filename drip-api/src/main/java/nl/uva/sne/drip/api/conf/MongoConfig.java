/*
 * Copyright 2017 S. Koulouzis, Wang Junchao, Huan Zhou, Yang Hu 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.uva.sne.drip.api.conf;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 *
 * @author S. Koulouzis
 */
@Configuration
@EnableMongoRepositories(basePackages = "nl.uva.sne.drip.api")
@PropertySources({
    @PropertySource(value = "classpath:drip.properties", ignoreResourceNotFound = true),
    @PropertySource(value = "file:etc/drip.properties", ignoreResourceNotFound = true)
})
@ComponentScan("nl.uva.sne.drip")
public class MongoConfig extends AbstractMongoConfiguration {

    @Value("${db.name}")
    private String dbName;
    @Value("${db.username}")
    private String dbUsername;
    @Value("${db.password}")
    private String dbPass;

//    @Autowired
//    private MongoDbFactory mongoFactory;
//    @Autowired
//    private MongoMappingContext mongoMappingContext;
    @Override
    protected String getDatabaseName() {
        return dbName;
    }

    @Override
    public Mongo mongo() throws Exception {
        return new MongoClient("127.0.0.1", 27017);
    }

    @Override
    protected String getMappingBasePackage() {
        return "nl.uva.sne.drip";
    }
}
