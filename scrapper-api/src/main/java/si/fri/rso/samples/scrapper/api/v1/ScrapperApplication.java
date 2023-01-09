package si.fri.rso.samples.scrapper.api.v1;

import com.kumuluz.ee.cors.annotations.CrossOrigin;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@CrossOrigin
@OpenAPIDefinition(info = @Info(title = "Scrapper c" +
        "" +
        "" +
        "atalog API", version = "v1",
        contact = @Contact(email = "rso@fri.uni-lj.si"),
        license = @License(name = "dev"), description = "API for managing scrapper metadata."),
        servers = @Server(url = "http://localhost:8080/"))
@ApplicationPath("/v1")
public class ScrapperApplication extends Application {

}
