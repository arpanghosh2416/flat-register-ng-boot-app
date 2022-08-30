package com.flat.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@GetMapping("/")
	public String test() {
		String msg = "<!doctype html>\r\n" + "<html lang=\"en\">\r\n" + "\r\n" + "<head>\r\n"
				+ "  <!-- Required meta tags -->\r\n" + "  <meta charset=\"utf-8\">\r\n"
				+ "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n" + "\r\n"
				+ "  <!-- Bootstrap CSS and JS -->\r\n"
				+ "  <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\"\r\n"
				+ "    integrity=\"sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3\" crossorigin=\"anonymous\">\r\n"
				+ "  <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js\"\r\n"
				+ "    integrity=\"sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p\"\r\n"
				+ "    crossorigin=\"anonymous\"></script>\r\n" + "\r\n"
				+ "  <title>Spring Boot CRUD Application</title>\r\n" + "</head>\r\n" + "\r\n" + "<body>\r\n"
				+ "  <div class=\"container my-5\">\r\n"
				+ "    <h1>Flat-Registration Spring Boot Application (Hosted by Arpan Ghosh)</h1>\r\n"
				+ "    <hr class=\"text-danger\">\r\n"
				+ "    <big style=\"font-size: 20px; font-family: cascadia code;\">Test All REST-APIs in Postman <span class=\"text-primary\">(Backend Server is Running...)</span></big>\r\n"
				+ "  </div>\r\n" + "</body>\r\n" + "\r\n" + "</html>";
		return msg;
	}

}
