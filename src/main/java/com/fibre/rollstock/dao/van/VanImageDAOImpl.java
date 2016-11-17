package com.fibre.rollstock.dao.van;

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

import com.fibre.rollstock.model.VanImage;
import com.mysql.jdbc.Statement;

@Repository
@Transactional(readOnly=false)
public class VanImageDAOImpl extends JdbcDaoSupport implements VanImageDAO {

	protected static final Logger LOGGER = LoggerFactory.getLogger(VanImageDAOImpl.class);
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private DataSource dataSource;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
		jdbcTemplate = getJdbcTemplate();
	}

	@Override
	public long create(VanImage image) {
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		final String sql = "INSERT INTO van_image(inspection_id, file_id, is_before) "
							+ "VALUES(?, ?, ?)";
		
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement ps = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					ps.setLong(1, image.getVanInspection().getId());
					ps.setLong(2, image.getElectronicFile().getId());
					ps.setBoolean(3, image.isBefore());
					
					return ps;
				}
			}, keyHolder
		);
		
		return (long) keyHolder.getKey();
	}

	@Override
	public void update(VanImage image) {
		
		final String sql = "UPDATE van_image "
						+ "SET inspection_id = ?, file_id = ?, is_before = ? "
						+ "WHERE id = ?";
		
		jdbcTemplate.update(sql, image.getVanInspection().getId(), 
									image.getElectronicFile().getId(),
									image.isBefore(), 
									image.getId());
	}

	@Override
	public void delete(long id) {
		
		String sql = "DELETE FROM van_image WHERE id = ?";
		
		jdbcTemplate.update(sql, id);
		
	}

	@Override
	public List<VanImage> findAll() {
		
		String sql = "SELECT * FROM van_image";
	
		List<VanImage> vanImages = jdbcTemplate.query(sql, new BeanPropertyRowMapper<VanImage>(VanImage.class));
		
		return vanImages;
	}		
	
	@Override
	public int count() {
		
		String sql = "SELECT COUNT(*) FROM van_image";
		
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
}
