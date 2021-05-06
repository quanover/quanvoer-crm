package com.quan.vo;

import java.util.List;

public class PageVO<T> {

    private  int total;
    private List<T> dataList;



    @Override
    public String toString() {
        return "PageVO{" +
                "total=" + total +
                ", dataList=" + dataList +
                '}';
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }


}
