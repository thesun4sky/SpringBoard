package com.choi.board.util;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class IdGenerator {

	
	
	private static IdGenerator instance = new IdGenerator();
	
	public static IdGenerator getInstance(){
		return instance;
	}
	
	private IdGenerator(){
		
	}
	
	public int generateNextId(String sequenceName,JdbcTemplate jdbcTemplate) throws Exception{
		
		StringBuffer sql = new StringBuffer();
		sql.append("select next_value from id_sequence ");
		sql.append("where sequence_name=?");
		
		
		//int aaa= jdbcTemplate.queryForInt("select count(*) from id_sequence");
		
		int id = jdbcTemplate.queryForInt(sql.toString(), sequenceName);
		
		
		id++;
		
		sql = new StringBuffer();
		sql.append("update id_sequence set next_value=? ");
		sql.append("where sequence_name=?");
		
		jdbcTemplate.update(sql.toString(),id,sequenceName);
		return id;
		
	}
}
