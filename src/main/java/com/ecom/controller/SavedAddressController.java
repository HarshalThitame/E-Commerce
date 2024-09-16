package com.ecom.controller;

import com.ecom.entity.SavedAddresses;
import com.ecom.service.SavedAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user/savedAddress")
public class SavedAddressController {

    @Autowired
    private SavedAddressService savedAddressService;

    @GetMapping
    public ResponseEntity<List<SavedAddresses>> getAllSavedAddress() {
        List<SavedAddresses> allSavedAddresses = savedAddressService.getAllSavedAddresses();
        if (allSavedAddresses != null) {
            return ResponseEntity.ok(allSavedAddresses);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<SavedAddresses> saveSavedAddress(@RequestBody SavedAddresses savedAddress) {
        SavedAddresses saveAddress = savedAddressService.saveAddress(savedAddress);
        if (saveAddress != null) {
            return ResponseEntity.ok(saveAddress);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SavedAddresses> updateSavedAddress(@PathVariable("id") Long id, @RequestBody SavedAddresses savedAddress) {
        Optional<SavedAddresses> savedAddressById = savedAddressService.getAddressByID(id);
        if (savedAddressById.isPresent()) {
            savedAddress.setId(id);
            savedAddressService.saveAddress(savedAddress);
            return ResponseEntity.ok(savedAddress);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<SavedAddresses>> getSavedAddressByUserId(@PathVariable Long id) {
        List<SavedAddresses> getAddressByUser = savedAddressService.getSavedAddressByUser(id);
        if (getAddressByUser != null) {
            return ResponseEntity.ok(getAddressByUser);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SavedAddresses> deleteSavedAddress(@PathVariable Long id) {
        Optional<SavedAddresses> savedAddressById = savedAddressService.getAddressByID(id);
        if (savedAddressById.isPresent()) {
            savedAddressService.deleteSavedAddress(id);
            return ResponseEntity.ok(savedAddressById.get());
        }
        return ResponseEntity.notFound().build();
    }

}
