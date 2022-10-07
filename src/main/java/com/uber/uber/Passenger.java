package com.uber.uber;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Passenger {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Integer id;

  @Column(nullable = false)
  public String name;

  @JsonIgnore
  @ManyToMany(mappedBy = "passengers")
  public Set<Driver> drivers = new HashSet<>();

  public Passenger() {
  }
}

@RestController
class PassengerController {
  @Autowired
  private PassengerRepo passengerRepo;

  @GetMapping("/passengers")
  public List<Passenger> getPassengers() {
    return passengerRepo.findAll();
  }

  @PostMapping("/passengers")
  public Passenger createPassenger(@RequestBody Passenger passengerData) {
    return passengerRepo.save(passengerData);
  }

}

interface PassengerRepo extends JpaRepository<Passenger, Integer> {
}