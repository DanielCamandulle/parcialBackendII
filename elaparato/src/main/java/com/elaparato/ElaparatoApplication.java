package com.elaparato;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import jakarta.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootApplication
public class ElaparatoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElaparatoApplication.class, args);
	}

	@Configuration
	public static class DatabaseInitializer {

		private final DataSource dataSource;

		@Autowired
		public DatabaseInitializer(DataSource dataSource) {
			this.dataSource = dataSource;
		}

		@PostConstruct
		public void initializeDatabase() {
			try (Connection connection = dataSource.getConnection()) {
				ScriptUtils.executeSqlScript(connection, new ClassPathResource("elaparato.sql"));
			} catch (SQLException e) {
				// Manejar la excepción
				e.printStackTrace();
			}
		}
	}
}
