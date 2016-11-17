package com.fibre.rollstock.dao.bin;

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

import com.fibre.rollstock.dao.roll.RollDAO;
import com.fibre.rollstock.model.Bin;
import com.fibre.rollstock.model.Roll;
import com.fibre.rollstock.model.Warehouse;
import com.mysql.jdbc.Statement;

@Repository
@Transactional(readOnly=false)
public class BinDAOImpl extends JdbcDaoSupport implements BinDAO {

	protected static final Logger LOGGER = LoggerFactory.getLogger(BinDAOImpl.class);
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private RollDAO rollDao;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
		jdbcTemplate = getJdbcTemplate();
	}

	@Override
	public long create(Bin bin) {
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		final String sql = "INSERT INTO bin(name, length, width, height, warehouse_id) "
							+ "VALUES(?, ?, ?, ?, ?)";
		
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement ps = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, bin.getName());
					ps.setDouble(2, bin.getLength());
					ps.setDouble(3, bin.getWidth());
					ps.setDouble(4, bin.getHeight());
					ps.setDouble(5, bin.getWarehouse().getId());
					
					return ps;
				}
			}, keyHolder
		);
		
		return (long) keyHolder.getKey();
	}

	@Override
	public void update(Bin bin) {
		
		final String sql = "UPDATE bin "
						+ "SET name = ?, length = ?, width = ?, height = ?, warehouse_id = ? "
						+ "WHERE id = ?";
		
		jdbcTemplate.update(sql, bin.getName(), 
									bin.getLength(),
									bin.getWidth(), 
									bin.getHeight(),
									bin.getWarehouse().getId(),
									bin.getId());
	}

	@Override
	public void delete(long id) {
		
		String sql = "DELETE FROM bin WHERE id = ?";
		
		jdbcTemplate.update(sql, id);
		
	}

	@Override
	public List<Bin> findAll() {
		
		String sql = "SELECT b.id as binId, b.name as binName, b.length as binLength, b.width as binWidth, b.height as binHeight, b.date_created as binDateCreated, "
						+ "w.id as whId, w.name as whName, w.description as whDescription, w.address as whAddress, w.date_created as whDateCreated "
						+ "FROM bin b "
						+ "INNER JOIN warehouse w ON w.id = b.warehouse_id ";
	
		List<Bin> bins = new ArrayList<Bin>();
		
		jdbcTemplate.query(sql, new RowMapper<Bin>() {
			@Override
			public Bin mapRow(ResultSet rs, int rowNum) throws SQLException {
				Warehouse warehouse = new Warehouse();
				warehouse.setId(rs.getLong("whId"));
				warehouse.setName(rs.getString("whName"));
				warehouse.setDescription(rs.getString("whDescription"));
				warehouse.setAddress(rs.getString("whAddress"));
				warehouse.setDateCreated(rs.getTimestamp("whDateCreated"));
				
				Bin bin = new Bin();
				bin.setId(rs.getLong("binId"));
				bin.setName(rs.getString("binName"));
				bin.setLength(rs.getDouble("binLength"));
				bin.setWidth(rs.getDouble("binWidth"));
				bin.setHeight(rs.getDouble("binHeight"));
				bin.setWarehouse(warehouse);
				bin.setDateCreated(rs.getTimestamp("binDateCreated"));
				
				List<Roll> rolls = rollDao.findByBin(rs.getLong("binId"));
				bin.setRolls(rolls);
				
				bins.add(bin);
				
				return bin;
			}
		});
		
		return bins;
	}

	@Override
	public Bin findById(long binId) {
		// TODO Auto-generated method stub
		return null;
	}	
	
	@Override
	public List<Bin> findByWarehouse(long warehouseId) {
		
		String sql = "SELECT b.id as binId, b.name as binName, b.length as binLength, b.width as binWidth, b.height as binHeight, b.date_created as binDateCreated, "
						+ "w.id as whId, w.name as whName, w.description as whDescription, w.address as whAddress, w.date_created as whDateCreated "
						+ "FROM bin b "
						+ "INNER JOIN warehouse w ON w.id = b.warehouse_id "
						+ "WHERE w.id = ?";
		
		List<Bin> bins = new ArrayList<Bin>();
		
		jdbcTemplate.query(sql, new RowMapper<Bin>() {
			@Override
			public Bin mapRow(ResultSet rs, int rowNum) throws SQLException {
				Warehouse warehouse = new Warehouse();
				warehouse.setId(rs.getLong("whId"));
				warehouse.setName(rs.getString("whName"));
				warehouse.setDescription(rs.getString("whDescription"));
				warehouse.setAddress(rs.getString("whAddress"));
				warehouse.setDateCreated(rs.getTimestamp("whDateCreated"));
				
				Bin bin = new Bin();
				bin.setId(rs.getLong("binId"));
				bin.setName(rs.getString("binName"));
				bin.setLength(rs.getDouble("binLength"));
				bin.setWidth(rs.getDouble("binWidth"));
				bin.setHeight(rs.getDouble("binHeight"));
				bin.setWarehouse(warehouse);
				bin.setDateCreated(rs.getTimestamp("binDateCreated"));
				
				List<Roll> rolls = rollDao.findByBin(rs.getLong("binId"));
				bin.setRolls(rolls);
				
				bins.add(bin);
				
				return bin;
			}
		}, warehouseId);
		
		return bins;
	}
	
	@Override
	public List<Bin> findAvailableByWarehouse(long warehouseId) {
		
		String sql = "SELECT b.id as binId, b.name as binName, b.length as binLength, b.width as binWidth, b.height as binHeight, b.date_created as binDateCreated, "
						+ "w.id as whId, w.name as whName, w.description as whDescription, w.address as whAddress, w.date_created as whDateCreated "
						+ "FROM bin b "
						+ "INNER JOIN warehouse w ON w.id = b.warehouse_id "
						+ "WHERE w.id = ? "
						+ "AND NOT EXISTS ("
							+ "SELECT 1 FROM `storage` WHERE bin_id = b.id"
						+ ")";
		
		List<Bin> bins = new ArrayList<Bin>();
		
		jdbcTemplate.query(sql, new RowMapper<Bin>() {
			@Override
			public Bin mapRow(ResultSet rs, int rowNum) throws SQLException {
				Warehouse warehouse = new Warehouse();
				warehouse.setId(rs.getLong("whId"));
				warehouse.setName(rs.getString("whName"));
				warehouse.setDescription(rs.getString("whDescription"));
				warehouse.setAddress(rs.getString("whAddress"));
				warehouse.setDateCreated(rs.getTimestamp("whDateCreated"));
				
				Bin bin = new Bin();
				bin.setId(rs.getLong("binId"));
				bin.setName(rs.getString("binName"));
				bin.setLength(rs.getDouble("binLength"));
				bin.setWidth(rs.getDouble("binWidth"));
				bin.setHeight(rs.getDouble("binHeight"));
				bin.setWarehouse(warehouse);
				bin.setDateCreated(rs.getTimestamp("binDateCreated"));
				
				List<Roll> rolls = rollDao.findByBin(rs.getLong("binId"));
				bin.setRolls(rolls);
				
				bins.add(bin);
				
				return bin;
			}
		}, warehouseId);
		
		return bins;
	}

	@Override
	public int count() {
		
		String sql = "SELECT COUNT(*) FROM bin";
		
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
}
