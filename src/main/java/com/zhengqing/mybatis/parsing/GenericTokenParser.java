package com.zhengqing.mybatis.parsing;

public class GenericTokenParser {

    private String openToken; // 开始标记, e.g., #{
    private String closeToken; // 结束标记, e.g., }
    private TokenHandler tokenHandler;

    public GenericTokenParser(String openToken, String closeToken, TokenHandler tokenHandler) {
        this.openToken = openToken;
        this.closeToken = closeToken;
        this.tokenHandler = tokenHandler;
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
            close = text.indexOf(closeToken, open);
            stringBuilder.append(tokenHandler.handleToken(text.substring(open + openToken.length(), close)));
            open = text.indexOf(openToken, close);
        }

        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        String s = "abc";
        System.out.println(s.indexOf("b"));
    }
}
