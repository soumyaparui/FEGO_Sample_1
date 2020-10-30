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
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobErrorCode;
import com.azure.storage.blob.models.BlobStorageException;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;

@SpringBootApplication
@RestController
public class Application {

    @RequestMapping("/")
    public String home() {
        String data = "soumya";
        String yourSasToken = "sv=2019-12-12&ss=bfqt&srt=sco&sp=rwdlacupx&se=2020-12-31T15:17:24Z&st=2020-10-29T07:17:24Z&spr=https,http&sig=I2n%2BRBtHXe4Pafc2hWQhQHcsQI97H4W8Owi6n3FZJtU%3D";
        /* Create a new BlobServiceClient with a SAS Token */
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().endpoint("https://soumyastorageaccount.blob.core.windows.net").sasToken(yourSasToken).buildClient();
        /* Create a new container client */
        BlobContainerClient containerClient = null;
        try {
            containerClient = blobServiceClient.getBlobContainerClient("aks");
        } catch (BlobStorageException ex) {
            // The container may already exist, so don't throw an error
            if (!ex.getErrorCode().equals(BlobErrorCode.CONTAINER_ALREADY_EXISTS)) {
                throw ex;
            }
        }
        /* Upload the file to the container */
        BlobClient blobClient = containerClient.getBlobClient("data.txt");
        try {
            InputStream input = blobClient.openInputStream();
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
