package com.jackson.p2padmin2022.user.model;

import java.util.ArrayList;
import java.util.List;

public class PermissionInfo {
    private Integer id;

    private String name;

    private String type;

    private String url;

    private String code;

    private Integer parentid;

    private Double sort;

    private Integer available;

    private List<PermissionInfo> permissionInfoList = new ArrayList<>();

    public List<PermissionInfo> getPermissionInfoList() {
        return permissionInfoList;
    }

    public PermissionInfo setPermissionInfoList(List<PermissionInfo> permissionInfoList) {
        this.permissionInfoList = permissionInfoList;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public Double getSort() {
        return sort;
    }

    public void setSort(Double sort) {
        this.sort = sort;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }
}