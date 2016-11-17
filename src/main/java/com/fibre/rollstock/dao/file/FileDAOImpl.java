package com.fibre.rollstock.dao.file;

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

import com.fibre.rollstock.model.ElectronicFile;
import com.mysql.jdbc.Statement;

@Repository
@Transactional(readOnly=false)
public class FileDAOImpl extends JdbcDaoSupport implements FileDAO {

	protected static final Logger LOGGER = LoggerFactory.getLogger(FileDAOImpl.class);
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private DataSource dataSource;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
		jdbcTemplate = getJdbcTemplate();
	}

	@Override
	public long create(ElectronicFile file) {
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		final String sql = "INSERT INTO e_file(file_name, base64) "
							+ "VALUES(?, ?)";
		
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement ps = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, file.getFileName());
					ps.setString(2, file.getBase64());
					
					return ps;
				}
			}, keyHolder
		);
		
		return (long) keyHolder.getKey();
	}

	@Override
	public void update(ElectronicFile file) {
		
		final String sql = "UPDATE e_file "
						+ "SET file_name = ?, base64 = ? "
						+ "WHERE id = ?";
		
		jdbcTemplate.update(sql, file.getFileName(), 
									file.getBase64(),									
									file.getId());
	}

	@Override
	public void delete(long id) {
		
		String sql = "DELETE FROM e_file WHERE id = ?";
		
		jdbcTemplate.update(sql, id);
		
	}

	@Override
	public List<ElectronicFile> findAll() {
		
		String sql = "SELECT * FROM e_file";
	
		List<ElectronicFile> electronicFiles = jdbcTemplate.query(sql, new BeanPropertyRowMapper<ElectronicFile>(ElectronicFile.class));
		
		return electronicFiles;
	}		
	
	@Override
	public int count() {
		
		String sql = "SELECT COUNT(*) FROM electronic_file";
		
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
}
