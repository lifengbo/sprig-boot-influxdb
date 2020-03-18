package com.newpao.modules.job;

import cn.hutool.core.util.RandomUtil;
import com.newpao.modules.influx.pojo.MeasurementHistory;
import com.newpao.modules.influx.service.InfluxDbService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * job任务
 *
 * @author fb.li
 */
@Component
@EnableScheduling
@Slf4j
public class JobTask {

    @Autowired
    private InfluxDbService influxDbService;

    /**
     * mock数据到influxdb
     */
    @Scheduled(cron = "30 0/1 * * * ?")
    private void mockDataToInfluxdb() {
        log.info("mockDataToInfluxdb>>---begin");
        List<MeasurementHistory> measurementHistories = mockData(100);

        influxDbService.save(measurementHistories);

        log.info("mockDataToInfluxdb>>---end");
    }

    /**
     * @param size
     * @return
     */
    private List<MeasurementHistory> mockData(int size) {
        return mockData(size, 1, 100);
    }

    /**
     * 随机生成mock数据, 主键id根据给定的范围生成, 默认 [1~100)
     *
     * @param size
     * @param beginId
     * @param endId
     * @return
     */
    private List<MeasurementHistory> mockData(int size, int beginId, int endId) {
        List<MeasurementHistory> measurementHistoryList = new ArrayList<>(size);
        long current = System.currentTimeMillis() / 1000;
        for (int i = 0; i < size; i++) {
            MeasurementHistory history = new MeasurementHistory();
            history.setId(String.valueOf(RandomUtil.randomInt(beginId, endId)));

            history.setValue(RandomUtil.randomDouble(10, 10000));

            history.setTime(Instant.ofEpochSecond(current - RandomUtil.randomLong(0, 60)));

            measurementHistoryList.add(history);
        }

        return measurementHistoryList;
    }
}
