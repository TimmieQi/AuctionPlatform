package com.example.auctionplatform.service;

import com.example.auctionplatform.converter.AddressConverter;

import com.example.auctionplatform.dao.Address;
import com.example.auctionplatform.dao.AddressRepository;
import com.example.auctionplatform.dto.AddressDTO;
import com.example.auctionplatform.logger.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*
  已重构
  YCX
 */
/**
 * 功能：
 * 作者：万礼阳
 * 日期：2024/7/18 下午4:26
 */
@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }
    private final AddressRepository addressRepository;
    @Override
    public Response<Void> deleteAddressById(int id){
        try {
            Optional<Address> optionalAddress = addressRepository.findById(id);
            if (optionalAddress.isPresent()) {
                addressRepository.deleteById(id);
                return Response.newSuccess(null,"Address deleted successfully!\n");
            }
            return Response.newError("Address with id " + id + " not found!\n");
        }
        catch (Exception e) {
            e.fillInStackTrace();
            LogManager.LogOtherError(e.getMessage() + "An error occurred while deleting address with id " + id+"\n");
            return Response.newError("An error occurred while deleting address with id " + id+"\n");
        }
    }
    @Override
    public Response<Void> updateAddressById(AddressDTO newAddress){
        try {
            Optional<Address> optionalAddress = addressRepository.findById(newAddress.getAddressId());
            if (optionalAddress.isEmpty()) {
                return Response.newError("Address with id " + newAddress.getAddressId() + " not found!\n");
            }
            if (newAddress.getAddress().isEmpty()) {
                return Response.newError("Address is empty!\n");
            }
            Address address = optionalAddress.get();
            address.setAddress(newAddress.getAddress());
            addressRepository.save(address);
            return Response.newSuccess(null, "Address updated successfully!\n");
        }
        catch (Exception e) {
            e.fillInStackTrace();
            LogManager.LogOtherError(e.getMessage() + "An error occurred while updating address id "
                    + newAddress.getAddressId()+"\n");
            return Response.newErrorWithEmptyReturn("An error occurred while updating address id "
                    + newAddress.getAddressId()+"\n");
        }
    }
    @Override
    public Response<List<AddressDTO>> getAllAddresses(){
        try {
            List<Address> addresses = addressRepository.findAll();
            if(addresses.isEmpty()){
                return Response.newErrorWithEmptyReturn("Address not found\n");
            }
            return Response.newSuccess(AddressConverter.convertAddresses(addresses),"Addresses found!\n");
        }
       catch(Exception e){
            e.fillInStackTrace();
            LogManager.LogOtherError(e.getMessage() + "An error occurred while getting addresses!\n");
            return Response.newErrorWithEmptyReturn("An error occurred while getting addresses!\n");
       }
    }
    @Override
    public Response<Void> addNewAddress(AddressDTO newAddress){
        Address address = AddressConverter.convertAddressDTO(newAddress);
        try {
            addressRepository.save(address);
        }catch (Exception e){
            e.fillInStackTrace();
            LogManager.LogOtherError(e.getMessage()+ "An error occurred while adding new address!\n");
            return Response.newError("Address cannot be added!\n");
        }

        return Response.newSuccess(null,"Address added successfully!\n");
    }
    @Override
    public Response<Address> getAddress(int id) {
        try {
            Optional<Address> optionalAddress = addressRepository.findById(id);
            return optionalAddress.map(address -> Response.newSuccess(address, "Address found!\n"))
                    .orElseGet(() -> Response.newErrorWithEmptyReturn("Address with id "+id+"not found!"));
        }
        catch (Exception e){
            e.fillInStackTrace();
            LogManager.LogOtherError(e.getMessage() + "An error occurred while fetching address\n");
            return Response.newErrorWithEmptyReturn("An error occurred while fetching address. Please try again later.\n");
        }
    }
    @Override
    public Response<List<AddressDTO>> getAddressesByUserId(int userId){
        try {
            List<Address> addresses = addressRepository.findByUserId(userId);
            if (addresses.isEmpty()) {
                return Response.newErrorWithEmptyReturn("Address not found!\n");
            }
            return Response.newSuccess(AddressConverter.convertAddresses(addresses), "Addresses found!\n");
        }
        catch (Exception e){
            e.fillInStackTrace();
            LogManager.LogOtherError(e.getMessage() + "An error occurred while fetching addresses\n");
            return Response.newErrorWithEmptyReturn("An error occurred while fetching addresses. Please try again later.\n");
        }
    }
}