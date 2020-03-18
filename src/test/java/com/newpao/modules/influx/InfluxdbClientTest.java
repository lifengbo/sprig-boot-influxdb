package com.newpao.modules.influx;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.InfluxDBClientOptions;
import com.influxdb.client.QueryApi;
import com.newpao.modules.influx.pojo.MeasurementHistory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class InfluxdbClientTest {
    private static char[] token = "YNoRC8zLk53VGDetrprWFQY7DuO42Ty3vkm1Lb-LorEo8DEZLVx4SB7JMnRYHujNBmlQDY_sNWfgQPU-_PQ53A==".toCharArray();

    private InfluxDBClient influxDBClient;

    @Before
    public void init() {
        InfluxDBClientOptions build = InfluxDBClientOptions.builder()
                .url("http://localhost:9999")
                .authenticateToken(token)
                .bucket("itom-hosts")
                .org("itom-org")
                .build();
        influxDBClient = InfluxDBClientFactory.create(build);
    }

    @After
    public void close() {
        influxDBClient.close();
    }

    private String getFilter(String... itemids) {
        String filter = "";
        if (ObjectUtil.isEmpty(itemids)) {
            return filter;
        }

        List<String> filterList = new ArrayList<>();
        for (String itemid : itemids) {
            filterList.add("r.itemid == \"" + itemid + "\"");
        }

        if (CollUtil.isEmpty(filterList)) {
            return filter;
        }
        return "|> filter(fn: (r) => " + CollUtil.join(filterList, " or ") + " )";
    }

    @Test
    public void typeTest() {
        String query = " from(bucket: \"itom-hosts\")  |> range(start: -2h)  " +
                "   |> filter(fn: (r) => r._measurement == \"historyDouble\") " +
                "    |> filter(fn: (r) => r._field == \"value\") " +
                "    |> aggregateWindow(every: 2m, fn: mean)" +
                "    |> group(columns: [\"_time\"], mode:\"by\")";

        QueryApi queryApi = influxDBClient.getQueryApi();

        List<MeasurementHistory> tables = queryApi.query(query, MeasurementHistory.class);
        System.out.println(tables);
    }

}