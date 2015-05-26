package com.jfinal.share.core;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.ext.plugin.shiro.ShiroInterceptor;
import com.jfinal.ext.plugin.shiro.ShiroPlugin;
import com.jfinal.ext.plugin.tablebind.AutoTableBindPlugin;
import com.jfinal.ext.plugin.tablebind.SimpleNameStyles;
import com.jfinal.ext.route.AutoBindRoutes;
import com.jfinal.plugin.activerecord.dialect.AnsiSqlDialect;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.dialect.OracleDialect;
import com.jfinal.plugin.activerecord.tx.TxByActionMethods;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.druid.DruidStatViewHandler;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.share.handler.HtmlHandler;
import com.jfinal.share.interceptor.ManagerInterceptor;

/**
 * API引导式配置 Jfinal核心配置
 * 
 * @author 風佑兲(375910297@qq.com)
 * @date 2014年11月30日 下午4:23:11
 */
public class FytConfig extends JFinalConfig {
	/**
	 * 供Shiro插件使用。
	 */
	private Routes routes;

	@Override
	public void configConstant(Constants me) {
		// 加载少量必要配置，随后可用getProperty(...)获取值
		loadPropertyFile("config.properties");
		// 开发模式
		me.setDevMode(getPropertyToBoolean("devMode", false));

		me.setError401View("/WEB-INF/pages/common/401.html"); // 没登录
		me.setError403View("/WEB-INF/pages/common/403.html"); // 没权限
		me.setError404View("/WEB-INF/pages/common/404.html");
		me.setError500View("/WEB-INF/pages/common/500.html");
		me.setBaseViewPath("/WEB-INF/pages/");
		me.setFreeMarkerTemplateUpdateDelay(1);

	}

	/**
	 * 配置路由
	 */
	@Override
	public void configRoute(Routes me) {
		routes = me;
		me.add(new AutoBindRoutes());
	}

	/**
	 * 配置插件
	 */
	@Override
	public void configPlugin(Plugins me) {
		// 配置alibaba数据库连接池
		DruidPlugin druidPlugin = new DruidPlugin(getProperty("db.url"), getProperty("db.username"), getProperty("db.password"),getProperty("db.driver"));
		WallFilter wallFilter = new WallFilter();
		wallFilter.setDbType(getProperty("db.type"));
		druidPlugin.addFilter(wallFilter);
		druidPlugin.addFilter(new StatFilter());
		me.add(druidPlugin);

		// 添加自动绑定model与表插件
		AutoTableBindPlugin autoTableBindPlugin = new AutoTableBindPlugin(druidPlugin, SimpleNameStyles.LOWER_UNDERLINE);
		autoTableBindPlugin.setShowSql(getPropertyToBoolean("showSql", false));
		String db = getProperty("db.type");
		if ("mysql".equals(db)) {
			autoTableBindPlugin.setDialect(new MysqlDialect());
		} else if ("oracle".equals(db)) {
			autoTableBindPlugin.setDialect(new OracleDialect());
		} else {
			autoTableBindPlugin.setDialect(new AnsiSqlDialect());
		}
		me.add(autoTableBindPlugin);

		// ehcache缓存插件
		me.add(new EhCachePlugin());

		// 配置shiro插件
		me.add(new ShiroPlugin(this.routes));
	}

	/**
	 * 配置全局拦截器
	 */
	@Override
	public void configInterceptor(Interceptors me) {
		// 自定义未登录拦截
		me.add(new ManagerInterceptor());
		// shiro权限拦截器配置
		me.add(new ShiroInterceptor());
		// 解决session在freemarker中不能取得的问题 获取方法：${session["manager"].username}
		me.add(new SessionInViewInterceptor());
		// 添加事物，对save、update和delete自动进行拦截
		me.add(new TxByActionMethods("save", "update", "delete"));
	}

	/**
	 * 配置处理器
	 */
	@Override
	public void configHandler(Handlers me) {
		me.add(new ContextPathHandler());
		DruidStatViewHandler dsvh = new DruidStatViewHandler("/druid");
		me.add(dsvh);
		me.add(new HtmlHandler());
	}

}
