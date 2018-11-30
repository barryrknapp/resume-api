package work.knapp.resumeapi.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import work.knapp.resumeapi.dao.ResumeDao;
import work.knapp.resumeapi.dao.ResumeDaoImpl;
import work.knapp.resumeapi.service.ResumeService;
import work.knapp.resumeapi.service.ResumeServiceImpl;

/**
 * Spring config for services and hystrix support
 */

@Configuration
public class ServiceConfig {

	private static Logger logger = LogManager.getLogger();

	@Bean
	public ResumeDao resumeDao() {

		logger.info("Initializing bean: ResumeDaoImpl");
		return new ResumeDaoImpl();
	}
	
	@Bean
	public ResumeService resumeService(ResumeDao resumeDao) {

		logger.info("Initializing bean: ResumeServiceImpl");
		return new ResumeServiceImpl(resumeDao);
	}

}
