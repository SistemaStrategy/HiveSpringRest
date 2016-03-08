package com.hiverest.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hive.jdbc.HiveDriver;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.hadoop.hive.HiveClient;
import org.springframework.data.hadoop.hive.HiveClientFactory;
import org.springframework.data.hadoop.hive.HiveClientFactoryBean;
import org.springframework.data.hadoop.hive.HiveTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

/**
 * Created by root on 3/2/16.
*/
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.hiverest.spring")
@PropertySources({
        @PropertySource("classpath:app.properties")
})
public class SpringConfiguration {

    @Value("${app.hiveUrl}")
    private String hiveUrl;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    DataSource hiveDataSource() {
        return new SimpleDriverDataSource(new HiveDriver(), hiveUrl);
    }

    @Bean
    HiveClientFactory hiveClientFactory(@Qualifier("hiveDataSource") DataSource hiveDataSource) throws Exception {
        HiveClientFactoryBean hiveClientFactoryBean = new HiveClientFactoryBean();
        hiveClientFactoryBean.setHiveDataSource(hiveDataSource);
        hiveClientFactoryBean.afterPropertiesSet();
        return hiveClientFactoryBean.getObject();
    }

    @Bean
    HiveClient hiveClient(@Qualifier("hiveClientFactory") HiveClientFactory hiveClientFactory){
        return hiveClientFactory.getHiveClient();
    }

}
