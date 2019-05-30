package com.zzitbar.quartzcluster.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzitbar.quartzcluster.utils.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PageReqDto {
    private Integer offset = 0;//偏移
    private Integer limit = 10;//页 行数

    private String sort;//排序字段名
    private String order = ASC;//排序字段顺序

    public static final String ASC = "asc";
    public static final String DESC = "desc";

    /**
     * 删除标记（0：正常；1：删除；）
     */
    public static final Integer NOT_DELETED = 0;
    public static final Integer DELETED = 0;

    //排序map
    private Map<String, String> orderByMap = new LinkedHashMap<String, String>(4);

    private Page page = new Page();

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public PageReqDto pageable() {
        if (getOrderByMap().size()>0) {
            List<String> ascs = new ArrayList<>();
            List<String> descs = new ArrayList<>();
            for (Map.Entry<String, String> entry : getOrderByMap().entrySet()) {
                if (ASC.equalsIgnoreCase(entry.getValue())) {
                    ascs.add(StringUtils.toUnderScoreCase(entry.getKey()));
                } else if (DESC.equalsIgnoreCase(entry.getValue())) {
                    descs.add(StringUtils.toUnderScoreCase(entry.getKey()));
                }
            }
            page.setAscs(ascs);
            page.setDescs(descs);
        }
        page.setSize(limit).setCurrent(offset/limit+1);
        return this;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Map<String, String> getOrderByMap() {
        if (null != sort && "" != sort) {
            String[] sorts = sort.split(",");
            String[] orders = new String[sorts.length];
            if (null != order && "" != order) {
                orders = order.split(",");
            }
            for (int i = 0; i<sorts.length; i++) {
                orderByMap.put(sorts[i], orders[i]);
            }
            sort = null;
        }
        return orderByMap;
    }

    public void setOrderByMap(Map<String, String> orderByMap) {
        this.orderByMap = orderByMap;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}