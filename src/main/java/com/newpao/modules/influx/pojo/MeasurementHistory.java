package com.newpao.modules.influx.pojo;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import lombok.Data;

import java.time.Instant;


/**
 * @author dev
 */
@Data
@Measurement(name = "history")
public class MeasurementHistory {
    @Column(timestamp = true)
    private Instant time;

    @Column(tag = true)
    private String id;

    @Column
    private Double value;

}
