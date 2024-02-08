package com.vinovibes.vinoapi.services;

import com.vinovibes.vinoapi.entities.user.Otp;
import com.vinovibes.vinoapi.exceptions.AppException;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * Service for generating and validating One Time Password
 */
@Service
public class OTPService {

    /**
     * Method for generating a 6 digit OTP.
     * @return OTP
     */
    public Otp generateOTP() {
        String[] digits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
        StringBuilder otpValue = new StringBuilder();
        while (otpValue.length() < 6) {
            otpValue.append(digits[(int) (Math.random() * 10)]);
        }
        Otp otp = new Otp();
        otp.setValue(otpValue.toString());
        otp.setExpiryTime(LocalDateTime.now().plusHours(1));
        return otp;
    }

    /**
     * Method for validating an OTP.
     * @param inputOtp input OTP
     * @param userOtp user OTP
     * @return true if the OTP is valid, false otherwise
     */
    public boolean validateOTP(String inputOtp, Otp userOtp) {
        if (LocalDateTime.now().isAfter(userOtp.getExpiryTime())) {
            throw new AppException("OTP Expired", HttpStatus.BAD_REQUEST);
        }

        return inputOtp.equals(userOtp.getValue());
    }
}
