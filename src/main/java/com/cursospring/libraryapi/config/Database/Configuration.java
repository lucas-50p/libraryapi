package com.cursospring.libraryapi.config.Database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Value("${spring.datasource.url}")
    String url;

    @Value("${spring.datasource.username}")
    String username;

    @Value("${spring.datasource.password}")
    String password;

    @Value("${spring.datasource.driver-class-name}")
    String driver;

    // Na utilize em produção
//    @Bean
//    public DataSource datasource(){
//        DriverManagerDataSource ds = new DriverManagerDataSource();
//        ds.setUrl(url);
//        ds.setUsername(username);
//        ds.setPassword(password);
//        ds.setDriverClassName(driver);
//        return ds;
//    }

    // Muitos usuarios

    @Bean
    public DataSource hikariDataSource() {

        HikariConfig config = new HikariConfig();
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driver);
        config.setJdbcUrl(url);

        // Aumentar quanto tem mais usuarios
        config.setMaximumPoolSize(10);
        config.setMaximumPoolSize(1);// Minimo
        config.setPoolName("library-db-pool");
        config.setMaxLifetime(600000);// 600 mil ms
        config.setConnectionTimeout(100000);// Timeout para conseguir a conexao
        config.setConnectionTestQuery("select 1");

        return new HikariDataSource(config);
    }
}
