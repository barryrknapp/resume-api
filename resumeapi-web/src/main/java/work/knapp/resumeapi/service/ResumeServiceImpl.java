package work.knapp.resumeapi.service;

import java.util.Optional;

import work.knapp.resumeapi.dao.ResumeDao;
import work.knapp.resumeapi.model.Resume;

public class ResumeServiceImpl implements ResumeService {

	private ResumeDao resumeDao;

	public ResumeServiceImpl(final ResumeDao resumeDao) {
		this.resumeDao = resumeDao;
	}

	@Override
	public Optional<Resume> getResume(String companyName) {

		return resumeDao.fetchResume(companyName);
	}

}
