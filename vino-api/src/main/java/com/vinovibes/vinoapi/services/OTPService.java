package com.vinovibes.vinoapi.services;

import org.springframework.stereotype.Service;

@Service
public class OTPService {

    public String generateOTP() {
        String[] digits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
        StringBuilder otp = new StringBuilder();
        while (otp.length() < 6) {
            otp.append(digits[(int) (Math.random() * 10)]);
        }
        return otp.toString();
    }

    public boolean validateOTP(String inputOtp, String userOtp) {
        return inputOtp.equals(userOtp);
    }
}
