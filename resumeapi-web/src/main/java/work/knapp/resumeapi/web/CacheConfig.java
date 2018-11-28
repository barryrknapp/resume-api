package work.knapp.resumeapi.web;

import java.lang.management.ManagementFactory;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.PersistenceConfiguration;
import net.sf.ehcache.config.PersistenceConfiguration.Strategy;
import net.sf.ehcache.management.ManagementService;
import work.knapp.resumeapi.service.ResumeService;

/**
 * Short term cache to limit high volumes of duplicate requests to customer
 * account service
 * 
 * @author bknapp
 */
@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {

	private static final int CACHE_DURATION_SECONDS = 15;

	private static final int MAX_ELEMENTS = 10000;

	@Bean(destroyMethod = "shutdown")
	public net.sf.ehcache.CacheManager ehCacheManager() {

		net.sf.ehcache.CacheManager manager = net.sf.ehcache.CacheManager
				.newInstance(new net.sf.ehcache.config.Configuration()
						.cache(new CacheConfiguration().name(ResumeService.CACHE_NAME_RESUME)
								.memoryStoreEvictionPolicy("LRU").maxEntriesLocalHeap(MAX_ELEMENTS)
								.timeToIdleSeconds(CACHE_DURATION_SECONDS).timeToLiveSeconds(CACHE_DURATION_SECONDS)
								.persistence(new PersistenceConfiguration().strategy(Strategy.NONE))));

		// Enable cache statistics on EhCache MBean to be consumed by any JMX-compatible
		// monitoring tool viz
		// JConsole/VisualVM
		ManagementService.registerMBeans(manager, ManagementFactory.getPlatformMBeanServer(), false, false, false,
				true);

		return manager;

	}

	@Bean
	@Override
	public CacheManager cacheManager() {
		return new EhCacheCacheManager(ehCacheManager());
	}

}
