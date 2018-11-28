package work.knapp.resumeapi.web.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SwaggerController {

  @RequestMapping("/")
  public String swagger(HttpServletResponse httpResponse) throws IOException {
    httpResponse.sendRedirect("/swagger-ui.html");
    return null;
  }
  
}
