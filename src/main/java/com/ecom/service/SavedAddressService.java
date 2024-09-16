package com.ecom.service;

import com.ecom.entity.SavedAddresses;

import java.util.List;
import java.util.Optional;

public interface SavedAddressService {

    public SavedAddresses saveAddress(SavedAddresses savedAddresses);
    public List<SavedAddresses> getAllSavedAddresses();
    public List<SavedAddresses> getSavedAddressByUser(Long userId);

    Optional<SavedAddresses> getAddressByID(Long id);

    void deleteSavedAddress(Long id);
}
