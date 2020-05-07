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
            if(statusCode == HttpStatus.UNAUTHORIZED.value()){
                return "redirect:/login";
            }
            else if(statusCode == HttpStatus.FORBIDDEN.value()) {
                request.setAttribute("pageTitle","Error 403 | MyExam");
                return "errors/error-403";
            }
            else if(statusCode == HttpStatus.NOT_FOUND.value()) {
                request.setAttribute("pageTitle","Error 404 | MyExam");
                return "errors/error-404";
            }
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                request.setAttribute("pageTitle","Error 500 | MyExam");
                return "errors/error-500";
            }
        }
        request.setAttribute("pageTitle","Error | MyExam");
        return "error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
