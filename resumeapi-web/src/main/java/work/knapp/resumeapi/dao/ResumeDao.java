package work.knapp.resumeapi.dao;

import java.util.Optional;

import work.knapp.resumeapi.model.Resume;

public interface ResumeDao {

	Optional<Resume> fetchResume(String companyName);

}
