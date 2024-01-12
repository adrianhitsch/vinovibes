package com.vinovibes.vinoapi.services;

import java.time.LocalDateTime;

import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.vinovibes.vinoapi.entities.Otp;
import com.vinovibes.vinoapi.exceptions.AppException;

@Service
public class OTPService {

    public Otp generateOTP() {
        String[] digits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
        StringBuilder otpValue = new StringBuilder();
        while (otpValue.length() < 6) {
            otpValue.append(digits[(int) (Math.random() * 10)]);
        }
        Otp otp = new Otp();
        otp.setToken(otpValue.toString());
        otp.setExpiryTime(LocalDateTime.now().plusHours(1));
        return otp;
    }

    public boolean validateOTP(String inputOtp, Otp userOtp) {

        if (LocalDateTime.now().isAfter(userOtp.getExpiryTime())) {
            throw new AppException("OTP Expired", HttpStatus.BAD_REQUEST);
        }

        return inputOtp.equals(userOtp.getToken());
    }
}
