package com.maw.crudsecurity.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.maw.crudsecurity.entity.Role;
import com.maw.crudsecurity.entity.User;
import com.maw.crudsecurity.repository.UserRepository;

import net.bytebuddy.utility.RandomString;

@Service
public class UserService {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    public Boolean isNameExists(String name) {
        return userRepository.existsByName(name);
    }

    public Boolean isEmailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public User findUserByName(String name) {
        return userRepository.findByName(name);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public void registerUser(User user, String siteURL) throws UnsupportedEncodingException, MessagingException {
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        Role role = roleService.findRoleByRoleName("USER");
        user.addUserRoles(role);

        String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);
        user.setEnabled(false);
        userRepository.save(user);

        sendVerificatonEmail(user, siteURL);

    }

    private void sendVerificatonEmail(User user, String siteURL)
            throws UnsupportedEncodingException, MessagingException {
        String toAddress = user.getEmail();
        String fromAddress = "anonismyothername@gmail.com";
        String senderName = "ACE Inspiration";
        String subject = "Please verify your registration";
        String content = "Dear [[name]], <br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank You, <br>"
                + "ACE Inspiration";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getName());
        String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);
    }

    public boolean verify(String verificationCode) {
    User user = userRepository.findByVerificationCode(verificationCode);
     
    if (user == null || user.isEnabled()) {
        return false;
    } else {
        user.setVerificationCode(null);
        user.setEnabled(true);
        userRepository.save(user);
         
        return true;
    }
     
}

}
