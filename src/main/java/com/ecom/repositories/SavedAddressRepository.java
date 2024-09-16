package com.ecom.repositories;

import com.ecom.entity.SavedAddresses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavedAddressRepository extends JpaRepository<SavedAddresses,Long> {


    public List<SavedAddresses> findByUserId(Long userId);
}
