package com.sage.codex.sagecodex.enums;

/**
 * @Description： 代码用途枚举
 */
public enum CodePurposeEnum {

    COMMON_GENERATE("common_generate", "生成"),
    COMMON_CHIT("common_chit", "闲聊"),
    COMMENT_GENERATE("comment_generate", "根据注释生成"),
    COMMENT_ADD("comment_add", "添加注释"),
    TEST_GENERATE("test_generate", "生成测试"),
    SEMANTIC_RETRIEVAL("semantic_retrieval", "语义检索"),
    CODE_COMPLETION("code_completion", "代码补全"),
    LINE_CODE_COMPLETION("line_code_completion", "行级代码补全"),
    CORRECTION_INSPECTION("correction_inspection", "纠错和检查");

    CodePurposeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    private String code;

    private String description;


    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
