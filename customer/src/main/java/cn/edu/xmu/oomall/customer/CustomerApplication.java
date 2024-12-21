package cn.edu.xmu.oomall.customer;

import cn.edu.xmu.javaee.core.jpa.SelectiveUpdateJpaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@EntityScan(basePackages = "cn.edu.xmu.oomall.customer.mapper.po")
@SpringBootApplication(scanBasePackages = {"cn.edu.xmu.javaee.core", "cn.edu.xmu.oomall.customer"})
@EnableJpaRepositories(value = "cn.edu.xmu.javaee.core.jpa", repositoryBaseClass = SelectiveUpdateJpaRepositoryImpl.class, basePackages = "cn.edu.xmu.oomall.customer.mapper")
@EnableDiscoveryClient
public class CustomerApplication {public static void main(String[] args) {
    SpringApplication.run(CustomerApplication.class, args);
}
}
