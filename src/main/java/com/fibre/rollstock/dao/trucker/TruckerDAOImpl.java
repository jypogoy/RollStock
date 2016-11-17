package com.fibre.rollstock.dao.trucker;

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

import com.fibre.rollstock.model.Trucker;
import com.mysql.jdbc.Statement;

@Repository
@Transactional(readOnly=false)
public class TruckerDAOImpl extends JdbcDaoSupport implements TruckerDAO {

	protected static final Logger LOGGER = LoggerFactory.getLogger(TruckerDAOImpl.class);
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private DataSource dataSource;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
		jdbcTemplate = getJdbcTemplate();
	}

	@Override
	public long create(Trucker trucker) {
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		final String sql = "INSERT INTO trucker(name, email, phone_landline, phone_mobile, address) "
							+ "VALUES(?, ?, ?, ?, ?)";
		
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement ps = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, trucker.getName());
					ps.setString(2, trucker.getEmail());
					ps.setString(3, trucker.getPhoneLandline());
					ps.setString(4, trucker.getPhoneMobile());
					ps.setString(5, trucker.getAddress());
					
					return ps;
				}
			}, keyHolder
		);
		
		return (long) keyHolder.getKey();
	}

	@Override
	public void update(Trucker trucker) {
		
		final String sql = "UPDATE trucker "
						+ "SET name = ?, email = ?, phone_landline = ?, phone_mobile = ?, address = ? "
						+ "WHERE id = ?";
		
		jdbcTemplate.update(sql, trucker.getName(), 
									trucker.getEmail(),
									trucker.getPhoneLandline(), 
									trucker.getPhoneMobile(), 
									trucker.getAddress(), 
									trucker.getId());
	}

	@Override
	public void delete(long id) {
		
		String sql = "DELETE FROM trucker WHERE id = ?";
		
		jdbcTemplate.update(sql, id);
		
	}

	@Override
	public List<Trucker> findAll() {
		
		String sql = "SELECT * FROM trucker";
	
		List<Trucker> truckers = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Trucker>(Trucker.class));
		
		return truckers;
	}		
	
	@Override
	public Trucker findById(long id) {
		
		String sql = "SELECT * FROM trucker WHERE id = ?";
		
		return jdbcTemplate.queryForObject(sql, new Object[]{ id }, new BeanPropertyRowMapper<Trucker>(Trucker.class));
	}
	
	@Override
	public int count() {
		
		String sql = "SELECT COUNT(*) FROM trucker";
		
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}	
}
