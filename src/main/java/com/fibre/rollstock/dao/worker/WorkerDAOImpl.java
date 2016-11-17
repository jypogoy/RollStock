package com.fibre.rollstock.dao.worker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fibre.rollstock.model.Worker;
import com.mysql.jdbc.Statement;

@Repository
@Transactional(readOnly=false)
public class WorkerDAOImpl extends JdbcDaoSupport implements WorkerDAO {

	protected static final Logger LOGGER = LoggerFactory.getLogger(WorkerDAOImpl.class);
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private DataSource dataSource;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
		jdbcTemplate = getJdbcTemplate();
	}
	
	@Override
	public long create(Worker worker) {
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		final String sql = "INSERT INTO worker(name, email, phone_landline, phone_mobile, address) "
							+ "VALUES(?, ?, ?, ?, ?)";
		
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement ps = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, worker.getName());
					ps.setString(2, worker.getEmail());
					ps.setString(3, worker.getPhoneLandline());
					ps.setString(4, worker.getPhoneMobile());
					ps.setString(5, worker.getAddress());
					
					return ps;
				}
			}, keyHolder
		);
		
		return (long) keyHolder.getKey();
	}

	@Override
	public void update(Worker worker) {
		
		final String sql = "UPDATE worker "
							+ "SET name = ?, email = ?, phone_landline = ?, phone_mobile = ?, address = ? "
							+ "WHERE id = ?";
		
		jdbcTemplate.update(sql, worker.getName(), 
									worker.getEmail(),
									worker.getPhoneLandline(), 
									worker.getPhoneMobile(), 
									worker.getAddress(), 
									worker.getId());
	}
	
	@Override
	public void delete(long id) {
		
		String sql = "DELETE FROM worker WHERE id = ?";

		jdbcTemplate.update(sql, id);
	}

	@Override
	public List<Worker> findAll() {

		String sql = "SELECT * FROM worker";
		
		List<Worker> workers = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Worker>(Worker.class));
		
		return workers;
	}

	@Override
	public int count() {
		
		String sql = "SELECT COUNT(*) FROM worker";
		
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
}
