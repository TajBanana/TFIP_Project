package tajbanana.sudokuserver.services;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@Order(1)
public class JwtFilter implements Filter {

    @Autowired
    AuthenticateService authSvc;

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        final HttpServletRequest httpReq = (HttpServletRequest)request;
        final HttpServletResponse httpResp = (HttpServletResponse)response;

        // Look for Authorization: Bearer token
        String authHeader = httpReq.getHeader("Authorization");
        if ((null == authHeader) ||
                !authSvc.validate(authHeader.trim().substring("Bearer ".length()))) {
            unauthorized(httpResp);
            return;
        }

        // incoming request
        long before = System.currentTimeMillis();
        chain.doFilter(request, response);
        // outgoing response
        long duration = System.currentTimeMillis() - before;
    }

    private void unauthorized(HttpServletResponse httpResp) throws IOException {
        httpResp.setStatus(401);
        httpResp.setContentType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE);
        try (PrintWriter pw = httpResp.getWriter()) {
            JsonObject resp = Json.createObjectBuilder()
                    .add("error", "Invalid token")
                    .build();
            pw.print(resp.toString());
        }
    }

}
