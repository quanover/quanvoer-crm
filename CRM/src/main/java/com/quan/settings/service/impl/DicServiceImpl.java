package com.quan.settings.service.impl;

import com.quan.settings.dao.DicTypeDao;
import com.quan.settings.dao.DicValueDao;
import com.quan.settings.domain.DicType;
import com.quan.settings.domain.DicValue;
import com.quan.settings.service.DicService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class DicServiceImpl implements DicService {



    @Resource
    private DicTypeDao dicTypeDao;

    @Resource
    private DicValueDao dicValueDao;



    @Override
    public Map<String, List<DicValue>> getAll() {

        Map<String, List<DicValue>> map=new HashMap<>();
        List<DicType> dtList = dicTypeDao.getTypeList();

        for (DicType dicType : dtList) {

            String code=dicType.getCode();
            List<DicValue> dvList=dicValueDao.getListByCode(code);
            map.put(code,dvList);
        }

        return map;
    }
}
