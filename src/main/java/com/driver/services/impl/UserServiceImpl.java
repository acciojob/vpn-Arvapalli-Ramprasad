package com.driver.services.impl;

import com.driver.model.CountryName;
import com.driver.model.ServiceProvider;
import com.driver.model.User;
import com.driver.repository.CountryRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.repository.UserRepository;
import com.driver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository3;
    @Autowired
    ServiceProviderRepository serviceProviderRepository3;
    @Autowired
    CountryRepository countryRepository3;

    @Override
    public User register(String username, String password, String countryName) throws Exception{
        // Convert the countryName string to the CountryName enum
        CountryName countryNameEnum = CountryName.valueOf(countryName.toUpperCase());

        // Retrieve the code from the enum
        String countryCode = countryNameEnum.getCode();


        User user = new User(username,password,countryCode,false,null);

        user = userRepository3.save(user);

        return user;
    }

    @Override
    public User subscribe(Integer userId, Integer serviceProviderId) {
        User user = userRepository3.findById(userId).get();
        ServiceProvider serviceProvider = serviceProviderRepository3.findById(serviceProviderId).get();
        serviceProvider.getUsers().add(user);
        serviceProviderRepository3.save(serviceProvider);
        user.getServiceProviderList().add(serviceProvider);
        user = userRepository3.save(user);
        return user;
    }
}
