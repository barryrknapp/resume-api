package work.knapp.resumeapi.dao;

import java.io.InputStream;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.ByteStreams;

import work.knapp.resumeapi.model.Resume;

public class ResumeDaoImpl implements ResumeDao {

	@Override
	public Optional<Resume> fetchResume(String companyName) {

		try {
			
		   
		    InputStream in = getClass().getResourceAsStream("/resume.json"); 
		    byte[] data = ByteStreams.toByteArray(in);
			// create ObjectMapper instance
			ObjectMapper objectMapper = new ObjectMapper();

			// convert json string to object
			Resume resume = objectMapper.readValue(data, Resume.class);

			return Optional.of(resume);
		} catch (Exception e) {
			throw new RuntimeException("Error reading file for " + companyName, e);
		}
	}

}
