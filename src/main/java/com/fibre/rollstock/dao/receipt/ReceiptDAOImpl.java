package com.fibre.rollstock.dao.receipt;

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

import com.fibre.rollstock.model.Receipt;
import com.fibre.rollstock.model.Supplier;
import com.fibre.rollstock.model.Trucker;
import com.fibre.rollstock.model.Worker;
import com.mysql.jdbc.Statement;

@Repository
@Transactional(readOnly=false)
public class ReceiptDAOImpl extends JdbcDaoSupport implements ReceiptDAO {

	protected static final Logger LOGGER = LoggerFactory.getLogger(ReceiptDAOImpl.class);
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private DataSource dataSource;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
		jdbcTemplate = getJdbcTemplate();
	}

	@Override
	public long create(Receipt receipt) {

		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		final String sql = "INSERT INTO receipt(supplier_id, trucker_id, prepared_by, checked_by, noted_by, date_checked, date_noted) "
							+ "VALUES(?, ?, ?, ?, ?, ?, ?)";
		
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement ps = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					ps.setLong(1, receipt.getSupplier().getId());
					ps.setLong(2, receipt.getTrucker() != null ? receipt.getTrucker().getId() : 0);
					ps.setLong(3, receipt.getPreparedBy() != null ? receipt.getPreparedBy().getId() : 0);
					ps.setLong(4, receipt.getCheckedBy() != null ? receipt.getCheckedBy().getId() : 0);
					ps.setLong(5, receipt.getNotedBy() != null ? receipt.getNotedBy().getId() : 0);
					ps.setTimestamp(6, receipt.getDateChecked());
					ps.setTimestamp(7, receipt.getDateNoted());
					
					return ps;
				}
			}, keyHolder
		);
		
		return (long) keyHolder.getKey();
	}

	@Override
	public void update(Receipt receipt) {

		final String sql = "UPDATE receipt "
						+ "SET supplier_id = ?, trucker_id = ?, prepared_by = ?, checked_by = ?, noted_by = ?, date_checked = ?, date_noted = ? "
						+ "WHERE id = ?";
		
		jdbcTemplate.update(sql, receipt.getSupplier().getId(),
									receipt.getTrucker().getId(),									 
									receipt.getPreparedBy().getId(),
									receipt.getCheckedBy().getId(),
									receipt.getNotedBy().getId(),
									receipt.getDateChecked(),
									receipt.getDateNoted(),
									receipt.getId());
		
	}

	@Override
	public void delete(long id) {

		String sql = "DELETE FROM receipt WHERE id = ?";
		
		jdbcTemplate.update(sql, id);
	}

	@Override
	public Receipt findById(long id) {

		String sql = "SELECT r.id, r.date_created, r.date_checked, r.date_noted, "
						+ "s.id as sId, s.name as sName, s.email as sEmail, s.phone_mobile as sMobile, s.phone_landline as sLandline, s.address as sAddress, s.date_created as sDateCreated, "
						+ "t.id as tId, t.name as tName, t.email as tEmail, t.phone_mobile as tMobile, t.phone_landline as tLandline, t.address as tAddress, t.date_created as tDateCreated, "
						+ "w1.id as w1Id, w1.name as w1Name, w1.email as w1Email, w1.phone_mobile as w1Mobile, w1.phone_landline as w1Landline, w1.address as w1Address, w1.date_created as w1DateCreated, "
						+ "w2.id as w2Id, w2.name as w2Name, w2.email as w2Email, w2.phone_mobile as w2Mobile, w2.phone_landline as w2Landline, w2.address as w2Address, w2.date_created as w2DateCreated, "
						+ "w3.id as w3Id, w3.name as w3Name, w3.email as w3Email, w3.phone_mobile as w3Mobile, w3.phone_landline as w3Landline, w3.address as w3Address, w3.date_created as w3DateCreated "
					+ "FROM receipt r "
					+ "INNER JOIN supplier s on s.id = r.supplier_id "
					+ "LEFT JOIN trucker t on t.id = r.trucker_id "
					+ "LEFT JOIN worker w1 on w1.id = r.prepared_by "
					+ "LEFT JOIN worker w2 on w2.id = r.checked_by "
					+ "LEFT JOIN worker w3 on w3.id = r.noted_by "
					+ "WHERE r.id = ? "
					+ "ORDER BY r.id DESC";
		
		String sqlBody = "SELECT IFNULL(COUNT(*), 0) FROM roll WHERE part = 'Body' AND receipt_id = ?";
		
		String sqlCover = "SELECT IFNULL(COUNT(*), 0) FROM roll WHERE part = 'Cover' AND receipt_id = ?";
		
		String sqlPad = "SELECT IFNULL(COUNT(*), 0) FROM roll WHERE part = 'Pad' AND receipt_id = ?";
		
		String sqlWeight = "SELECT IFNULL(SUM(weight), 0) FROM roll WHERE receipt_id = ?";
		
		return jdbcTemplate.queryForObject(sql, new Object[]{id}, new RowMapper<Receipt>() {
			@Override
			public Receipt mapRow(ResultSet rs, int rowNum) throws SQLException {
				Supplier supplier = new Supplier();
				supplier.setId(rs.getLong("sId"));
				supplier.setName(rs.getString("sName"));
				supplier.setEmail(rs.getString("sEmail"));
				supplier.setPhoneMobile(rs.getString("sMobile"));
				supplier.setPhoneLandline(rs.getString("sLandline"));
				supplier.setAddress(rs.getString("sAddress"));
				supplier.setDateCreated(rs.getTimestamp("sDateCreated"));
				
				Trucker trucker = new Trucker();
				trucker.setId(rs.getLong("tId"));
				trucker.setName(rs.getString("tName"));
				trucker.setEmail(rs.getString("tEmail"));
				trucker.setPhoneMobile(rs.getString("tMobile"));
				trucker.setPhoneLandline(rs.getString("tLandline"));
				trucker.setAddress(rs.getString("tAddress"));
				trucker.setDateCreated(rs.getTimestamp("tDateCreated"));
				
				Worker preparedBy = new Worker();
				preparedBy.setId(rs.getLong("w1Id"));
				preparedBy.setName(rs.getString("w1Name"));
				preparedBy.setEmail(rs.getString("w1Email"));
				preparedBy.setPhoneMobile(rs.getString("w1Mobile"));
				preparedBy.setPhoneLandline(rs.getString("w1Landline"));
				preparedBy.setAddress(rs.getString("w1Address"));
				preparedBy.setDateCreated(rs.getTimestamp("w1DateCreated"));
				
				Worker checkedBy = new Worker();
				checkedBy.setId(rs.getLong("w2Id"));
				checkedBy.setName(rs.getString("w2Name"));
				checkedBy.setEmail(rs.getString("w2Email"));
				checkedBy.setPhoneMobile(rs.getString("w2Mobile"));
				checkedBy.setPhoneLandline(rs.getString("w2Landline"));
				checkedBy.setAddress(rs.getString("w2Address"));
				checkedBy.setDateCreated(rs.getTimestamp("w2DateCreated"));
				
				Worker notedBy = new Worker();
				notedBy.setId(rs.getLong("w3Id"));
				notedBy.setName(rs.getString("w3Name"));
				notedBy.setEmail(rs.getString("w3Email"));
				notedBy.setPhoneMobile(rs.getString("w3Mobile"));
				notedBy.setPhoneLandline(rs.getString("w3Landline"));
				notedBy.setAddress(rs.getString("w3Address"));
				notedBy.setDateCreated(rs.getTimestamp("w3DateCreated"));
				
				Receipt receipt = new Receipt();
				receipt.setId(rs.getLong("id"));				
				receipt.setSupplier(supplier);
				receipt.setTrucker(trucker);
				receipt.setPreparedBy(preparedBy);
				receipt.setCheckedBy(checkedBy);
				receipt.setNotedBy(notedBy);
				receipt.setDateCreated(rs.getTimestamp("date_created"));
				receipt.setDateChecked(rs.getTimestamp("date_checked"));
				receipt.setDateNoted(rs.getTimestamp("date_noted"));
				
				receipt.setBodyCount(jdbcTemplate.queryForObject(sqlBody, new Object[]{ rs.getLong("id") }, Double.class));
				receipt.setCoverCount(jdbcTemplate.queryForObject(sqlCover, new Object[]{ rs.getLong("id") }, Double.class));
				receipt.setPadCount(jdbcTemplate.queryForObject(sqlPad, new Object[]{ rs.getLong("id") }, Double.class));
				receipt.setTotalWeight(jdbcTemplate.queryForObject(sqlWeight, new Object[]{ rs.getLong("id") }, Double.class));
				
				return receipt;
			}
		});	
	}

	@Override
	public List<Receipt> findAll() {

		String sql = "SELECT r.id, r.date_created, r.date_checked, r.date_noted, "
						+ "s.id as sId, s.name as sName, s.email as sEmail, s.phone_mobile as sMobile, s.phone_landline as sLandline, s.address as sAddress, s.date_created as sDateCreated, "
						+ "t.id as tId, t.name as tName, t.email as tEmail, t.phone_mobile as tMobile, t.phone_landline as tLandline, t.address as tAddress, t.date_created as tDateCreated, "
						+ "w1.id as w1Id, w1.name as w1Name, w1.email as w1Email, w1.phone_mobile as w1Mobile, w1.phone_landline as w1Landline, w1.address as w1Address, w1.date_created as w1DateCreated, "
						+ "w2.id as w2Id, w2.name as w2Name, w2.email as w2Email, w2.phone_mobile as w2Mobile, w2.phone_landline as w2Landline, w2.address as w2Address, w2.date_created as w2DateCreated, "
						+ "w3.id as w3Id, w3.name as w3Name, w3.email as w3Email, w3.phone_mobile as w3Mobile, w3.phone_landline as w3Landline, w3.address as w3Address, w3.date_created as w3DateCreated "
					+ "FROM receipt r "
					+ "INNER JOIN supplier s on s.id = r.supplier_id "
					+ "LEFT JOIN trucker t on t.id = r.trucker_id "
					+ "LEFT JOIN worker w1 on w1.id = r.prepared_by "
					+ "LEFT JOIN worker w2 on w2.id = r.checked_by "
					+ "LEFT JOIN worker w3 on w3.id = r.noted_by "
					+ "ORDER BY r.id DESC";;
		
		String sqlBody = "SELECT IFNULL(COUNT(*), 0) FROM roll WHERE part = 'Body' AND receipt_id = ?";
		
		String sqlCover = "SELECT IFNULL(COUNT(*), 0) FROM roll WHERE part = 'Cover' AND receipt_id = ?";
		
		String sqlPad = "SELECT IFNULL(COUNT(*), 0) FROM roll WHERE part = 'Pad' AND receipt_id = ?";
		
		String sqlWeight = "SELECT IFNULL(SUM(weight), 0) FROM roll WHERE receipt_id = ?";
					
		List<Receipt> receipts = new ArrayList<Receipt>();
 		
		jdbcTemplate.query(sql, new RowMapper<Receipt>() {
			@Override
			public Receipt mapRow(ResultSet rs, int rowNum) throws SQLException {
				Supplier supplier = new Supplier();
				supplier.setId(rs.getLong("sId"));
				supplier.setName(rs.getString("sName"));
				supplier.setEmail(rs.getString("sEmail"));
				supplier.setPhoneMobile(rs.getString("sMobile"));
				supplier.setPhoneLandline(rs.getString("sLandline"));
				supplier.setAddress(rs.getString("sAddress"));
				supplier.setDateCreated(rs.getTimestamp("sDateCreated"));
				
				Trucker trucker = new Trucker();
				trucker.setId(rs.getLong("tId"));
				trucker.setName(rs.getString("tName"));
				trucker.setEmail(rs.getString("tEmail"));
				trucker.setPhoneMobile(rs.getString("tMobile"));
				trucker.setPhoneLandline(rs.getString("tLandline"));
				trucker.setAddress(rs.getString("tAddress"));
				trucker.setDateCreated(rs.getTimestamp("tDateCreated"));
				
				Worker preparedBy = new Worker();
				preparedBy.setId(rs.getLong("w1Id"));
				preparedBy.setName(rs.getString("w1Name"));
				preparedBy.setEmail(rs.getString("w1Email"));
				preparedBy.setPhoneMobile(rs.getString("w1Mobile"));
				preparedBy.setPhoneLandline(rs.getString("w1Landline"));
				preparedBy.setAddress(rs.getString("w1Address"));
				preparedBy.setDateCreated(rs.getTimestamp("w1DateCreated"));
				
				Worker checkedBy = new Worker();
				checkedBy.setId(rs.getLong("w2Id"));
				checkedBy.setName(rs.getString("w2Name"));
				checkedBy.setEmail(rs.getString("w2Email"));
				checkedBy.setPhoneMobile(rs.getString("w2Mobile"));
				checkedBy.setPhoneLandline(rs.getString("w2Landline"));
				checkedBy.setAddress(rs.getString("w2Address"));
				checkedBy.setDateCreated(rs.getTimestamp("w2DateCreated"));
				
				Worker notedBy = new Worker();
				notedBy.setId(rs.getLong("w3Id"));
				notedBy.setName(rs.getString("w3Name"));
				notedBy.setEmail(rs.getString("w3Email"));
				notedBy.setPhoneMobile(rs.getString("w3Mobile"));
				notedBy.setPhoneLandline(rs.getString("w3Landline"));
				notedBy.setAddress(rs.getString("w3Address"));
				notedBy.setDateCreated(rs.getTimestamp("w3DateCreated"));
				
				Receipt receipt = new Receipt();
				receipt.setId(rs.getLong("id"));
				receipt.setSupplier(supplier);
				receipt.setTrucker(trucker);
				receipt.setPreparedBy(preparedBy);
				receipt.setCheckedBy(checkedBy);
				receipt.setNotedBy(notedBy);
				receipt.setDateCreated(rs.getTimestamp("date_created"));
				receipt.setDateChecked(rs.getTimestamp("date_checked"));
				receipt.setDateNoted(rs.getTimestamp("date_noted"));
				
				receipt.setBodyCount(jdbcTemplate.queryForObject(sqlBody, new Object[]{ rs.getLong("id") }, Double.class));
				receipt.setCoverCount(jdbcTemplate.queryForObject(sqlCover, new Object[]{ rs.getLong("id") }, Double.class));
				receipt.setPadCount(jdbcTemplate.queryForObject(sqlPad, new Object[]{ rs.getLong("id") }, Double.class));
				receipt.setTotalWeight(jdbcTemplate.queryForObject(sqlWeight, new Object[]{ rs.getLong("id") }, Double.class));
				receipt.setRollCount(receipt.getBodyCount() + receipt.getCoverCount() + receipt.getPadCount());
				
				receipts.add(receipt);
				
				return receipt;
			}
		});	
		
		return receipts;
	}
}
