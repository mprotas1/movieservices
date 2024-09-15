package com.movieapp.cinemas.domain.repository;

import com.movieapp.cinemas.domain.entity.Address;
import com.movieapp.cinemas.domain.model.AddressInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query("SELECT a FROM Address a " +
            "WHERE a.city = :#{#addressInformation.city()} AND a.street = :#{#addressInformation.street()} AND a.postalCode = :#{#addressInformation.postalCode()}")
    Optional<Address> findByAddressInformation(AddressInformation addressInformation);

}
