package com.tutrit.webclient.controller;

import com.tutrit.bean.Car;
import com.tutrit.bean.CarBuilder;
import com.tutrit.gateway.CarGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;
import java.util.function.Consumer;

@Controller
@RequestMapping("/cars")
public class CarController {

    @Autowired(required = false)
    private CarGateway carGateway;

    @GetMapping("/{id}")
    public ModelAndView findCarById(@PathVariable String id) {
        var mov = new ModelAndView();
        carGateway.findCarById(id)
                .ifPresentOrElse(addCarToModel(mov),
                        addEmptyCar(mov));
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
                          @RequestParam Optional<String> save) {
        Car car = CarBuilder.builder()
                .carId(carId)
                .owner(owner)
                .vin(vin)
                .plateNumber(plateNumber)
                .brand(brand)
                .model(model)
                .generation(generation)
                .modification(modification)
                .engine(engine)
                .year(year)
                .build();

        save.ifPresentOrElse(i -> carGateway.saveCar(car), addEmptyCar(new ModelAndView()));
        return "redirect:/cars/" + id;
    }


    private Runnable addEmptyCar(ModelAndView mov) {
        return () -> mov.addObject("car", new Car(null, null, null, null, null, null, null, null, null, null));
    }

    private Consumer<Car> addCarToModel(ModelAndView mov) {
        return c -> mov.addObject("car", c);
    }
}
