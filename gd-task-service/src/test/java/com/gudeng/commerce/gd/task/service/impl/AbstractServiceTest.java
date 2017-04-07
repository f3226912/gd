package com.gudeng.commerce.gd.task.service.impl;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Description: TODO(单元测试抽象类)
 * @author mpan
 * @date 2015年12月14日 下午5:43:37
 */
@RunWith(SpringJUnit4ClassRunner.class)
//加载配置文件
@ContextConfiguration(locations = { "classpath*:/conf/test-spring-service.xml" })
public abstract class AbstractServiceTest {

}
