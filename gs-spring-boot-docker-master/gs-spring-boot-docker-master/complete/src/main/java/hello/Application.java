package hello;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

	@RequestMapping("/")
	public String home() {

		String data = "soumya";
		try {
//			Resource resource = new ClassPathResource("data.txt");
//			InputStream input = resource.getInputStream();

			FileSystemResource imgFile = new FileSystemResource("C:/FEGO/data.txt");
			InputStream input = imgFile.getInputStream();
			
			byte[] bdata = FileCopyUtils.copyToByteArray(input);
			data = new String(bdata, StandardCharsets.UTF_8);
			
		} catch (IOException e) {

		}
		return data;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
