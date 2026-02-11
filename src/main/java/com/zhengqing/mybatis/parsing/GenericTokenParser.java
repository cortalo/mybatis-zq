package com.zhengqing.mybatis.parsing;

public class GenericTokenParser {

    private String openToken; // 开始标记, e.g., #{
    private String closeToken; // 结束标记, e.g., }

    public GenericTokenParser(String openToken, String closeToken) {
        this.openToken = openToken;
        this.closeToken = closeToken;
    }

    public String parse(String text) {
        int open = text.indexOf(openToken);
        if (open == -1) {
            return text;
        }
        StringBuilder stringBuilder = new StringBuilder();
        int close = -1;
        while (open != -1) {
            stringBuilder.append(text, close + 1, open);
            stringBuilder.append(" ? ");
            close = text.indexOf(closeToken, open);
            open = text.indexOf(openToken, close);
        }

        return stringBuilder.toString();
    }
}
