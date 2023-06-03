package com.mb_medical_clinic_be.common;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SortParamMapper {
    public SortParamMapper() {
    }

    public static List<Sort.Order> map(List<SortOrder> params) {
        if (params != null && !params.isEmpty()) {
            List<Sort.Order> list = (List)params.stream().filter((p) -> {
                return StringUtils.isNotBlank(p.getProperty());
            }).map((param) -> {
                return new Sort.Order(param.getDirection(), param.getProperty());
            }).collect(Collectors.toList());
            return list;
        } else {
            return Collections.emptyList();
        }
    }
}
