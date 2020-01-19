package com.app.pojos;

import javax.persistence.*;

@Entity
@Table(name = "dining_table")
public class DiningTable {
	private Integer tableId;
	private String tableStatus;
	public DiningTable() {
		// TODO Auto-generated constructor stub
	}
	public DiningTable(String tableStatus) {
		this.tableStatus = tableStatus;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "table_id")
	public Integer getTableId() {
		return tableId;
	}
	public void setTableId(Integer tableId) {
		this.tableId = tableId;
	}
	@Column(name = "table_status",length = 20)
	public String getTableStatus() {
		return tableStatus;
	}
	public void setTableStatus(String tableStatus) {
		this.tableStatus = tableStatus;
	}
	@Override
	public String toString() {
		return "DiningTable [tableId=" + tableId + ", tableStatus=" + tableStatus + "]";
	}
	
}
