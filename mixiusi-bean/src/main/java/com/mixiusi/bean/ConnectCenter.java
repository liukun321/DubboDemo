package com.mixiusi.bean;

import java.io.Serializable;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
@Entity
@Table(name = "connect_center")
public class ConnectCenter implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2459171403643103284L;
	@Id
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menuSeq")  
//	@SequenceGenerator(name = "centerSeq", initialValue = 120001, allocationSize = 1, sequenceName = "MENU_SEQUENCE")
	private Integer id;
	/**
	 * ？？
	 */
	@NotNull
	private String name;
	/**
	 * 团队名称
	 */
	@NotNull
	private String team;
	/**
	 * 联系电话
	 */
	@NotNull
	private String phone;
	public Integer getId() {
		return id;
	}
	public String getTeam() {
		return team;
	}
	public String getPhone() {
		return phone;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((team == null) ? 0 : team.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConnectCenter other = (ConnectCenter) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (team == null) {
			if (other.team != null)
				return false;
		} else if (!team.equals(other.team))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ConnectCenter [id=" + id + ", name=" + name + ", team=" + team + ", phone=" + phone + "]";
	}
	
}
