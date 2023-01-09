package si.fri.rso.samples.scrapper.api.v1.resources;

import com.kumuluz.ee.logs.cdi.Log;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.rso.samples.scrapper.services.beans.ScrapperBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;
import com.kumuluz.ee.cors.annotations.CrossOrigin;


@Log
@ApplicationScoped
@Path("/scrapper")
@Produces(MediaType.APPLICATION_JSON)
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
@CrossOrigin
public class ScrapperResource {

    private Logger log = Logger.getLogger(ScrapperResource.class.getName());

    @Inject
    private ScrapperBean scrapperBean;


    @Context
    protected UriInfo uriInfo;

    @Operation(description = "Get metadata for artikel.", summary = "Get metadata for artikel")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Image metadata",
                    content = @Content(
                            schema = @Schema(implementation = List.class))
            )})
    @GET
    @Path("/{artikliName}")
    public Response getArtikliPrice(@Parameter(description = "Metadata ID.", required = true)
                                     @PathParam("artikliName") String artikliName) {

        List<Float> artikelPrice = scrapperBean.getScrapperPrice(artikliName);

        if (artikelPrice.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(artikelPrice).build();
    }
}
