package com.example.a51425.mainuiframe.bean;

import java.util.List;

/**
 * 作者：Created by wr
 * 时间: 2018/1/10 15:22
 */
public class SpeciesBean {

    private List<Result> result;
    private String result_info;
    private String result_code;
    public void setResult(List<Result> result) {
        this.result = result;
    }
    public List<Result> getResult() {
        return result;
    }

    public void setResult_info(String result_info) {
        this.result_info = result_info;
    }
    public String getResult_info() {
        return result_info;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }
    public String getResult_code() {
        return result_code;
    }



    /**
     * Auto-generated: 2018-01-10 15:0:12
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */
    public static class Children {

        private String id;
        private String pid;
        private String title;
        private List<String> children;
        public void setId(String id) {
            this.id = id;
        }
        public String getId() {
            return id;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }
        public String getPid() {
            return pid;
        }

        public void setTitle(String title) {
            this.title = title;
        }
        public String getTitle() {
            return title;
        }

        public void setChildren(List<String> children) {
            this.children = children;
        }
        public List<String> getChildren() {
            return children;
        }

    }


    /**
     * Auto-generated: 2018-01-10 15:0:12
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */
    public  static class Result {

        private String id;
        private String pid;
        private String title;
        private List<Children> children;
        public void setId(String id) {
            this.id = id;
        }
        public String getId() {
            return id;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }
        public String getPid() {
            return pid;
        }

        public void setTitle(String title) {
            this.title = title;
        }
        public String getTitle() {
            return title;
        }

        public void setChildren(List<Children> children) {
            this.children = children;
        }
        public List<Children> getChildren() {
            return children;
        }

    }
}
