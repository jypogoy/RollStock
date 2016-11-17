package com.fibre.rollstock.dao.warehouse;

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

import com.fibre.rollstock.dao.bin.BinDAO;
import com.fibre.rollstock.model.Bin;
import com.fibre.rollstock.model.Warehouse;
import com.mysql.jdbc.Statement;

@Repository
@Transactional(readOnly=false)
public class WarehouseDAOImpl extends JdbcDaoSupport implements WarehouseDAO {

	protected static final Logger LOGGER = LoggerFactory.getLogger(WarehouseDAOImpl.class);
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private BinDAO binDao;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
		jdbcTemplate = getJdbcTemplate();
	}

	@Override
	public long create(Warehouse warehouse) {
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		final String sql = "INSERT INTO warehouse(name, description, address) "
							+ "VALUES(?, ?, ?)";
		
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement ps = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, warehouse.getName());
					ps.setString(2, warehouse.getDescription());
					ps.setString(3, warehouse.getAddress());
					
					return ps;
				}
			}, keyHolder
		);
		
		return (long) keyHolder.getKey();
	}

	@Override
	public void update(Warehouse warehouse) {
		
		final String sql = "UPDATE warehouse "
						+ "SET name = ?, description = ?, address = ? "
						+ "WHERE id = ?";
		
		jdbcTemplate.update(sql, warehouse.getName(), 
									warehouse.getDescription(),
									warehouse.getAddress(), 
									warehouse.getId());
	}

	@Override
	public void delete(long id) {
		
		String sql = "DELETE FROM warehouse WHERE id = ?";
		
		jdbcTemplate.update(sql, id);
		
	}

	@Override
	public List<Warehouse> findAll() {
		
		String sql = "SELECT w.id as whId, w.name as whName, w.description as whDescription, w.address as whAddress, w.date_created as whDateCreated, count(b.id) as bin_count "
						+ "FROM warehouse w "
						+ "LEFT JOIN bin b on b.warehouse_id = w.id "
						+ "GROUP BY w.id ";
		
		//String sqlBins = "SELECT * FROM bin WHERE bin.warehouse_id = ?";
		
		List<Warehouse> warehouses = new ArrayList<Warehouse>();
		
		jdbcTemplate.query(sql, new RowMapper<Warehouse>() {
			@Override
			public Warehouse mapRow(ResultSet rs, int rowNum) throws SQLException {
				Warehouse warehouse = new Warehouse();
				warehouse.setId(rs.getLong("whId"));
				warehouse.setName(rs.getString("whName"));
				warehouse.setDescription(rs.getString("whDescription"));
				warehouse.setAddress(rs.getString("whAddress"));
				warehouse.setDateCreated(rs.getTimestamp("whDateCreated"));
				
				//List<Bin> bins = jdbcTemplate.query(sqlBins, new Object[]{rs.getLong("whId")}, new BeanPropertyRowMapper<Bin>(Bin.class));
				List<Bin> bins = binDao.findByWarehouse(rs.getLong("whId"));
				warehouse.setBins(bins);
				
				warehouses.add(warehouse);
				
				return warehouse;
			}
		});	
		
		return warehouses;
	}

	@Override
	public Warehouse findById(long id) {
		
		String sql = "SELECT * FROM warehouse WHERE id = ?";
		
		return (Warehouse) jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<Warehouse>(Warehouse.class));
	}		
	
	@Override
	public int count() {
		
		String sql = "SELECT COUNT(*) FROM warehouse";
		
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
}
