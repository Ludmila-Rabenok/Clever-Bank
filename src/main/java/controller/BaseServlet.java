package controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * This is the underlying servlet that determines which method to call.
 */
public class BaseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String method = getMethod(req);
            if (method != null && !"".equals(method)) {
                callMethod(method, req, resp);
            }
        } catch (Exception e) {
            req.getRequestDispatcher(WebPages.PAGE_404).forward(req, resp);
        }
    }

    private String getMethod(HttpServletRequest request) {
        String method = request.getPathInfo();
        if (!"".equals(method)) {
            return method.substring(1, method.length());
        }
        return null;
    }

    private void callMethod(String method, HttpServletRequest request, HttpServletResponse response)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        this.getClass().getDeclaredMethod(method, HttpServletRequest.class, HttpServletResponse.class)
                .invoke(this, request, response);
    }
}
