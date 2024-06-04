package com.driver.services;

import com.driver.model.Admin;

public interface AdminService {

    public Admin register(String username, String password);
}