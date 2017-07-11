/**   
 * filename：UserSession.java
 *   
 * date：2016年4月26日
 * Copyright reey Corporation 2016 
 *   
 */
package cn.com.reey.VMSweb.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.com.reey.VMSweb.entity.Permission;
import cn.com.reey.VMSweb.entity.Users;


public class UserSession {
	
	private String username;
	

	private String roleName;
	private Set<String> permissionsNames = new HashSet<>();

	public UserSession(Users user) {
        if(user != null){
    		username = user.getUsername();
            //用户的角色
        	if(user.getRole() != null){
        		roleName = user.getRole().getRolename();
        	} 
            //用户的角色对应的所有权限
        	for (Permission perm : user.getRole().getPermissions()) {
        		permissionsNames.add(perm.getPermissionname());  
			}
        }  
        return;  
    }  

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}


	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Set<String> getPermissionsNames() {
		return permissionsNames;
	}

	public void setPermissionsNames(Set<String> permissionsNames) {
		this.permissionsNames = permissionsNames;
	}

	
	
}
