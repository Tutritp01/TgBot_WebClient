package com.tutrit.webclient.controller;

import com.tutrit.bean.Car;
import com.tutrit.bean.CarBuilder;
import com.tutrit.controller.gateway.CarGatewayHttp;
import com.tutrit.gateway.CarGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/cars")
public class CarController {

//    @Autowired
    private CarGateway carGateway = new CarGatewayHttp();

    @GetMapping("/{id}")
    public ModelAndView findCarById(@PathVariable String id) {
        var mov = new ModelAndView();
        carGateway.findCarById(id)
                .ifPresentOrElse(
                        car -> mov.addObject("car", car),
                        () -> mov.addObject("car", CarBuilder.builder().build()));
        mov.setViewName("car-form");
        return mov;
    }

    @PostMapping("/{id}")
    public String saveCar(@PathVariable String id,
                          @RequestParam String carId,
                          @RequestParam String owner,
                          @RequestParam String vin,
                          @RequestParam String plateNumber,
                          @RequestParam String brand,
                          @RequestParam String model,
                          @RequestParam String generation,
                          @RequestParam String modification,
                          @RequestParam String engine,
                          @RequestParam Integer year,
                          @RequestParam Optional<String> save,
                          @RequestParam Optional<String> delete) {
        var car = new Car(carId, owner, vin, plateNumber, brand, model, generation, modification, engine, year);

        save.ifPresent(i -> carGateway.saveCar(car));
        delete.ifPresent(i -> carGateway.deleteCarById(carId));
        return "redirect:/cars/" + id;
    }

}
