/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newpao.modules.influx.dao;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.WritePrecision;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author 86183
 */
@Component
@Slf4j
public class InfluxdDbDao {

    @Autowired
    private InfluxDBClient influxDBClient;

    public <M> void save(@Nonnull List<M> list) {
        if (CollectionUtils.isEmpty(list)) {

            log.info("list is null");
            return;
        }
        try (WriteApi writeApi = influxDBClient.getWriteApi()) {
            writeApi.writeMeasurements(WritePrecision.NS, list);
        }
    }

    /**
     * |> range(start: v.timeRangeStart, stop: v.timeRangeStop)
     * |> filter(fn: (r) => r._measurement == "historyFloat")
     * |> filter(fn: (r) => r._field == "value")
     * |> filter(fn: (r) => r.itemid == "1")
     * |> aggregateWindow(every: 2m, fn: mean)
     */
    public <M> List<M> query(String query, Class<M> measurementType) {
        return influxDBClient.getQueryApi().query(query, measurementType);
    }

}

