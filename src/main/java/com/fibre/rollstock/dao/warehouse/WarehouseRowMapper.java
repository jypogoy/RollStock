package com.fibre.rollstock.dao.warehouse;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.fibre.rollstock.model.Warehouse;

public class WarehouseRowMapper implements RowMapper<Warehouse> {

	@Override
	public Warehouse mapRow(ResultSet rs, int rowNum) throws SQLException {
		Warehouse warehouse = new Warehouse();
		warehouse.setId(rs.getLong("id"));
		warehouse.setName(rs.getString("name"));
		warehouse.setDescription(rs.getString("description"));
		warehouse.setAddress(rs.getString("address"));
		warehouse.setDateCreated(rs.getTimestamp("date_created"));
		return warehouse;
	}

}
