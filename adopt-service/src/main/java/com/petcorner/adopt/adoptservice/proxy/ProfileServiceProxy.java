package com.petcorner.adopt.adoptservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="profile-service")
public interface ProfileServiceProxy {


    @GetMapping("/animal/userInfo")
    public String userInfo();
}
