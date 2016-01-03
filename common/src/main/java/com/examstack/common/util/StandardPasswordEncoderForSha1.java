package com.examstack.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 2013-7-13
 * @author scar
 * sha加密算法，实现PasswordEncoder
 */
public class StandardPasswordEncoderForSha1 implements PasswordEncoder {

	@Override
	public String encode(CharSequence rwPassword) {
		// TODO Auto-generated method stub
		MessageDigest mDigest = null;
		try {
			mDigest = MessageDigest.getInstance("SHA1");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        byte[] result = mDigest.digest(rwPassword.toString().getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
         
        return sb.toString();
	}

	@Override
	public boolean matches(CharSequence rwPassword, String password) {
		// TODO Auto-generated method stub
		return rwPassword.equals(password) ? true : false;
	}

}
