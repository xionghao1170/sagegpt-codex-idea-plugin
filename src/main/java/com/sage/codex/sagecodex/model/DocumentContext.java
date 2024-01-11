package com.sage.codex.sagecodex.model;

import com.sage.codex.sagecodex.enums.CodePurposeEnum;

/**
 * @Description：
 * @Author: xionghao
 * @Date: 2024/1/3 20:53
 */
public class DocumentContext {

    private String  documentText;

    private String caretBeforeText;

    private CodePurposeEnum codePurposeEnum;

    private int lineNumber;

    private String fileTypeName;

    public String getDocumentText() {
        return documentText;
    }

    public void setDocumentText(String documentText) {
        this.documentText = documentText;
    }

    public String getCaretBeforeText() {
        return caretBeforeText;
    }

    public void setCaretBeforeText(String caretBeforeText) {
        this.caretBeforeText = caretBeforeText;
    }

    public CodePurposeEnum getCodePurposeEnum() {
        return codePurposeEnum;
    }

    public void setCodePurposeEnum(CodePurposeEnum codePurposeEnum) {
        this.codePurposeEnum = codePurposeEnum;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getFileTypeName() {
        return fileTypeName;
    }

    public void setFileTypeName(String fileTypeName) {
        this.fileTypeName = fileTypeName;
    }
}
