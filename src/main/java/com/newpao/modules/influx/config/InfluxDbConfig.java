/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newpao.modules.influx.config;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.InfluxDBClientOptions;
import com.influxdb.client.domain.Bucket;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author fb.li
 */
@Configuration
@Log4j2
public class InfluxDbConfig {
    @Value("${spring.influx.url:''}")
    private String url;
    @Value("${spring.influx.token:''}")
    private String token;
    @Value("${spring.influx.orgName:''}")
    private String orgName;

    @Getter
    @Value("${spring.influx.bucket:''}")
    private String bucket;

    private InfluxDBClient influxDBClient;


    /**
     * 连接数据库bucket
     *
     * @return influxDb实例
     */
    @Bean
    public InfluxDBClient buildInfluxDbClient() {
        if (influxDBClient == null) {
            synchronized (this) {
                if (influxDBClient == null) {
                    InfluxDBClientOptions build = InfluxDBClientOptions.builder()
                            .url(url)
                            .authenticateToken(token.toCharArray())
                            .bucket(bucket)
                            .org(orgName)
                            .build();
                    influxDBClient = InfluxDBClientFactory.create(build);

                    Bucket bucketByName = influxDBClient.getBucketsApi().findBucketByName(bucket);
                    if (bucketByName == null || bucketByName.getOrgID() == null) {
                        log.error("buildInfluxDbClient has error!!!!!");
                    }
                }
            }
        }

        return influxDBClient;
    }
}
