package cn.com.reey.VMSweb.entity;

// Generated 2016-5-5 16:53:14 by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Driver generated by hbm2java
 */
@Entity
@Table(name = "driver", catalog = "vms")
public class Driver implements java.io.Serializable {

	private Integer driverid;
	private String drivername;
	private String drivernote;
	@JSONField(serialize = false)
	private Set<Application> applications = new HashSet<Application>(0);

	public Driver() {
	}

	public Driver(String drivername, String drivernote,
			Set<Application> applications) {
		this.drivername = drivername;
		this.drivernote = drivernote;
		this.applications = applications;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "driverid", unique = true, nullable = false)
	public Integer getDriverid() {
		return this.driverid;
	}

	public void setDriverid(Integer driverid) {
		this.driverid = driverid;
	}

	@Column(name = "drivername", length = 32)
	public String getDrivername() {
		return this.drivername;
	}

	public void setDrivername(String drivername) {
		this.drivername = drivername;
	}

	@Column(name = "drivernote")
	public String getDrivernote() {
		return this.drivernote;
	}

	public void setDrivernote(String drivernote) {
		this.drivernote = drivernote;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "driver")
	public Set<Application> getApplications() {
		return this.applications;
	}

	public void setApplications(Set<Application> applications) {
		this.applications = applications;
	}

}