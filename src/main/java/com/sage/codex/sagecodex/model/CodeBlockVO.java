package com.sage.codex.sagecodex.model;

/**
 * @Description：
 * @Author: xionghao
 * @Date: 2023/12/28 14:35
 */
public class CodeBlockVO {

    private String content;
    private boolean isCodeBlock;

    public CodeBlockVO(String content, boolean isCodeBlock) {
        this.content = content;
        this.isCodeBlock = isCodeBlock;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isCodeBlock() {
        return isCodeBlock;
    }

    public void setCodeBlock(boolean codeBlock) {
        isCodeBlock = codeBlock;
    }
}
