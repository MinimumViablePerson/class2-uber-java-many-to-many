package com.uber.uber;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Entity
public class Driver {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Integer id;

  @Column(nullable = false)
  public String name;

  @ManyToMany(cascade = { CascadeType.ALL })
  @JoinTable(name = "Driver_Passenger", joinColumns = { @JoinColumn(name = "driver_id") }, inverseJoinColumns = {
      @JoinColumn(name = "passenger_id") })
  public Set<Passenger> passengers = new HashSet<>();

  public Driver() {
  }
}

@RestController
class DriverController {
  @Autowired
  private DriverRepo driverRepo;

  @Autowired
  private PassengerRepo passengerRepo;

  @GetMapping("/drivers")
  public List<Driver> getDrivers() {
    return driverRepo.findAll();
  }

  @PostMapping("/drivers")
  public Driver createDriver(@RequestBody Driver driverData) {
    return driverRepo.save(driverData);
  }

  @PostMapping("/ride")
  public Message createRide(@RequestBody DriverAndPassengerIds body) {
    Driver driver = driverRepo.findById(body.driverId).get();
    Passenger passenger = passengerRepo.findById(body.passengerId).get();
    driver.passengers.add(passenger);
    driverRepo.save(driver);
    return new Message("Ride successfully created.");
  }
}

class DriverAndPassengerIds {
  public Integer driverId;
  public Integer passengerId;
}

// const message = {}
class Message {
  public String message;

  public Message(String message) {
    this.message = message;
  }
}

interface DriverRepo extends JpaRepository<Driver, Integer> {
}
