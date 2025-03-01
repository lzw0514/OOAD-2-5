//School of Informatics Xiamen University, GPL-3.0 license

package cn.edu.xmu.oomall.customer;

import cn.edu.xmu.javaee.core.jpa.SelectiveUpdateJpaRepositoryImpl;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * RocketMQ是无法测试的，由于每个SpringBoot测试是独立，会重复向RocketMQ服务器注册Producer和Consumer，
 * 从而会产生The consumer group[] has been created before, specify another name please的错误。
 * 因此需要把RocketMQ的Producer和Consumer从测试环境中摘出去。
 */
@ComponentScan(basePackages = {"cn.edu.xmu.javaee.core",
        "cn.edu.xmu.oomall.customer"},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {RocketMQMessageListener.class, SpringBootApplication.class }),
        })
@SpringBootConfiguration
@EnableAutoConfiguration
@EntityScan(basePackages = "cn.edu.xmu.oomall.customer.mapper.po")
@EnableJpaRepositories(value = "cn.edu.xmu.javaee.core.jpa", repositoryBaseClass = SelectiveUpdateJpaRepositoryImpl.class, basePackages = "cn.edu.xmu.oomall.customer.mapper")

@EnableFeignClients
@EnableDiscoveryClient
public class CustomerTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(CustomerTestApplication.class, args);
    }
}
