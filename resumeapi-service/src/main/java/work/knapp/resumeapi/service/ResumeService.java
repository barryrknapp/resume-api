package work.knapp.resumeapi.service;

import java.util.Optional;

import work.knapp.resumeapi.model.Resume;

public interface ResumeService {

	String CACHE_NAME_RESUME = "CACHE_NAME_RESUME";

	/**
	 * fetch a resume tailored for a company
	 * @param companyName
	 * @return
	 */
	Optional<Resume> getResume(String companyName);

}
