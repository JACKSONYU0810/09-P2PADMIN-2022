package com.jackson.p2padmin2022.user.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class UserInfo {
    private Integer id;

    private String username;

    private String password;

    private Date createdate;

    private Integer logincount;

    private Date lastlogintime;

    private Integer staffid;

    private StaffInfo staffInfo;

    public StaffInfo getStaffInfo() {
        return staffInfo;
    }

    public UserInfo setStaffInfo(StaffInfo staffInfo) {
        this.staffInfo = staffInfo;
        return this;
    }

    private Map<String,String> map;

    public Map<String, String> getMap() {
        return map;
    }

    public UserInfo setMap(Map<String, String> map) {
        this.map = map;
        return this;
    }

    private List<PermissionInfo> permissionInfoList = new ArrayList<>();

    public List<PermissionInfo> getPermissionInfoList() {
        return permissionInfoList;
    }

    public UserInfo setPermissionInfoList(List<PermissionInfo> permissionInfoList) {
        this.permissionInfoList = permissionInfoList;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Integer getLogincount() {
        return logincount;
    }

    public void setLogincount(Integer logincount) {
        this.logincount = logincount;
    }

    public Date getLastlogintime() {
        return lastlogintime;
    }

    public void setLastlogintime(Date lastlogintime) {
        this.lastlogintime = lastlogintime;
    }

    public Integer getStaffid() {
        return staffid;
    }

    public void setStaffid(Integer staffid) {
        this.staffid = staffid;
    }
}