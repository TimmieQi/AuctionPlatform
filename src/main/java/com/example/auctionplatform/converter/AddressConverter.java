package com.example.auctionplatform.converter;

import com.example.auctionplatform.dao.Address;
import com.example.auctionplatform.dto.AddressDTO;

public class AddressConverter {
    public static Address convertAddressDTO(AddressDTO addressDTO) {
        Address address = new Address();
        address.setUserId(addressDTO.getUserId());
        address.setAddress(addressDTO.getAddress());
        address.setAddressId(addressDTO.getAddressId());
        return address;
    }
    public static AddressDTO convertAddress(Address address) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setUserId(address.getUserId());
        addressDTO.setAddress(address.getAddress());
        addressDTO.setAddressId(address.getAddressId());
        return addressDTO;
    }
}
