package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.ConnectionRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConnectionServiceImpl implements ConnectionService {
    @Autowired
    UserRepository userRepository2;
    @Autowired
    ServiceProviderRepository serviceProviderRepository2;
    @Autowired
    ConnectionRepository connectionRepository2;

    @Override
    public User connect(int userId, String countryName) throws Exception{

        User user  = userRepository2.findById(userId).get();
        if(connectionRepository2.findById(userId)!=null){
            throw new Exception("Already connected");
        }
        else if(CountryName.valueOf(countryName.toUpperCase()).equals(countryName)){
            return user;
        }
        else{
            for(ServiceProvider serviceProvider: user.getServiceProviderList()){
                if(serviceProvider.getUsers().contains(userId)){
                    Connection connection = new Connection(user,serviceProvider);

                    // Convert the countryName string to the CountryName enum
                    CountryName countryNameEnum = CountryName.valueOf(countryName.toUpperCase());

                    // Retrieve the code from the enum
                    String countryCode = countryNameEnum.getCode();

                    user.setMaskedIp(countryCode);
                    user.setConnected(Boolean.TRUE);
                    user = userRepository2.save(user);
                    return user;
                }
            }
        }

        throw  new Exception("Unable to connect");
    }
    @Override
    public User disconnect(int userId) throws Exception {

        return null;
    }
    @Override
    public User communicate(int senderId, int receiverId) throws Exception {
        return null;
    }
}
