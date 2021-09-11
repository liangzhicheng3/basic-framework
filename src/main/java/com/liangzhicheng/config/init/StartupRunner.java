package com.liangzhicheng.config.init;

import com.liangzhicheng.common.utils.SysCacheUtil;
import com.liangzhicheng.common.utils.SysToolUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @description 启动初始化加载数据配置，存在多个初始化加载数据，创建多个类，使用@Order(value = ?)注解来区分先后顺序
 * @author liangzhicheng
 * @since 2020-08-20
 */
@Component
public class StartupRunner implements CommandLineRunner {

	@Override
	public void run(String ... args) throws Exception {
		SysToolUtil.info("StartupRunner -> come start ...");
		//缓存初始化
		SysCacheUtil.init();
	}

}
