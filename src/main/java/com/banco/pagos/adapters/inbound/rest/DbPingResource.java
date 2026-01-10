package com.banco.pagos.adapters.inbound.rest;

import javax.sql.DataSource;
import jakarta.annotation.Resource;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 *
 * @author
 */
@Path("/db-ping")
public class DbPingResource {

    @Resource(lookup = "java:/PayrollDS")
    DataSource ds;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String ping() throws Exception {
        try ( var c = ds.getConnection();  var ps = c.prepareStatement("SELECT 1");  var rs = ps.executeQuery()) {
            rs.next();
            return "DB_OK=" + rs.getInt(1);
        }
    }
}
