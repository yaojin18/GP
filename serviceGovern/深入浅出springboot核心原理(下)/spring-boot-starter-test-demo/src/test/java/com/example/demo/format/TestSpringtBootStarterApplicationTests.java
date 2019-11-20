package com.example.demo.format;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class TestSpringtBootStarterApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	@Qualifier("db1JdbcTemplate")
	JdbcTemplate db3JdbcTemplate;
	/*
	 * @Autowired DataSource dataSource;
	 */

	@Test
	public void addDataData() {

		String sql = "insert into user_info(name,age) values('mic1',18)";
		db3JdbcTemplate.execute(sql);
	}
}
