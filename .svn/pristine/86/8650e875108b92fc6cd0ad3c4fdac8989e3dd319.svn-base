package com.treemanage.treecomponent.msg;

import org.springframework.http.HttpStatus;

import java.util.List;

public class TableResult<T> extends BaseResponse {

    TableData<T> data;

    public TableResult() {
        this.data = new TableData<T>();
    }

    public TableResult(long total, List<T> rows) {
        this.data = new TableData<T>(total, rows);
    }

    public TableResult(HttpStatus httpStatus, String discription, long total, List<T> rows) {
        super(httpStatus.value(),discription);
        this.data = new TableData<T>(total, rows);
    }

    public TableResult(HttpStatus httpStatus, String discription, List<T> rows) {
        super(httpStatus.value(),discription);
        this.data = new TableData<T>(rows.size(), rows);
    }

    public TableResult(int code,String discription,long total,List<T> rows) {
        super(code,discription);
        this.data = new TableData<T>(total, rows);
    }

    TableResult<T> total(int total) {
        this.data.setTotal(total);
        return this;
    }

    TableResult<T> total(List<T> rows) {
        this.data.setRows(rows);
        return this;
    }

    public TableData<T> getData() {
        return data;
    }

    public void setData(TableData<T> data) {
        this.data = data;
    }

    public class TableData<T> {
        long total;
        List<T> rows;

        public TableData(long total, List<T> rows) {
            this.total = total;
            this.rows = rows;
        }

        public TableData() {
        }

        public long getTotal() {
            return total;
        }

        public void setTotal(long total) {
            this.total = total;
        }

        public List<T> getRows() {
            return rows;
        }

        public void setRows(List<T> rows) {
            this.rows = rows;
        }
    }
}
