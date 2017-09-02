package com.helloblog;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Configuration
public class Errorpage {
    //@Bean
    //public EmbeddedServletContainerCustomizer embeddedServletContainerCustomizer() {
    //    return new EmbeddedServletContainerCustomizer() {
    //
    //        @Override
    //        public void customize(ConfigurableEmbeddedServletContainer container) {
    //            ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/iserror");
    //            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/iserror");
    //            ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/iserror");
    //            container.addErrorPages(error401Page, error404Page, error500Page);
    //        }
    //    };
    //}

    @Controller
    public class Error implements ErrorController {

        @RequestMapping("/error")
        public String handleError() {
            return "error";
        }

        @Override
        public String getErrorPath() {
            return "";
        }

    }

}
