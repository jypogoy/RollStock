package com.fibre.rollstock.dao.storage;

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

import com.fibre.rollstock.model.Storage;
import com.mysql.jdbc.Statement;

@Repository
@Transactional(readOnly=false)
public class StorageDAOImpl extends JdbcDaoSupport implements StorageDAO {

	protected static final Logger LOGGER = LoggerFactory.getLogger(StorageDAOImpl.class);
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private DataSource dataSource;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
		jdbcTemplate = getJdbcTemplate();
	}

	@Override
	public long create(Storage storage) {
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		final String sql = "INSERT INTO storage(roll_id, bin_id, check_in_by) "
							+ "VALUES(?, ?, ?)";
		
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement ps = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					ps.setLong(1, storage.getRoll().getId());
					ps.setLong(2, storage.getBin().getId());
					ps.setLong(3, storage.getCheckInBy().getId());
					
					return ps;
				}
			}, keyHolder
		);
		
		return (long) keyHolder.getKey();
	}

	@Override
	public void update(Storage storage) {
		
		final String sql = "UPDATE storage "
						+ "SET roll_id = ?, bin_id = ?, check_in_by = ?, check_out_date = ?, check_out_by = ? "
						+ "WHERE id = ?";
		
		jdbcTemplate.update(sql, storage.getRoll().getId(), 
									storage.getBin().getId(),
									storage.getCheckInBy().getId(), 
									storage.getCheckOutDate(), 
									storage.getCheckOutBy().getId(), 
									storage.getId());
	}

	@Override
	public void delete(long id) {
		
		String sql = "DELETE FROM storage WHERE id = ?";
		
		jdbcTemplate.update(sql, id);
		
	}

	@Override
	public List<Storage> findAll() {
		
		String sql = "SELECT * FROM storage";
	
		List<Storage> storages = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Storage>(Storage.class));
		
		return storages;
	}		
	
	@Override
	public int count() {
		
		String sql = "SELECT COUNT(*) FROM storage";
		
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
}
