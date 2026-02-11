package com.zhengqing.mybatis.parsing;

import com.google.common.collect.Lists;

import java.util.List;

public class ParameterMappingTokenHandler implements TokenHandler {

    private List<String> parameterMappings = Lists.newArrayList();

    @Override
    public String handleToken(String content) {
        parameterMappings.add(content);
        return "?";
    }
}
