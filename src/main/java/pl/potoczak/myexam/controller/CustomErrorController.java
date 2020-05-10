package pl.potoczak.myexam.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request){
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            request.setAttribute("pageTitle","Error | MyExam");
            request.setAttribute("errorCode","...");
            request.setAttribute("errorDescription", "Unhandled error!");

            if(statusCode == HttpStatus.UNAUTHORIZED.value()){
                return "redirect:/login";
            }
            else if(statusCode == HttpStatus.FORBIDDEN.value()) {
                request.setAttribute("pageTitle","Error 403 | MyExam");
                request.setAttribute("errorCode","403");
                request.setAttribute("errorDescription", "Page Forbidden");
            }
            else if(statusCode == HttpStatus.NOT_FOUND.value()) {
                request.setAttribute("pageTitle","Error 404 | MyExam");
                request.setAttribute("errorCode","404");
                request.setAttribute("errorDescription", "Page Not Found");
            }
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                request.setAttribute("pageTitle","Error 500 | MyExam");
                request.setAttribute("errorCode","500");
                request.setAttribute("errorDescription", "Server Error");
            }
        }
        return "error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
