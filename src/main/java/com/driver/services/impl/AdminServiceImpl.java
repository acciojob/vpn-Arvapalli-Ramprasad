package com.driver.services.impl;

import com.driver.model.Admin;
import com.driver.model.Country;
import com.driver.model.CountryName;
import com.driver.model.ServiceProvider;
import com.driver.repository.AdminRepository;
import com.driver.repository.CountryRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminRepository adminRepository1;

    @Autowired
    ServiceProviderRepository serviceProviderRepository1;

    @Autowired
    CountryRepository countryRepository1;

    @Override
    public Admin register(String username, String password) {
        Admin admin = new Admin(username,password);
        admin = adminRepository1.save(admin);
        return admin;
    }

    @Override
    public Admin addServiceProvider(int adminId, String providerName) {
        ServiceProvider serviceProvider = new ServiceProvider(adminId,providerName);
        Admin admin = serviceProviderRepository1.save(serviceProvider).getAdmin();
        return admin;
    }

    @Override
    public ServiceProvider addCountry(int serviceProviderId, String countryName) throws Exception{

        ServiceProvider serviceProvider1 = serviceProviderRepository1.findById(serviceProviderId).get();


        // Convert the countryName string to the CountryName enum
        CountryName countryNameEnum = CountryName.valueOf(countryName.toUpperCase());

        // Retrieve the code from the enum
        String countryCode = countryNameEnum.getCode();

        Country country = new Country(countryNameEnum,countryCode,null,serviceProvider1);

        serviceProvider1.getCountryList().add(country);

        // Save the updated ServiceProvider
        serviceProviderRepository1.save(serviceProvider1);

        return serviceProvider1;
    }
}
