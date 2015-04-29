package com.useractivity.controller;

import com.useractivity.elasticsearch.service.QuadrantService;
import com.useractivity.kafka.producer.UserActivityProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by SatishDivakarla on 4/21/15.
 */

/**
 * Controller class(Spring MVC) to load JSP pages and
 */

@Controller
@RequestMapping("/")
public class UserActivityController {

    /**
     * Loads welcome.jsp
     * @return ModelAndView object
     */
    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public ModelAndView getWelcomePage() {

        ModelAndView modelAndView = new ModelAndView("welcome");

        return modelAndView;

    }

    /**
     * Loads activityview.jsp
     * @return ModelAndView object
     */
    @RequestMapping(value = "/activityview", method = RequestMethod.GET)
    public ModelAndView getActivityView() {

        ModelAndView model = new ModelAndView("activityview");

        return model;

    }

    /**
     * A REST webservice call to write the coordinates to kafka server
     * @param coordinateX X Coordinate
     * @param coordinateY Y Coordinate
     * @return Response Entitiy, "Success" if no failures
     */
    @RequestMapping(value = "/{coordinateX}/{coordinateY}", method = RequestMethod.POST)
    public ResponseEntity<String> updateCoordinates(@PathVariable String coordinateX, @PathVariable String coordinateY) {
        UserActivityProducer.getInstance().produce(coordinateX, coordinateY);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    /**
     * REST webservice call to retrieve the active quadrant in last 5 seconds
     * @return Active Quadrant
     */
    @RequestMapping(value="/viewactivity/", method = RequestMethod.GET)
    public ResponseEntity<Integer> getQuadrant(){
        return new ResponseEntity<Integer>(QuadrantService.getActiveQuadrant(), HttpStatus.OK);
    }
}
