package com.fibre.rollstock.dao.customer;

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

import com.fibre.rollstock.model.Customer;
import com.mysql.jdbc.Statement;

@Repository
@Transactional(readOnly=false)
public class CustomerDAOImpl extends JdbcDaoSupport implements CustomerDAO {

	protected static final Logger LOGGER = LoggerFactory.getLogger(CustomerDAOImpl.class);
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private DataSource dataSource;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
		jdbcTemplate = getJdbcTemplate();
	}

	@Override
	public long create(Customer customer) {
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		final String sql = "INSERT INTO customer(name, email, phone_landline, phone_mobile, address) "
							+ "VALUES(?, ?, ?, ?, ?)";
		
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement ps = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, customer.getName());
					ps.setString(2, customer.getEmail());
					ps.setString(3, customer.getPhoneLandline());
					ps.setString(4, customer.getPhoneMobile());
					ps.setString(5, customer.getAddress());
					
					return ps;
				}
			}, keyHolder
		);
		
		return (long) keyHolder.getKey();
	}

	@Override
	public void update(Customer customer) {
		
		final String sql = "UPDATE customer "
						+ "SET name = ?, email = ?, phone_landline = ?, phone_mobile = ?, address = ? "
						+ "WHERE id = ?";
		
		jdbcTemplate.update(sql, customer.getName(), 
									customer.getEmail(),
									customer.getPhoneLandline(), 
									customer.getPhoneMobile(), 
									customer.getAddress(), 
									customer.getId());
	}

	@Override
	public void delete(long id) {
		
		String sql = "DELETE FROM customer WHERE id = ?";
		
		jdbcTemplate.update(sql, id);
		
	}

	@Override
	public List<Customer> findAll() {
		
		String sql = "SELECT * FROM customer";
	
		List<Customer> customers = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Customer>(Customer.class));
		
		return customers;
	}		
	
	@Override
	public int count() {
		
		String sql = "SELECT COUNT(*) FROM customer";
		
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
}
