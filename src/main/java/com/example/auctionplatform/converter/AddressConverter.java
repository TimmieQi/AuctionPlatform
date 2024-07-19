package com.example.auctionplatform.converter;

import com.example.auctionplatform.dao.Address;
import com.example.auctionplatform.dto.AddressDTO;

import java.util.ArrayList;
import java.util.List;

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
    public static List<Address> convertAddressDTOs(List<AddressDTO> addressDTOS) {
        List<Address> addressList = new ArrayList<>();
        for (AddressDTO addressDTO : addressDTOS) {
            addressList.add(convertAddressDTO(addressDTO));
        }
        return addressList;
    }
    public static List<AddressDTO> convertAddresses(List<Address> addressS) {
        List<AddressDTO> addressDTOList = new ArrayList<>();
        for (Address address : addressS) {
            addressDTOList.add(convertAddress(address));
        }
        return addressDTOList;
    }
}
