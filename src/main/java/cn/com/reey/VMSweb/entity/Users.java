package cn.com.reey.VMSweb.entity;

// Generated 2016-5-5 16:53:14 by Hibernate Tools 4.0.0

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Users generated by hbm2java
 */
@Entity
@Table(name = "users", catalog = "vms")
public class Users implements java.io.Serializable {

	private Integer userid;
	private Role role;
	private String username;
	@JSONField(serialize = false)
	private String userpwd;
	private String userworkid;
	private String userrealname;
	private String userdepartment;
	private String userduty;
	private String userphonenumber;
	private String userimg;
	private Date userlastlogintime;
	private String usernote;
	private String cid;
	@JSONField(serialize = false)
	private String usersignid;
	@JSONField(serialize = false)
	private Set<Application> applicationsForApplicationuser = new HashSet<Application>(
			0);
	@JSONField(serialize = false)
	private Set<Application> applicationsForApplicationsendpeople = new HashSet<Application>(
			0);

	public Users() {
	}

	public Users(Role role, String username, String userpwd, String userworkid,
			String userrealname, String userdepartment, String userduty,
			String userphonenumber, String userimg, Date userlastlogintime,
			String usersignid, String usernote, String cid,
			Set<Application> applicationsForApplicationuser,
			Set<Application> applicationsForApplicationsendpeople) {
		this.role = role;
		this.username = username;
		this.userpwd = userpwd;
		this.userworkid = userworkid;
		this.userrealname = userrealname;
		this.userdepartment = userdepartment;
		this.userduty = userduty;
		this.userphonenumber = userphonenumber;
		this.userimg = userimg;
		this.userlastlogintime = userlastlogintime;
		this.usersignid = usersignid;
		this.usernote = usernote;
		this.cid = cid;
		this.applicationsForApplicationuser = applicationsForApplicationuser;
		this.applicationsForApplicationsendpeople = applicationsForApplicationsendpeople;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "userid", unique = true, nullable = false)
	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userrole")
	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Column(name = "username", length = 32)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "userpwd", length = 32)
	public String getUserpwd() {
		return this.userpwd;
	}

	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}

	@Column(name = "userworkid", length = 32)
	public String getUserworkid() {
		return this.userworkid;
	}

	public void setUserworkid(String userworkid) {
		this.userworkid = userworkid;
	}

	@Column(name = "userrealname", length = 32)
	public String getUserrealname() {
		return this.userrealname;
	}

	public void setUserrealname(String userrealname) {
		this.userrealname = userrealname;
	}

	@Column(name = "userdepartment", length = 32)
	public String getUserdepartment() {
		return this.userdepartment;
	}

	public void setUserdepartment(String userdepartment) {
		this.userdepartment = userdepartment;
	}

	@Column(name = "userduty", length = 32)
	public String getUserduty() {
		return this.userduty;
	}

	public void setUserduty(String userduty) {
		this.userduty = userduty;
	}

	@Column(name = "userphonenumber", length = 11)
	public String getUserphonenumber() {
		return this.userphonenumber;
	}

	public void setUserphonenumber(String userphonenumber) {
		this.userphonenumber = userphonenumber;
	}

	@Column(name = "userimg")
	public String getUserimg() {
		return this.userimg;
	}

	public void setUserimg(String userimg) {
		this.userimg = userimg;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "userlastlogintime", length = 19)
	public Date getUserlastlogintime() {
		return this.userlastlogintime;
	}

	public void setUserlastlogintime(Date userlastlogintime) {
		this.userlastlogintime = userlastlogintime;
	}

	@Column(name = "usersignid", length = 32)
	public String getUsersignid() {
		return this.usersignid;
	}

	public void setUsersignid(String usersignid) {
		this.usersignid = usersignid;
	}

	@Column(name = "usernote")
	public String getUsernote() {
		return this.usernote;
	}

	public void setUsernote(String usernote) {
		this.usernote = usernote;
	}
	
	@Column(name = "cid")
	public String getCid() {
		return this.cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usersByApplicationuser")
	public Set<Application> getApplicationsForApplicationuser() {
		return this.applicationsForApplicationuser;
	}

	public void setApplicationsForApplicationuser(
			Set<Application> applicationsForApplicationuser) {
		this.applicationsForApplicationuser = applicationsForApplicationuser;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usersByApplicationsendpeople")
	public Set<Application> getApplicationsForApplicationsendpeople() {
		return this.applicationsForApplicationsendpeople;
	}

	public void setApplicationsForApplicationsendpeople(
			Set<Application> applicationsForApplicationsendpeople) {
		this.applicationsForApplicationsendpeople = applicationsForApplicationsendpeople;
	}

}
