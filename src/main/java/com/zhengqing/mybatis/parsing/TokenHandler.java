package com.zhengqing.mybatis.parsing;

import java.util.List;

public interface TokenHandler {

    String handleToken(String content);

    List<String> getParameters();

}
