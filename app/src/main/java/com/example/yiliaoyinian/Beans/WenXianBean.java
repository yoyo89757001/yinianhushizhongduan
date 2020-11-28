package com.example.yiliaoyinian.Beans;

import java.util.List;

public class WenXianBean {


    /**
     * success : true
     * result : {"page":1,"rows":15,"totalRecord":2,"data":[{"id":23,"articleId":23,"title":"走向全国！美捷威通智护卧床系统首亮相安徽烧伤整形学术年会","articleImg":"https://images.jiahubao.net/group1/M00/00/33/rBoolV3kleiALqxZAAf-rI5grEo909.png","sort":1,"detail":"百家齐聚争鸣高新，群英荟萃论剑湖滨！2019年11月29-30日，由安徽省医师协会烧伤整形学分会主办，安徽医科大学第一附属医院承办的\u201c安徽省医师协会烧伤整形学分会2019年学术年会\u201d在\u201c大湖名城、创新高地\u201d合肥高新区隆重召开。","status":1,"publishTime":1575257863000,"createName":"李涛"},{"id":9,"articleId":9,"title":"高血压最全日常生活管理指南","subtitle":"高血压最全日常生活管理指南","articleImg":"https://images.jiahubao.net/group1/M00/00/29/rBoolV2RV0mADm8GAADAjKNBaR4586.png","sort":1,"detail":"高血压是导致心梗、脑梗、肾衰最重要的危险因素。据统计，全国每年有 200 万人死于与高血压有关的疾病。而且 6 成以上的冠心病人、8 成以上脑梗病人、9 成脑出血病人都有高血压史。","status":1,"publishTime":1575257204000}],"pageCount":2,"totalPage":1,"prePage":1,"nextPage":1,"firstPage":true,"lastPage":true}
     * code : 1
     */

    private boolean success;
    private ResultBean result;
    private int code;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class ResultBean {
        /**
         * page : 1
         * rows : 15
         * totalRecord : 2
         * data : [{"id":23,"articleId":23,"title":"走向全国！美捷威通智护卧床系统首亮相安徽烧伤整形学术年会","articleImg":"https://images.jiahubao.net/group1/M00/00/33/rBoolV3kleiALqxZAAf-rI5grEo909.png","sort":1,"detail":"百家齐聚争鸣高新，群英荟萃论剑湖滨！2019年11月29-30日，由安徽省医师协会烧伤整形学分会主办，安徽医科大学第一附属医院承办的\u201c安徽省医师协会烧伤整形学分会2019年学术年会\u201d在\u201c大湖名城、创新高地\u201d合肥高新区隆重召开。","status":1,"publishTime":1575257863000,"createName":"李涛"},{"id":9,"articleId":9,"title":"高血压最全日常生活管理指南","subtitle":"高血压最全日常生活管理指南","articleImg":"https://images.jiahubao.net/group1/M00/00/29/rBoolV2RV0mADm8GAADAjKNBaR4586.png","sort":1,"detail":"高血压是导致心梗、脑梗、肾衰最重要的危险因素。据统计，全国每年有 200 万人死于与高血压有关的疾病。而且 6 成以上的冠心病人、8 成以上脑梗病人、9 成脑出血病人都有高血压史。","status":1,"publishTime":1575257204000}]
         * pageCount : 2
         * totalPage : 1
         * prePage : 1
         * nextPage : 1
         * firstPage : true
         * lastPage : true
         */

        private int page;
        private int rows;
        private int totalRecord;
        private int pageCount;
        private int totalPage;
        private int prePage;
        private int nextPage;
        private boolean firstPage;
        private boolean lastPage;
        private List<DataBean> data;

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getRows() {
            return rows;
        }

        public void setRows(int rows) {
            this.rows = rows;
        }

        public int getTotalRecord() {
            return totalRecord;
        }

        public void setTotalRecord(int totalRecord) {
            this.totalRecord = totalRecord;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getPrePage() {
            return prePage;
        }

        public void setPrePage(int prePage) {
            this.prePage = prePage;
        }

        public int getNextPage() {
            return nextPage;
        }

        public void setNextPage(int nextPage) {
            this.nextPage = nextPage;
        }

        public boolean isFirstPage() {
            return firstPage;
        }

        public void setFirstPage(boolean firstPage) {
            this.firstPage = firstPage;
        }

        public boolean isLastPage() {
            return lastPage;
        }

        public void setLastPage(boolean lastPage) {
            this.lastPage = lastPage;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * id : 23
             * articleId : 23
             * title : 走向全国！美捷威通智护卧床系统首亮相安徽烧伤整形学术年会
             * articleImg : https://images.jiahubao.net/group1/M00/00/33/rBoolV3kleiALqxZAAf-rI5grEo909.png
             * sort : 1
             * detail : 百家齐聚争鸣高新，群英荟萃论剑湖滨！2019年11月29-30日，由安徽省医师协会烧伤整形学分会主办，安徽医科大学第一附属医院承办的“安徽省医师协会烧伤整形学分会2019年学术年会”在“大湖名城、创新高地”合肥高新区隆重召开。
             * status : 1
             * publishTime : 1575257863000
             * createName : 李涛
             * subtitle : 高血压最全日常生活管理指南
             */

            private long id;
            private int articleId;
            private String title;
            private String articleImg;
            private int sort;
            private String detail;
            private int status;
            private long publishTime;
            private String createName;
            private String subtitle;

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public int getArticleId() {
                return articleId;
            }

            public void setArticleId(int articleId) {
                this.articleId = articleId;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getArticleImg() {
                return articleImg;
            }

            public void setArticleImg(String articleImg) {
                this.articleImg = articleImg;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public long getPublishTime() {
                return publishTime;
            }

            public void setPublishTime(long publishTime) {
                this.publishTime = publishTime;
            }

            public String getCreateName() {
                return createName;
            }

            public void setCreateName(String createName) {
                this.createName = createName;
            }

            public String getSubtitle() {
                return subtitle;
            }

            public void setSubtitle(String subtitle) {
                this.subtitle = subtitle;
            }
        }
    }
}
