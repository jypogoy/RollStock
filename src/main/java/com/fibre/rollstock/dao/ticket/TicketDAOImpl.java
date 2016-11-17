package com.fibre.rollstock.dao.ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fibre.rollstock.model.Ticket;
import com.mysql.jdbc.Statement;

@Repository
@Transactional(readOnly=false)
public class TicketDAOImpl extends JdbcDaoSupport implements TicketDAO {

	protected static final Logger LOGGER = LoggerFactory.getLogger(TicketDAOImpl.class);
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private DataSource dataSource;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
		jdbcTemplate = getJdbcTemplate();
	}

	@Override
	public long create() {

		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		final String sql = "INSERT INTO ticket(date_issued) "
							+ "VALUES(NOW())";
		
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement ps = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);					
					return ps;
				}
			}, keyHolder
		);
		
		return (long) keyHolder.getKey();
	}

	@Override
	public void update(Ticket ticket) {
		
	}

	@Override
	public void delete(long id) {

		String sql = "DELETE FROM ticket WHERE id = ?";
		
		jdbcTemplate.update(sql, id);
	}

	@Override
	public Ticket findById(long id) {

		return null;
	}

	@Override
	public List<Ticket> findAll() {

		return null;
	}
}
