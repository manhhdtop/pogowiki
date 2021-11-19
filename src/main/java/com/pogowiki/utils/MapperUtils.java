package com.pogowiki.utils;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class MapperUtils {
    @Autowired
    private ModelMapper modelMapper;

    public <T> T map(Object obj, Class<T> tClass) {
        if (obj == null) {
            return null;
        }
        return modelMapper.map(obj, tClass);
    }

    public <T> T map(Object src, T des) {
        if (src == null) {
            return null;
        }
        modelMapper.map(src, des);
        return des;
    }

    public <T> List<T> mapList(List<?> list) {
        Type type = new TypeToken<List<T>>() {
        }.getType();
        return modelMapper.map(list, type);
    }

    public <T> Page<T> mapPage(Page<?> page) {
        PageUtils<T> pageUtils = new PageUtils<>();
        if (page.isEmpty()) {
            return pageUtils.setPage(new ArrayList<>(), page.getPageable(), page.getTotalElements());
        }
        List<?> list = page.getContent();
        List<T> tList = mapList(list);
        return pageUtils.setPage(tList, page.getPageable(), page.getTotalElements());
    }
}
