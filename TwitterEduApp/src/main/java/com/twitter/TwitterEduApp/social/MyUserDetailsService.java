package com.twitter.TwitterEduApp.social;

import com.twitter.TwitterEduApp.twitter.AuthenticatingSignInAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;

import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;


/**
 * Created by emawary on 2018-03-04.
 */

@Component
public class MyUserDetailsService  implements UserDetailsService  {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Logger logger = LoggerFactory.getLogger(AuthenticatingSignInAdapter.class);
        logger.info("MyUserDetailsService *********************************************************************"+ username);
        return new User("marcin","warycha",null);
    }
}
