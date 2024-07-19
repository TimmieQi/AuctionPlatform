package com.example.auctionplatform.service;

import com.example.auctionplatform.dao.Address;
import com.example.auctionplatform.dto.AddressDTO;

import java.util.List;

public interface AddressService  {
    /**
     * 和删除一样，判断id是否存在，存在就用get方法获取
     */
    public Address getAddress(int id);
    public List<Address> getAllAddresses();

    /**
     * 直接添加进去就行，没什么需要判断的
     */
    public String addNewAddress(AddressDTO newAddress);

    /**
     *删除一个用户，依靠AddressRepository里面的find方法查询id是否存在，然后调用容器函数判断之后存在就删除
     */
    public String deleteAddressById(int id);

    /**
     * 导入类后判断id，然后判断是否需要更改，不能设置非空，导入之前的默认值，该多少是多少，只能改Address
     * @param newAddress
     * @return
     */
    public String updateAddressById(AddressDTO newAddress);
}
