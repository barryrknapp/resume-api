package work.knapp.resumeapi.web.rest;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import work.knapp.resumeapi.model.Resume;
import work.knapp.resumeapi.service.ResumeService;

public class ResumeControllerTest {

	private ResumeController sut;
	
	@Mock
	private ResumeService resumeService;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		sut = new ResumeController();
		sut.resumeService = resumeService;
	}
	
	@Test
	public void testGET() {
		String companyName = "booyah!";
		
		Optional<Resume> expected = resume();
		
		Mockito.when(resumeService.getResume(companyName)).thenReturn(expected);
		ResponseEntity<Resume> actual = sut.get(companyName);
		
		Assert.assertEquals(expected.get(), actual.getBody());
		Assert.assertEquals(200, actual.getStatusCodeValue());

		
	}

	private Optional<Resume> resume() {
		Resume r = new Resume();
		r.setAddress("howdy");
		return Optional.of(r);
	}
}
