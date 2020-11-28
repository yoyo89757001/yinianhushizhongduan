package com.example.yiliaoyinian.Beans;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * Created by Raul_lsj on 2018/3/22.
 */

public class ScrollBean implements SectionEntity {



    private String header;
    private boolean isHead;
    private ScrollItemBean scrollItemBean;

    public ScrollBean( boolean isHead,String header) {
        this.header = header;
        this.isHead = isHead;
    }

    public ScrollBean(ScrollItemBean scrollItemBean) {
        this.scrollItemBean = scrollItemBean;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public boolean isHead() {
        return isHead;
    }

    public void setHead(boolean head) {
        isHead = head;
    }

    public ScrollItemBean getScrollItemBean() {
        return scrollItemBean;
    }

    public void setScrollItemBean(ScrollItemBean scrollItemBean) {
        this.scrollItemBean = scrollItemBean;
    }

    @Override
    public boolean isHeader() {
        return isHead;
    }

    @Override
    public int getItemType() {
        if (isHead()){
            return HEADER_TYPE;
        }else {
            return NORMAL_TYPE;
        }

    }


    public static class ScrollItemBean {
        private String text;
        private String type;

        public ScrollItemBean(String text, String type) {
            this.text = text;
            this.type = type;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
