package com.uber.uber;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Entity
public class Driver {
  @Id
  @GeneratedValue
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

  @GetMapping("/drivers")
  public List<Driver> getDrivers() {
    return driverRepo.findAll();
  }

}

interface DriverRepo extends JpaRepository<Driver, Integer> {
}