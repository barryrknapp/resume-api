package work.knapp.resumeapi.web.rest;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import work.knapp.resumeapi.model.Resume;
import work.knapp.resumeapi.service.ResumeService;
/**
 * Provides an API to encrypt a customer id.  An encrypted customer id is used to fetch customer details. see CustomerDetailsController
 * The expectation is that an internal service will encrypt a customer id and provide the encrypted id to an external third party.  The third party
 * can use that encrypted id to fetch customer details.
 * This endpoint on the /internal/ path is not exposed through APIGateway
 * @author bknapp
 *
 */
@RestController
@RequestMapping("/resume")
public class ResumeController {

	private static final Logger LOGGER = LogManager.getLogger();

	//visible for test
	@Autowired
	protected ResumeService resumeService;

	@ApiOperation(value = "get resume")
	@GetMapping("/{companyName}")
	public ResponseEntity<Resume> get(
			@ApiParam(value = "Name of company") @PathVariable(name = "companyName", required = true) String companyName) {
		
		LOGGER.debug(String.format("GET : resume for : %s", companyName));
		
		Optional<Resume> resume = resumeService.getResume(companyName);
	
		
		
		return resume.map(s -> new ResponseEntity<Resume>(s, HttpStatus.OK))
				.orElse(new ResponseEntity<Resume>(HttpStatus.NOT_FOUND));
	}

}
