package com.fibre.rollstock.dao.roll;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fibre.rollstock.model.RollDescription;
import com.mysql.jdbc.Statement;


@Repository
@Transactional(readOnly=false)
public class RollDescriptionDAOImpl extends JdbcDaoSupport implements RollDescriptionDAO {

	protected static final Logger LOGGER = LoggerFactory.getLogger(RollDescriptionDAOImpl.class);
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private DataSource dataSource;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
		jdbcTemplate = getJdbcTemplate();
	}

	@Override
	public long create(RollDescription description) {
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		final String sql = "INSERT INTO roll_description(text, details) "
							+ "VALUES(?, ?)";
		
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement ps = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, description.getText());
					ps.setString(2, description.getDetails());
					
					return ps;
				}
			}, keyHolder
		);
		
		return (long) keyHolder.getKey();
	}

	@Override
	public void update(RollDescription description) {
		
		final String sql = "UPDATE roll_description "
						+ "SET text = ?, details = ? "
						+ "WHERE id = ?";
		
		jdbcTemplate.update(sql, description.getText(),
									description.getDetails(),
									description.getId());
	}

	@Override
	public void delete(long id) {
		
		String sql = "DELETE FROM roll_description WHERE id = ?";
		
		jdbcTemplate.update(sql, id);
		
	}

	@Override
	public List<RollDescription> findAll() {
		
		String sql = "SELECT * FROM roll_description";
		
		List<RollDescription> rollDescriptions = new ArrayList<RollDescription>();
				
		jdbcTemplate.query(sql, new RowMapper<RollDescription>() {
			@Override
			public RollDescription mapRow(ResultSet rs, int rowNum) throws SQLException {
								
				RollDescription description = new RollDescription();
				description.setId(rs.getLong("id"));
				description.setText(rs.getString("text"));
				description.setDetails(rs.getString("details"));
				description.setDateCreated(rs.getTimestamp("date_created"));
				
				rollDescriptions.add(description);
				
				return description;
			}
		});	
		
		return rollDescriptions;
	}	
	
	@Override
	public int count() {
		
		String sql = "SELECT COUNT(*) FROM roll_description";
		
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
}
