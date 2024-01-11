package com.sage.codex.sagecodex.model;

/**
 * @Description：
 * @Author: xionghao
 * @Date: 2024/1/3 21:03
 */
public class PageContext {

   private  int lineNumber;

    private String pageContext;

    private  String fileName;

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getPageContext() {
        return pageContext;
    }

    public void setPageContext(String pageContext) {
        this.pageContext = pageContext;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
