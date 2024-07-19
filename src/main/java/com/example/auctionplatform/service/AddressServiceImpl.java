package com.example.auctionplatform.service;

import com.example.auctionplatform.converter.AddressConverter;
import com.example.auctionplatform.dao.Address;
import com.example.auctionplatform.dao.AddressRepository;
import com.example.auctionplatform.dto.AddressDTO;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 功能：
 * 作者：万礼阳
 * 日期：2024/7/18 下午4:26
 */
@Service
public class AddressServiceImpl {
    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }
    private AddressRepository addressRepository;
    public String deleteAddressById(int id){
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (optionalAddress.isPresent()) {
            addressRepository.deleteById(id);
            return "Address deleted successfully!";
        }
        return "Address not found!";
    }


    public String addNewAddress( AddressDTO newAddress){
        Address AddressDTO = AddressConverter.convertAddressDTO(newAddress);
        addressRepository.save(AddressDTO);
        return "Successfully added new address!";
    }

    public Address getAddress(int id) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (optionalAddress.isPresent()) {
            return optionalAddress.get();
        }
        return null;
    }
}