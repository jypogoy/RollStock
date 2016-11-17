package com.fibre.rollstock.dao.van;

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
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fibre.rollstock.dao.trucker.TruckerDAO;
import com.fibre.rollstock.model.Bin;
import com.fibre.rollstock.model.Trucker;
import com.fibre.rollstock.model.VanInspection;
import com.fibre.rollstock.model.Warehouse;
import com.fibre.rollstock.model.Worker;
import com.mysql.jdbc.Statement;

@Repository
@Transactional(readOnly=false)
public class VanInspectionDAOImpl extends JdbcDaoSupport implements VanInspectionDAO {

	protected static final Logger LOGGER = LoggerFactory.getLogger(VanInspectionDAOImpl.class);
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private DataSource dataSource;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
		jdbcTemplate = getJdbcTemplate();
	}

	@Override
	public long create(VanInspection inspection) {
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		final String sql = "INSERT INTO van_inspection(trucker_id, truck_number, van_number, remarks) "
							+ "VALUES(?, ?, ?, ?)";
		
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement ps = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					ps.setLong(1, inspection.getTrucker().getId());
					ps.setString(2, inspection.getTruckNumber());
					ps.setString(3, inspection.getVanNumber());
					ps.setString(4, inspection.getRemarks());
					/*ps.setLong(5, inspection.getInspectedBy().getId());*/
					
					return ps;
				}
			}, keyHolder
		);
		
		return (long) keyHolder.getKey();
	}

	@Override
	public void update(VanInspection inspection) {
		
		final String sql = "UPDATE van_inspection "
						+ "SET trucker_id = ?, truck_number = ?, van_number = ?, remarks = ?, inspected_by = ? "
						+ "WHERE id = ?";
		
		jdbcTemplate.update(sql, inspection.getTrucker().getId(), 
									inspection.getTruckNumber(),
									inspection.getVanNumber(), 
									inspection.getRemarks(), 
									inspection.getInspectedBy().getId(), 
									inspection.getId());
	}

	@Override
	public void delete(long id) {
		
		String sql = "DELETE FROM van_inspection WHERE id = ?";
		
		jdbcTemplate.update(sql, id);
		
	}

	@Override
	public List<VanInspection> findAll() {
		
		String sql = "SELECT * " 
						+ "FROM van_inspection vi "
						+ "INNER JOIN trucker t on t.id = vi.trucker_id "
						+ "LEFT JOIN worker w on w.id = vi.inspected_by";
	
		//List<VanInspection> vanInspections = jdbcTemplate.query(sql, new BeanPropertyRowMapper<VanInspection>(VanInspection.class));
		
		List<VanInspection> vanInspections = new ArrayList<VanInspection>();
		
		jdbcTemplate.query(sql, new RowMapper<VanInspection>() {
			@Override
			public VanInspection mapRow(ResultSet rs, int rowNum) throws SQLException {
				VanInspection inspection = new VanInspection();
				inspection.setId(rs.getLong("vi.id"));
								
				Trucker trucker = new Trucker();
				trucker.setId(rs.getLong("t.id"));
				trucker.setName(rs.getString("t.name"));
				trucker.setEmail("t.email");
				trucker.setPhoneMobile("t.phone_mobile");
				trucker.setPhoneLandline("t.phone_landline");
				trucker.setAddress("t.address");
				trucker.setDateCreated(rs.getTimestamp("t.date_created"));				
				inspection.setTrucker(trucker);			
				
				inspection.setTruckNumber(rs.getString("vi.truck_number"));		
				inspection.setVanNumber(rs.getString("vi.van_number"));	
				inspection.setRemarks(rs.getString("vi.remarks"));	
				inspection.setDateCreated(rs.getTimestamp("vi.date_created"));
				
				Worker worker = new Worker();
				worker.setId(rs.getLong("w.id"));
				worker.setName(rs.getString("w.name"));
				worker.setEmail(rs.getString("w.email"));
				worker.setPhoneLandline(rs.getString("w.phone_landline"));
				worker.setPhoneMobile(rs.getString("w.phone_mobile"));
				worker.setAddress(rs.getString("w.address"));
				worker.setDateCreated(rs.getTimestamp("w.date_created"));
				inspection.setInspectedBy(worker);
				
				vanInspections.add(inspection);
				
				return inspection;
			}
		});	
		
		return vanInspections;
	}		
	
	@Override
	public int count() {
		
		String sql = "SELECT COUNT(*) FROM van_inspection";
		
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
}
