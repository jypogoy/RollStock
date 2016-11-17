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

import com.fibre.rollstock.common.RollPart;
import com.fibre.rollstock.model.Part;
import com.fibre.rollstock.model.Receipt;
import com.fibre.rollstock.model.Roll;
import com.fibre.rollstock.model.RollDescription;
import com.fibre.rollstock.model.Storage;
import com.fibre.rollstock.model.Supplier;
import com.fibre.rollstock.model.Ticket;
import com.fibre.rollstock.model.Trucker;
import com.fibre.rollstock.model.Worker;
import com.mysql.jdbc.Statement;

@Repository
@Transactional(readOnly=false)
public class RollDAOImpl extends JdbcDaoSupport implements RollDAO {

	protected static final Logger LOGGER = LoggerFactory.getLogger(RollDAOImpl.class);
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private DataSource dataSource;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
		jdbcTemplate = getJdbcTemplate();
	}

	@Override
	public long create(Roll roll) {
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		final String sql = "INSERT INTO roll(part, number, description_id, grade, sized, weight, lineal, remarks, ticket_id, receipt_id) "
							+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement ps = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, roll.getPart().getName());
					ps.setString(2, roll.getNumber());
					ps.setLong(3, roll.getDescription().getId());
					ps.setDouble(4, roll.getGrade());
					ps.setDouble(5, roll.getSized());
					ps.setDouble(6, roll.getWeight());
					ps.setDouble(7, roll.getLineal());
					ps.setString(8, roll.getRemarks());
					ps.setLong(9, roll.getTicket().getId());
					ps.setLong(10, roll.getReceipt().getId());
					
					return ps;
				}
			}, keyHolder
		);
		
		return (long) keyHolder.getKey();
	}

	@Override
	public void update(Roll roll) {
		
		final String sql = "UPDATE roll "
						+ "SET part = ?, number = ?, description_id = ?, grade = ?, sized = ?, weight = ?, lineal = ?, remarks = ?, ticket_id = ?, receipt_id = ? "
						+ "WHERE id = ?";
		
		jdbcTemplate.update(sql, roll.getPart().getName(),
									roll.getNumber(), 
									roll.getDescription().getId(),
									roll.getGrade(), 
									roll.getSized(), 
									roll.getWeight(),
									roll.getLineal(),
									roll.getRemarks(),
									roll.getTicket().getId(),
									roll.getReceipt().getId(),
									roll.getId());
	}

	@Override
	public void delete(long id) {
		
		String sql = "DELETE FROM roll WHERE id = ?";
		
		jdbcTemplate.update(sql, id);
	}

	@Override
	public List<Roll> findAll() {
		
		String sql = "SELECT *, "
						+ " rd.id as descId, rd.text as descText, rd.details as descDetails, rd.date_created as descDateCreated, "
						+ " wp.id as pId, wp.name as pName, wp.email as pEmail, wp.phone_mobile as pMobile, wp.phone_landline as pLandline, wp.address as pAddress, wp.date_created as pDateCreated, "
						+ " s.id as sId, s.name as sName, s.email as sEmail, s.phone_mobile as sMobile, s.phone_landline as sLandline, s.address as sAddress, s.date_created as sDateCreated, "
						+ " tr.id as trId, tr.name as trName, tr.email as trEmail, tr.phone_mobile as trMobile, tr.phone_landline as trLandline, tr.address as trAddress, tr.date_created as trDateCreated, "
						+ " wp.id as pId, wp.name as pName, wp.email as pEmail, wp.phone_mobile as pMobile, wp.phone_landline as pLandline, wp.address as pAddress, wp.date_created as pDateCreated, "
						+ " wc.id as cId, wc.name as cName, wc.email as cEmail, wc.phone_mobile as cMobile, wc.phone_landline as cLandline, wc.address as cAddress, wc.date_created as cDateCreated, "
						+ " wn.id as nId, wn.name as nName, wn.email as nEmail, wn.phone_mobile as nMobile, wn.phone_landline as nLandline, wn.address as nAddress, wn.date_created as nDateCreated "
					+ " FROM roll r "
					+ " INNER JOIN roll_description rd on rd.id = r.description_id "
					+ " INNER JOIN ticket t on t.id = r.ticket_id "
					+ " INNER JOIN receipt rc on rc.id = r.receipt_id "
					+ " INNER JOIN supplier s on s.id = rc.supplier_id "
					+ " LEFT JOIN trucker tr on tr.id = rc.trucker_id "
					+ " LEFT JOIN worker wp on wp.id = rc.prepared_by "
					+ " LEFT JOIN worker wc on wc.id = rc.checked_by "
					+ " LEFT JOIN worker wn on wn.id = rc.noted_by";
		
		List<Roll> rolls = new ArrayList<Roll>();
				
		jdbcTemplate.query(sql, new RowMapper<Roll>() {
			@Override
			public Roll mapRow(ResultSet rs, int rowNum) throws SQLException {
				Ticket ticket = new Ticket();
				ticket.setId(rs.getLong("ticket_id"));
				ticket.setDateCreated(rs.getTimestamp("date_issued"));
				
				Receipt receipt = new Receipt();
				receipt.setId(rs.getLong("receipt_id"));
				
					Supplier supplier = new Supplier();
					supplier.setId(rs.getLong("sId"));
					supplier.setName(rs.getString("sName"));
					supplier.setEmail(rs.getString("sEmail"));
					supplier.setPhoneMobile(rs.getString("sMobile"));
					supplier.setPhoneLandline(rs.getString("sLandline"));
					supplier.setAddress(rs.getString("sAddress"));
					supplier.setDateCreated(rs.getTimestamp("sDateCreated"));
					
				receipt.setSupplier(supplier);
				
					Trucker trucker = new Trucker();
					trucker.setId(rs.getLong("trId"));
					trucker.setName(rs.getString("trName"));
					trucker.setEmail(rs.getString("trEmail"));
					trucker.setPhoneMobile(rs.getString("trMobile"));
					trucker.setPhoneLandline(rs.getString("trLandline"));
					trucker.setAddress(rs.getString("trAddress"));
					trucker.setDateCreated(rs.getTimestamp("trDateCreated"));
				
				receipt.setTrucker(trucker);	
					
					Worker preparedBy = new Worker();
					preparedBy.setId(rs.getLong("pId"));
					preparedBy.setName(rs.getString("pName"));
					preparedBy.setEmail(rs.getString("pEmail"));
					preparedBy.setPhoneMobile(rs.getString("pMobile"));
					preparedBy.setPhoneLandline(rs.getString("pLandline"));
					preparedBy.setAddress(rs.getString("pAddress"));
					preparedBy.setDateCreated(rs.getTimestamp("pDateCreated"));
				
				receipt.setPreparedBy(preparedBy);	
					
					Worker checkedBy = new Worker();
					checkedBy.setId(rs.getLong("cId"));
					checkedBy.setName(rs.getString("cName"));
					checkedBy.setEmail(rs.getString("cEmail"));
					checkedBy.setPhoneMobile(rs.getString("cMobile"));
					checkedBy.setPhoneLandline(rs.getString("cLandline"));
					checkedBy.setAddress(rs.getString("cAddress"));
					checkedBy.setDateCreated(rs.getTimestamp("cDateCreated"));
			
				receipt.setCheckedBy(checkedBy);
				
					Worker notedBy = new Worker();
					notedBy.setId(rs.getLong("nId"));
					notedBy.setName(rs.getString("nName"));
					notedBy.setEmail(rs.getString("nEmail"));
					notedBy.setPhoneMobile(rs.getString("nMobile"));
					notedBy.setPhoneLandline(rs.getString("nLandline"));
					notedBy.setAddress(rs.getString("nAddress"));
					notedBy.setDateCreated(rs.getTimestamp("nDateCreated"));
			
				receipt.setNotedBy(notedBy);
				receipt.setDateCreated(rs.getTimestamp("date_created"));
				receipt.setDateChecked(rs.getTimestamp("date_checked"));
				receipt.setDateNoted(rs.getTimestamp("date_noted"));
				
				Roll roll = new Roll();
				roll.setId(rs.getLong("id"));		
				
					Part part = new Part();
					part.setId(RollPart.valueOf(rs.getString("part")).getId());
					part.setName(RollPart.valueOf(rs.getString("part")).name());
				
				roll.setPart(part);
				roll.setNumber(rs.getString("number"));
				
					RollDescription description = new RollDescription();
					description.setId(rs.getLong("descId"));
					description.setText(rs.getString("descText"));
					description.setDetails(rs.getString("descDetails"));
					description.setDateCreated(rs.getTimestamp("descDateCreated"));
				
				roll.setDescription(description);
				roll.setGrade(rs.getDouble("grade"));
				roll.setSized(rs.getDouble("sized"));
				roll.setWeight(rs.getDouble("weight"));
				roll.setLineal(rs.getDouble("lineal"));
				roll.setRemarks(rs.getString("remarks"));;
				roll.setDateCreated(rs.getTimestamp("date_created"));
				roll.setTicket(ticket);
				roll.setReceipt(receipt);
				
				rolls.add(roll);
				
				return roll;
			}
		});	
		
		return rolls;
	}

	@Override
	public List<Roll> findByReceipt(long receiptId) {
		
		String sql = "SELECT *, "
						+ " rd.id as descId, rd.text as descText, rd.details as descDetails, rd.date_created as descDateCreated, "
						+ " wp.id as pId, wp.name as pName, wp.email as pEmail, wp.phone_mobile as pMobile, wp.phone_landline as pLandline, wp.address as pAddress, wp.date_created as pDateCreated, "
						+ " s.id as sId, s.name as sName, s.email as sEmail, s.phone_mobile as sMobile, s.phone_landline as sLandline, s.address as sAddress, s.date_created as sDateCreated, "
						+ " tr.id as trId, tr.name as trName, tr.email as trEmail, tr.phone_mobile as trMobile, tr.phone_landline as trLandline, tr.address as trAddress, tr.date_created as trDateCreated, "
						+ " wp.id as pId, wp.name as pName, wp.email as pEmail, wp.phone_mobile as pMobile, wp.phone_landline as pLandline, wp.address as pAddress, wp.date_created as pDateCreated, "
						+ " wc.id as cId, wc.name as cName, wc.email as cEmail, wc.phone_mobile as cMobile, wc.phone_landline as cLandline, wc.address as cAddress, wc.date_created as cDateCreated, "
						+ " wn.id as nId, wn.name as nName, wn.email as nEmail, wn.phone_mobile as nMobile, wn.phone_landline as nLandline, wn.address as nAddress, wn.date_created as nDateCreated "
					+ " FROM roll r "
					+ " INNER JOIN roll_description rd on rd.id = r.description_id "
					+ " INNER JOIN ticket t on t.id = r.ticket_id "
					+ " INNER JOIN receipt rc on rc.id = r.receipt_id "
					+ " INNER JOIN supplier s on s.id = rc.supplier_id "
					+ " LEFT JOIN trucker tr on tr.id = rc.trucker_id "
					+ " LEFT JOIN worker wp on wp.id = rc.prepared_by "
					+ " LEFT JOIN worker wc on wc.id = rc.checked_by "
					+ " LEFT JOIN worker wn on wn.id = rc.noted_by"
					+ " WHERE r.receipt_id = ?";
		
		List<Roll> rolls = new ArrayList<Roll>();
				
		jdbcTemplate.query(sql, new Object[]{ receiptId },  new RowMapper<Roll>() {
			@Override
			public Roll mapRow(ResultSet rs, int rowNum) throws SQLException {
				Ticket ticket = new Ticket();
				ticket.setId(rs.getLong("ticket_id"));
				ticket.setDateCreated(rs.getTimestamp("date_issued"));
				
				Receipt receipt = new Receipt();
				receipt.setId(rs.getLong("receipt_id"));
				
					Supplier supplier = new Supplier();
					supplier.setId(rs.getLong("sId"));
					supplier.setName(rs.getString("sName"));
					supplier.setEmail(rs.getString("sEmail"));
					supplier.setPhoneMobile(rs.getString("sMobile"));
					supplier.setPhoneLandline(rs.getString("sLandline"));
					supplier.setAddress(rs.getString("sAddress"));
					supplier.setDateCreated(rs.getTimestamp("sDateCreated"));
					
				receipt.setSupplier(supplier);
				
					Trucker trucker = new Trucker();
					trucker.setId(rs.getLong("trId"));
					trucker.setName(rs.getString("trName"));
					trucker.setEmail(rs.getString("trEmail"));
					trucker.setPhoneMobile(rs.getString("trMobile"));
					trucker.setPhoneLandline(rs.getString("trLandline"));
					trucker.setAddress(rs.getString("trAddress"));
					trucker.setDateCreated(rs.getTimestamp("trDateCreated"));
				
				receipt.setTrucker(trucker);	
					Worker preparedBy = new Worker();
					preparedBy.setId(rs.getLong("pId"));
					preparedBy.setName(rs.getString("pName"));
					preparedBy.setEmail(rs.getString("pEmail"));
					preparedBy.setPhoneMobile(rs.getString("pMobile"));
					preparedBy.setPhoneLandline(rs.getString("pLandline"));
					preparedBy.setAddress(rs.getString("pAddress"));
					preparedBy.setDateCreated(rs.getTimestamp("pDateCreated"));
				
				receipt.setPreparedBy(preparedBy);	
					
					Worker checkedBy = new Worker();
					checkedBy.setId(rs.getLong("cId"));
					checkedBy.setName(rs.getString("cName"));
					checkedBy.setEmail(rs.getString("cEmail"));
					checkedBy.setPhoneMobile(rs.getString("cMobile"));
					checkedBy.setPhoneLandline(rs.getString("cLandline"));
					checkedBy.setAddress(rs.getString("cAddress"));
					checkedBy.setDateCreated(rs.getTimestamp("cDateCreated"));
			
				receipt.setCheckedBy(checkedBy);
				
					Worker notedBy = new Worker();
					notedBy.setId(rs.getLong("nId"));
					notedBy.setName(rs.getString("nName"));
					notedBy.setEmail(rs.getString("nEmail"));
					notedBy.setPhoneMobile(rs.getString("nMobile"));
					notedBy.setPhoneLandline(rs.getString("nLandline"));
					notedBy.setAddress(rs.getString("nAddress"));
					notedBy.setDateCreated(rs.getTimestamp("nDateCreated"));
			
				receipt.setNotedBy(notedBy);
				receipt.setDateCreated(rs.getTimestamp("date_created"));
				receipt.setDateChecked(rs.getTimestamp("date_checked"));
				receipt.setDateNoted(rs.getTimestamp("date_noted"));
				
				Roll roll = new Roll();
				roll.setId(rs.getLong("id"));		
				
					Part part = new Part();
					part.setId(RollPart.valueOf(rs.getString("part")).getId());
					part.setName(RollPart.valueOf(rs.getString("part")).name());
				
				roll.setPart(part);
				roll.setNumber(rs.getString("number"));

					RollDescription description = new RollDescription();
					description.setId(rs.getLong("descId"));
					description.setText(rs.getString("descText"));
					description.setDetails(rs.getString("descDetails"));
					description.setDateCreated(rs.getTimestamp("descDateCreated"));
			
				roll.setDescription(description);
				roll.setGrade(rs.getDouble("grade"));
				roll.setSized(rs.getDouble("sized"));
				roll.setWeight(rs.getDouble("weight"));
				roll.setLineal(rs.getDouble("lineal"));
				roll.setRemarks(rs.getString("remarks"));;
				roll.setDateCreated(rs.getTimestamp("date_created"));
				roll.setTicket(ticket);
				roll.setReceipt(receipt);
				
				rolls.add(roll);
				
				return roll;
			}
		});	
		
		return rolls;
	}

	@Override
	public void createMultiple(List<Roll> rolls) {
		
		final String sql = "INSERT INTO roll(part, number, description_id, grade, sized, weight, lineal, remarks, ticket_id, receipt_id) "
						+ "VALUES";
		
		StringBuffer buffer = new StringBuffer();
		buffer.append(sql);
		
		int counter = 0;
		for (Roll roll : rolls) {
			buffer.append("(" + roll.getPart().getName() + "," + roll.getNumber() + ", "
					+ roll.getDescription().getId() + "," + roll.getGrade() + ", "
					+ roll.getSized() + "," + roll.getWeight() + "," + roll.getLineal() + ", "
					+ roll.getRemarks() + "," + roll.getTicket().getId() + "," + roll.getReceipt().getId() + ")");
			if(counter < rolls.size()) {
				buffer.append(",");
			}
			counter++;
		}
		
		jdbcTemplate.update(buffer.toString());
		
	}

	@Override
	public void deleteByReceipt(long receiptId) {
		
		String sql = "DELETE FROM roll WHERE receipt_id = ?";
		
		jdbcTemplate.update(sql, receiptId);
	}

	@Override
	public List<Roll> findForStorage() {
		
		String sql = "SELECT *, "
						+ " rd.id as descId, rd.text as descText, rd.details as descDetails, rd.date_created as descDateCreated, "
						+ " wp.id as pId, wp.name as pName, wp.email as pEmail, wp.phone_mobile as pMobile, wp.phone_landline as pLandline, wp.address as pAddress, wp.date_created as pDateCreated, "
						+ " s.id as sId, s.name as sName, s.email as sEmail, s.phone_mobile as sMobile, s.phone_landline as sLandline, s.address as sAddress, s.date_created as sDateCreated, "
						+ " tr.id as trId, tr.name as trName, tr.email as trEmail, tr.phone_mobile as trMobile, tr.phone_landline as trLandline, tr.address as trAddress, tr.date_created as trDateCreated, "
						+ " wp.id as pId, wp.name as pName, wp.email as pEmail, wp.phone_mobile as pMobile, wp.phone_landline as pLandline, wp.address as pAddress, wp.date_created as pDateCreated, "
						+ " wc.id as cId, wc.name as cName, wc.email as cEmail, wc.phone_mobile as cMobile, wc.phone_landline as cLandline, wc.address as cAddress, wc.date_created as cDateCreated, "
						+ " wn.id as nId, wn.name as nName, wn.email as nEmail, wn.phone_mobile as nMobile, wn.phone_landline as nLandline, wn.address as nAddress, wn.date_created as nDateCreated "
					+ "FROM roll r "
					+ "INNER JOIN roll_description rd on rd.id = r.description_id "
					+ "INNER JOIN ticket t on t.id = r.ticket_id "
					+ "INNER JOIN receipt rc on rc.id = r.receipt_id "
					+ "INNER JOIN supplier s on s.id = rc.supplier_id "
					+ "AND NOT EXISTS (" 
						+ "SELECT 1 FROM `storage` WHERE roll_id = r.id"
					+ ")"
					+ "LEFT JOIN trucker tr on tr.id = rc.trucker_id "
					+ "LEFT JOIN worker wp on wp.id = rc.prepared_by "
					+ "LEFT JOIN worker wc on wc.id = rc.checked_by "
					+ "LEFT JOIN worker wn on wn.id = rc.noted_by ";
		
		List<Roll> rolls = new ArrayList<Roll>();
				
		jdbcTemplate.query(sql, new RowMapper<Roll>() {
			@Override
			public Roll mapRow(ResultSet rs, int rowNum) throws SQLException {
				Ticket ticket = new Ticket();
				ticket.setId(rs.getLong("ticket_id"));
				ticket.setDateCreated(rs.getTimestamp("date_issued"));
				
				Receipt receipt = new Receipt();
				receipt.setId(rs.getLong("receipt_id"));
				
					Supplier supplier = new Supplier();
					supplier.setId(rs.getLong("sId"));
					supplier.setName(rs.getString("sName"));
					supplier.setEmail(rs.getString("sEmail"));
					supplier.setPhoneMobile(rs.getString("sMobile"));
					supplier.setPhoneLandline(rs.getString("sLandline"));
					supplier.setAddress(rs.getString("sAddress"));
					supplier.setDateCreated(rs.getTimestamp("sDateCreated"));
					
				receipt.setSupplier(supplier);
				
					Trucker trucker = new Trucker();
					trucker.setId(rs.getLong("trId"));
					trucker.setName(rs.getString("trName"));
					trucker.setEmail(rs.getString("trEmail"));
					trucker.setPhoneMobile(rs.getString("trMobile"));
					trucker.setPhoneLandline(rs.getString("trLandline"));
					trucker.setAddress(rs.getString("trAddress"));
					trucker.setDateCreated(rs.getTimestamp("trDateCreated"));
				
				receipt.setTrucker(trucker);	
					
					Worker preparedBy = new Worker();
					preparedBy.setId(rs.getLong("pId"));
					preparedBy.setName(rs.getString("pName"));
					preparedBy.setEmail(rs.getString("pEmail"));
					preparedBy.setPhoneMobile(rs.getString("pMobile"));
					preparedBy.setPhoneLandline(rs.getString("pLandline"));
					preparedBy.setAddress(rs.getString("pAddress"));
					preparedBy.setDateCreated(rs.getTimestamp("pDateCreated"));
				
				receipt.setPreparedBy(preparedBy);	
					
					Worker checkedBy = new Worker();
					checkedBy.setId(rs.getLong("cId"));
					checkedBy.setName(rs.getString("cName"));
					checkedBy.setEmail(rs.getString("cEmail"));
					checkedBy.setPhoneMobile(rs.getString("cMobile"));
					checkedBy.setPhoneLandline(rs.getString("cLandline"));
					checkedBy.setAddress(rs.getString("cAddress"));
					checkedBy.setDateCreated(rs.getTimestamp("cDateCreated"));
			
				receipt.setCheckedBy(checkedBy);
				
					Worker notedBy = new Worker();
					notedBy.setId(rs.getLong("nId"));
					notedBy.setName(rs.getString("nName"));
					notedBy.setEmail(rs.getString("nEmail"));
					notedBy.setPhoneMobile(rs.getString("nMobile"));
					notedBy.setPhoneLandline(rs.getString("nLandline"));
					notedBy.setAddress(rs.getString("nAddress"));
					notedBy.setDateCreated(rs.getTimestamp("nDateCreated"));
			
				receipt.setNotedBy(notedBy);
				receipt.setDateCreated(rs.getTimestamp("date_created"));
				receipt.setDateChecked(rs.getTimestamp("date_checked"));
				receipt.setDateNoted(rs.getTimestamp("date_noted"));
				
				Roll roll = new Roll();
				roll.setId(rs.getLong("id"));		
				
					Part part = new Part();
					part.setId(RollPart.valueOf(rs.getString("part")).getId());
					part.setName(RollPart.valueOf(rs.getString("part")).name());
				
				roll.setPart(part);
				roll.setNumber(rs.getString("number"));
				
					RollDescription description = new RollDescription();
					description.setId(rs.getLong("descId"));
					description.setText(rs.getString("descText"));
					description.setDetails(rs.getString("descDetails"));
					description.setDateCreated(rs.getTimestamp("descDateCreated"));
				
				roll.setDescription(description);
				roll.setGrade(rs.getDouble("grade"));
				roll.setSized(rs.getDouble("sized"));
				roll.setWeight(rs.getDouble("weight"));
				roll.setLineal(rs.getDouble("lineal"));
				roll.setRemarks(rs.getString("remarks"));;
				roll.setDateCreated(rs.getTimestamp("date_created"));
				roll.setTicket(ticket);
				roll.setReceipt(receipt);
				
				rolls.add(roll);
				
				return roll;
			}
		});	
		
		return rolls;
	}

	@Override
	public List<Roll> findByBin(long binId) {
		String sql = "SELECT *, "
						+ " rd.id as descId, rd.text as descText, rd.details as descDetails, rd.date_created as descDateCreated, "
						+ " wp.id as pId, wp.name as pName, wp.email as pEmail, wp.phone_mobile as pMobile, wp.phone_landline as pLandline, wp.address as pAddress, wp.date_created as pDateCreated, "
						+ " s.id as sId, s.name as sName, s.email as sEmail, s.phone_mobile as sMobile, s.phone_landline as sLandline, s.address as sAddress, s.date_created as sDateCreated, "
						+ " tr.id as trId, tr.name as trName, tr.email as trEmail, tr.phone_mobile as trMobile, tr.phone_landline as trLandline, tr.address as trAddress, tr.date_created as trDateCreated, "
						+ " wp.id as pId, wp.name as pName, wp.email as pEmail, wp.phone_mobile as pMobile, wp.phone_landline as pLandline, wp.address as pAddress, wp.date_created as pDateCreated, "
						+ " wc.id as cId, wc.name as cName, wc.email as cEmail, wc.phone_mobile as cMobile, wc.phone_landline as cLandline, wc.address as cAddress, wc.date_created as cDateCreated, "
						+ " wn.id as nId, wn.name as nName, wn.email as nEmail, wn.phone_mobile as nMobile, wn.phone_landline as nLandline, wn.address as nAddress, wn.date_created as nDateCreated "
					+ "FROM roll r "
					+ "INNER JOIN roll_description rd on rd.id = r.description_id "
					+ "INNER JOIN ticket t on t.id = r.ticket_id "
					+ "INNER JOIN receipt rc on rc.id = r.receipt_id "
					+ "INNER JOIN supplier s on s.id = rc.supplier_id "
					+ "INNER JOIN `storage` st on st.roll_id = r.id "					
					+ "LEFT JOIN trucker tr on tr.id = rc.trucker_id "
					+ "LEFT JOIN worker wp on wp.id = rc.prepared_by "
					+ "LEFT JOIN worker wc on wc.id = rc.checked_by "
					+ "LEFT JOIN worker wn on wn.id = rc.noted_by "
					+ "WHERE st.bin_id = ?";
		
		List<Roll> rolls = new ArrayList<Roll>();
				
		jdbcTemplate.query(sql, new Object[]{ binId }, new RowMapper<Roll>() {
			@Override
			public Roll mapRow(ResultSet rs, int rowNum) throws SQLException {
				Ticket ticket = new Ticket();
				ticket.setId(rs.getLong("ticket_id"));
				ticket.setDateCreated(rs.getTimestamp("date_issued"));
				
				Receipt receipt = new Receipt();
				receipt.setId(rs.getLong("receipt_id"));
				
					Supplier supplier = new Supplier();
					supplier.setId(rs.getLong("sId"));
					supplier.setName(rs.getString("sName"));
					supplier.setEmail(rs.getString("sEmail"));
					supplier.setPhoneMobile(rs.getString("sMobile"));
					supplier.setPhoneLandline(rs.getString("sLandline"));
					supplier.setAddress(rs.getString("sAddress"));
					supplier.setDateCreated(rs.getTimestamp("sDateCreated"));
					
				receipt.setSupplier(supplier);
				
					Trucker trucker = new Trucker();
					trucker.setId(rs.getLong("trId"));
					trucker.setName(rs.getString("trName"));
					trucker.setEmail(rs.getString("trEmail"));
					trucker.setPhoneMobile(rs.getString("trMobile"));
					trucker.setPhoneLandline(rs.getString("trLandline"));
					trucker.setAddress(rs.getString("trAddress"));
					trucker.setDateCreated(rs.getTimestamp("trDateCreated"));
				
				receipt.setTrucker(trucker);	
					
					Worker preparedBy = new Worker();
					preparedBy.setId(rs.getLong("pId"));
					preparedBy.setName(rs.getString("pName"));
					preparedBy.setEmail(rs.getString("pEmail"));
					preparedBy.setPhoneMobile(rs.getString("pMobile"));
					preparedBy.setPhoneLandline(rs.getString("pLandline"));
					preparedBy.setAddress(rs.getString("pAddress"));
					preparedBy.setDateCreated(rs.getTimestamp("pDateCreated"));
				
				receipt.setPreparedBy(preparedBy);	
					
					Worker checkedBy = new Worker();
					checkedBy.setId(rs.getLong("cId"));
					checkedBy.setName(rs.getString("cName"));
					checkedBy.setEmail(rs.getString("cEmail"));
					checkedBy.setPhoneMobile(rs.getString("cMobile"));
					checkedBy.setPhoneLandline(rs.getString("cLandline"));
					checkedBy.setAddress(rs.getString("cAddress"));
					checkedBy.setDateCreated(rs.getTimestamp("cDateCreated"));
			
				receipt.setCheckedBy(checkedBy);
				
					Worker notedBy = new Worker();
					notedBy.setId(rs.getLong("nId"));
					notedBy.setName(rs.getString("nName"));
					notedBy.setEmail(rs.getString("nEmail"));
					notedBy.setPhoneMobile(rs.getString("nMobile"));
					notedBy.setPhoneLandline(rs.getString("nLandline"));
					notedBy.setAddress(rs.getString("nAddress"));
					notedBy.setDateCreated(rs.getTimestamp("nDateCreated"));
			
				receipt.setNotedBy(notedBy);
				receipt.setDateCreated(rs.getTimestamp("date_created"));
				receipt.setDateChecked(rs.getTimestamp("date_checked"));
				receipt.setDateNoted(rs.getTimestamp("date_noted"));
				
				Roll roll = new Roll();
				roll.setId(rs.getLong("id"));		
				
					Part part = new Part();
					part.setId(RollPart.valueOf(rs.getString("part")).getId());
					part.setName(RollPart.valueOf(rs.getString("part")).name());
				
				roll.setPart(part);
				roll.setNumber(rs.getString("number"));
				
					RollDescription description = new RollDescription();
					description.setId(rs.getLong("descId"));
					description.setText(rs.getString("descText"));
					description.setDetails(rs.getString("descDetails"));
					description.setDateCreated(rs.getTimestamp("descDateCreated"));
				
				roll.setDescription(description);
				roll.setGrade(rs.getDouble("grade"));
				roll.setSized(rs.getDouble("sized"));
				roll.setWeight(rs.getDouble("weight"));
				roll.setLineal(rs.getDouble("lineal"));
				roll.setRemarks(rs.getString("remarks"));;
				roll.setDateCreated(rs.getTimestamp("date_created"));
				roll.setTicket(ticket);
				roll.setReceipt(receipt);
				
					Storage storage = new Storage();
					storage.setId(rs.getLong("st.id"));
					storage.setCheckInDate(rs.getTimestamp("st.check_in_date"));											
					
					//TODO Add more fields here...
				roll.setStorage(storage);
					
				rolls.add(roll);
				
				return roll;
			}
		});	
		
		return rolls;
	}		
}
