package work.knapp.resumeapi.service;

import java.util.Optional;

import work.knapp.resumeapi.model.Resume;

public class ResumeServiceImpl implements ResumeService {

	@Override
	public Optional<Resume> getResume(String companyName) {
		Resume r = new Resume();
		r.setCompanyName(companyName);
		r.setId(1);
		
		//TODO move rendering to UI service
		r.setContent("<html><h1>HOWDY</h1></html>");
		return Optional.of(r);
	}

}
