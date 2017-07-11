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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Role generated by hbm2java
 */
@Entity
@Table(name = "role", catalog = "vms")
public class Role implements java.io.Serializable {

	private Integer roleid;
	private String rolename;
	private String rolenote;
	private Set<Permission> permissions = new HashSet<Permission>(0);
	@JSONField(serialize = false)
	private Set<Users> userses = new HashSet<Users>(0);

	public Role() {
	}

	public Role(String rolename, String rolenote, Set<Permission> permissions,
			Set<Users> userses) {
		this.rolename = rolename;
		this.rolenote = rolenote;
		this.permissions = permissions;
		this.userses = userses;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "roleid", unique = true, nullable = false)
	public Integer getRoleid() {
		return this.roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}

	@Column(name = "rolename")
	public String getRolename() {
		return this.rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	@Column(name = "rolenote")
	public String getRolenote() {
		return this.rolenote;
	}

	public void setRolenote(String rolenote) {
		this.rolenote = rolenote;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "role_permission", catalog = "vms", joinColumns = { @JoinColumn(name = "roleid", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "permissionid", nullable = false, updatable = false) })
	public Set<Permission> getPermissions() {
		return this.permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
	public Set<Users> getUserses() {
		return this.userses;
	}

	public void setUserses(Set<Users> userses) {
		this.userses = userses;
	}

}