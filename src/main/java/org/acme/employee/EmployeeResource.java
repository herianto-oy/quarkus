package org.acme.employee;
import java.util.List;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/employee")

public class EmployeeResource {
    private static final Logger LOG = Logger.getLogger(EmployeeResource.class);

    @Inject
    private EmployeeService employeeService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        EmployeeResponse resp = null;
        resp = new EmployeeResponse(200, "Success !", employeeService.getAll());
        return Response.ok(resp).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response createBulk(@RestForm("data") FileUpload fileUpload){
        List<String> mimetypeXlsx = List.of("application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "text/csv");
        EmployeeResponse resp = null;
        
        if(fileUpload.contentType() == null){
            LOG.info("Invalid Parameter");
            resp = new EmployeeResponse(400, "Invalid parameter!", null);
        } else if(mimetypeXlsx.contains(fileUpload.contentType())){
            LOG.info(fileUpload.contentType());
            resp = new EmployeeResponse(200, "Success !", employeeService.createBulk(fileUpload));
            return Response.ok(resp).build();
        } else {
            LOG.info("No Support "+fileUpload.contentType());
            resp = new EmployeeResponse(400, "No support mimetype !", null);
        }

        return Response.ok(resp).build();
    }
}
