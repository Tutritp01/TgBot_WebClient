package com.tutrit.webclient.exception;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Controller
public class ErrorHandlerController implements ErrorController {

    @RequestMapping("/error")
    public ModelAndView handleError(HttpServletRequest request) {
        final var mov = new ModelAndView();
        Optional.ofNullable(HttpStatus.valueOf((Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE))).ifPresent(
                status -> {
                    switch (status) {
                        case NOT_FOUND -> {
                            mov.addObject("imageSrc", this.randomErrorPageVideo());
                            mov.setViewName("404");
                        }
                        case INTERNAL_SERVER_ERROR -> mov.setViewName("500");
                        case FORBIDDEN -> mov.setViewName("403");
                        default -> mov.setViewName("error");
                    }
                }
        );
        return mov;
    }

    private String randomErrorPageVideo() {
        String[] video = {
                "https://freefrontend.com/assets/img/html-funny-404-pages/404-Error-Page-Smoke-From-Toaster.mp4",
                "https://freefrontend.com/assets/img/html-funny-404-pages/Ghost-HTML-Page-404.mp4",
                "https://freefrontend.com/assets/img/html-funny-404-pages/Pickle-Rick-Sliced-404.mp4"
        };
        return video[ThreadLocalRandom.current().nextInt(0, 3)];
    }
}
