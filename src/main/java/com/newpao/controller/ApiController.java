package com.newpao.controller;

import cn.hutool.core.util.StrUtil;
import com.newpao.modules.influx.pojo.MeasurementHistory;
import com.newpao.modules.influx.service.InfluxDbService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author fb.li
 */
@RestController
@CrossOrigin
@Log4j2
@RequestMapping(value = "api")
public class ApiController {

    @Autowired
    private InfluxDbService influxDbService;

    @GetMapping
    public List<MeasurementHistory> getItems(String startTime, String everyTime, Long... ids) {
        if (StrUtil.isBlank(startTime)) {
            startTime = "-1h";
        }
        if (StrUtil.isBlank(everyTime)) {
            everyTime = "1m";
        }

        return influxDbService.query(startTime, everyTime, MeasurementHistory.class, ids);
    }
}
