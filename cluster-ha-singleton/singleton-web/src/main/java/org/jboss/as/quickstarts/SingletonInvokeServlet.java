package org.jboss.as.quickstarts;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.as.quickstarts.cluster.hasingleton.service.ejb.SingletonAccess;

/**
 * // TODO: Document this
 *
 * @author rhusar
 * @since 4.0
 */
@WebServlet(urlPatterns = {"/ssingleton"})
public class SingletonInvokeServlet extends HttpServlet {

    @EJB
    private SingletonAccess singleton;

    @Override
    public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        //super.doGet(httpServletRequest, httpServletResponse);

        httpServletResponse.setContentType("text/html");


        httpServletResponse.getWriter().println("NUmber of invocations: " + singleton.getInstanceId());

        httpServletResponse.getWriter().close();
    }
}
