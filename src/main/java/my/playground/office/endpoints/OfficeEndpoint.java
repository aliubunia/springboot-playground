package my.playground.office.endpoints;

import my.playground.office.model.Office;
import my.playground.office.services.Clock;
import my.playground.office.services.OfficeService;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.*;

@Controller
@Path("/office")
public class OfficeEndpoint {

    @Inject
    protected OfficeService officeService;

    @Inject
    protected Clock clock;

    @Path("save.json")
    @POST @Consumes(APPLICATION_JSON) @Produces(APPLICATION_JSON)
    public Response save(JsonOffice jsonOffice) {
        if (jsonOffice == null) {
            return Response.status(BAD_REQUEST).build();
        }
        Office office = jsonOffice.unwrap();
        Integer id = officeService.saveOffice(office);
        office.setId(id);
        return Response.status(CREATED).
                header("Location", "/api/office/" + id + ".json").
                entity(JsonOffice.wrap(office, clock)).
                build();
    }

    @Path("{id}.json")
    @GET @Produces(APPLICATION_JSON)
    public Response getOffice(@PathParam("id") Integer id) {
        Optional<Office> office = officeService.findOfficeByID(id);
        if (!office.isPresent()) {
            return Response.status(NOT_FOUND).build();
        }
        return Response.ok(office.get()).build();
    }

    @Path("all.json")
    @GET @Produces(APPLICATION_JSON)
    public Response getAllOffices() {
        List<Office> offices = officeService.getAllOffices();
        return toResponse(offices);
    }

    @Path("openNow.json")
    @GET @Produces(APPLICATION_JSON)
    public Response getOfficesOpenNow() {
        List<Office> offices = officeService.getOfficesOpenNow();
        return toResponse(offices);
    }

    private Response toResponse(List<Office> offices) {
        List<JsonOffice> jsonOffices = offices.stream().map(office -> JsonOffice.wrap(office, clock)).collect(Collectors.toList());
        return Response.ok(jsonOffices).build();
    }

}
