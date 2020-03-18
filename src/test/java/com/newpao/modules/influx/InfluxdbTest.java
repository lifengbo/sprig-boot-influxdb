package com.newpao.modules.influx;

import com.newpao.modules.BaseTest;
import com.newpao.modules.influx.service.InfluxDbService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class InfluxdbTest extends BaseTest {

    @Autowired
    private InfluxDbService influxdbService;

    @Test
    public void testSelect() {
        String query = " from(bucket: \"itom-hosts\")" +
                "  |> range(start: -1h)" +
                "  |> filter(fn: (r) => r._measurement == \"temperature\")" +
                "  |> filter(fn: (r) => r._field == \"value\")" +
                "  |> aggregateWindow(every: 1m, fn: mean)";
        System.out.print(query);


    }

}