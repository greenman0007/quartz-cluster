package com.zzitbar.quartzcluster.dto;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * bootstrap-table 分页查询结果DTO
 */
public class PageDataDto<T> {

    private long total = 0;

    private List<T> rows = new ArrayList<T>();

    public PageDataDto() {
    }

    public PageDataDto(long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public PageDataDto(Page<T> page) {
        this.rows = page.getRecords();
        this.total = page.getTotal();
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public static PageDataDto buildPageData(PageReqDto dto) {
        return new PageDataDto(dto.getPage().getTotal(), dto.getPage().getRecords());
    }

    public static PageDataDto buildPageData(PageReqDto dto, List data) {
        return new PageDataDto(dto.getPage().getTotal(), data);
    }
}