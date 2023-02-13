package com.tutrit.webclient.controller;

import com.tutrit.gateway.CarGateway;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class CarController {

    @Autowired(required = false)
    private CarGateway carGateway;

    @GetMapping("/cars/{carId}")
    public ModelAndView openCarFormByCarId(@PathVariable String carId) {
        var mov = new ModelAndView();
        mov.addObject("car", carGateway.findCarById(carId));
        mov.setViewName("car-form.html");
        return mov;
    }

    @PostMapping("/cars/{id}")
    public String saveCar(@PathVariable String id,
                          HttpServletRequest r,
                          @RequestParam String carId,
                          @RequestParam String carOwner,
                          @RequestParam String carVin,
                          @RequestParam String carPlateNumber,
                          @RequestParam String carBrand,
                          @RequestParam String carModel,
                          @RequestParam String carGeneration,
                          @RequestParam String carModification,
                          @RequestParam String carEngine,
                          @RequestParam Integer carYear,
                          @RequestParam Optional<String> save,
                          @RequestParam Optional<String> delete) {
        save.ifPresent(i -> carGateway.saveCar());
        delete.ifPresent(i -> carGateway.deleteCar());
        carGateway.saveCar(null);
        return "redirect:/cars/" + carId;
    }
}
