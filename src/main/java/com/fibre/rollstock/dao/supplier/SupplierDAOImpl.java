package com.fibre.rollstock.dao.supplier;

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

import com.fibre.rollstock.model.Supplier;
import com.mysql.jdbc.Statement;

@Repository
@Transactional(readOnly=false)
public class SupplierDAOImpl extends JdbcDaoSupport implements SupplierDAO {

	protected static final Logger LOGGER = LoggerFactory.getLogger(SupplierDAOImpl.class);
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private DataSource dataSource;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
		jdbcTemplate = getJdbcTemplate();
	}
	
	@Override
	public long create(Supplier supplier) {
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		final String sql = "INSERT INTO supplier(name, email, phone_landline, phone_mobile, address) "
							+ "VALUES(?, ?, ?, ?, ?)";
		
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement ps = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, supplier.getName());
					ps.setString(2, supplier.getEmail());
					ps.setString(3, supplier.getPhoneLandline());
					ps.setString(4, supplier.getPhoneMobile());
					ps.setString(5, supplier.getAddress());
					
					return ps;
				}
			}, keyHolder
		);
		
		return (long) keyHolder.getKey();
	}

	@Override
	public void update(Supplier supplier) {
		
		final String sql = "UPDATE supplier "
							+ "SET name = ?, email = ?, phone_landline = ?, phone_mobile = ?, address = ? "
							+ "WHERE id = ?";
		
		jdbcTemplate.update(sql, supplier.getName(), 
									supplier.getEmail(),
									supplier.getPhoneLandline(), 
									supplier.getPhoneMobile(), 
									supplier.getAddress(), 
									supplier.getId());
	}
	
	@Override
	public void delete(long id) {
		
		String sql = "DELETE FROM supplier WHERE id = ?";

		jdbcTemplate.update(sql, id);
	}

	@Override
	public List<Supplier> findAll() {

		String sql = "SELECT * FROM supplier";
		
		List<Supplier> suppliers = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Supplier>(Supplier.class));
		
		return suppliers;
	}

	@Override
	public int count() {
		
		String sql = "SELECT COUNT(*) FROM supplier";
		
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
}
