/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newpao.modules.influx.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.newpao.modules.influx.config.InfluxDbConfig;
import com.newpao.modules.influx.dao.InfluxdDbDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fb.li
 */
@Service
public class InfluxDbService {

    @Autowired
    private InfluxDbConfig config;

    @Autowired
    private InfluxdDbDao influxdbDao;

    public <M> void save(@Nonnull List<M> list) {
        influxdbDao.save(list);
    }

    public <M> List<M> query(String start, String every, Class<M> clazz, Long... ids) {
        String bucket = config.getBucket();
        String filter = getFilter(ids);

        String query = " from(bucket: \"" + bucket + "\")" +
                "  |> range(start: " + start + ")" +
                "  |> filter(fn: (r) => r._measurement == \"history\")" +
                "  |> filter(fn: (r) => r._field == \"value\")" +
                filter +
                "  |> aggregateWindow(every: " + every + ", fn: mean)";

        return influxdbDao.query(query, clazz);
    }

    private String getFilter(Long[] ids) {
        String filter = "";
        if (ObjectUtil.isEmpty(ids)) {
            return filter;
        }

        List<String> filterList = new ArrayList<>();
        for (Long itemid : ids) {
            filterList.add("r.id == \"" + itemid + "\"");
        }

        if (CollUtil.isEmpty(filterList)) {
            return filter;
        }
        return "|> filter(fn: (r) => " + CollUtil.join(filterList, " or ") + " )";
    }
}
