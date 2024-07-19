package com.example.auctionplatform.service;

import com.example.auctionplatform.converter.AddressConverter;
import com.example.auctionplatform.converter.AuctionItemConverter;
import com.example.auctionplatform.dao.Address;
import com.example.auctionplatform.dao.AddressRepository;
import com.example.auctionplatform.dto.AddressDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;
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
    private final AddressRepository addressRepository;
    public String deleteAddressById(int id){
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (optionalAddress.isPresent()) {
            addressRepository.deleteById(id);
            return "Address deleted successfully!";
        }
        return "Address not found!";
    }
    public String updateAddressById(AddressDTO newAddress){
        Optional<Address> optionalAddress = addressRepository.findById(newAddress.getAddressId());
        String message = "";
        if (optionalAddress.isPresent()) {
            if(newAddress.getAddress() != null){
                message+="Address set to"+newAddress.getAddress()+"\"\n";
            }
            addressRepository.save(optionalAddress.get());
        }
        return message;
    }
    public List<AddressDTO> getAllAddresses(){

        return AddressConverter.convertAddresses(addressRepository.findAll());
    }

    public String addNewAddress( AddressDTO newAddress){
        Address AddressDTO = AddressConverter.convertAddressDTO(newAddress);
        addressRepository.save(AddressDTO);
        return "Successfully added new address!";
    }

    public Address getAddress(int id) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        return optionalAddress.orElse(null);
    }
    public List<AddressDTO> getAddressesByUserId(int userId){
        return AddressConverter.convertAddresses(addressRepository.findByUserId(userId));
    }
}