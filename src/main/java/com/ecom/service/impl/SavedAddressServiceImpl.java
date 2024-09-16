package com.ecom.service.impl;

import com.ecom.entity.SavedAddresses;
import com.ecom.repositories.SavedAddressRepository;
import com.ecom.service.SavedAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SavedAddressServiceImpl implements SavedAddressService {

    @Autowired
    private SavedAddressRepository savedAddressRepository;

    @Override
    public SavedAddresses saveAddress(SavedAddresses savedAddresses) {
        return savedAddressRepository.save(savedAddresses);
    }

    @Override
    public List<SavedAddresses> getAllSavedAddresses() {
        return savedAddressRepository.findAll();
    }

    @Override
    public List<SavedAddresses> getSavedAddressByUser(Long useID) {
        return savedAddressRepository.findByUserId(useID);
    }

    @Override
    public Optional<SavedAddresses> getAddressByID(Long id) {
        return savedAddressRepository.findById(id);
    }

    @Override
    public void deleteSavedAddress(Long id) {
        savedAddressRepository.deleteById(id);
    }
}
