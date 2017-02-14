/*
 * Copyright 2017 S. Koulouzis, Wang Junchao, Huan Zhou, Yang Hu 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.uva.sne.drip.api.service;

import java.util.logging.Level;
import java.util.logging.Logger;
import nl.uva.sne.drip.api.dao.UserDao;
import nl.uva.sne.drip.commons.types.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author S. Koulouzis
 */
@Service
public class UserService implements UserDetailsService {

    public static final String ADMIN = "ADMIN";

    @Autowired
    UserDao dao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
//            dao.deleteAll();
//            User u = new User();
//            u.setAccountNonExpired(true);
//            u.setAccountNonLocked(true);
//            Collection<GrantedAuthority> athorities = new HashSet<>();
//            GrantedAuthority ga = new SimpleGrantedAuthority("ROLE_USER");
//            athorities.add(ga);
//            u.setAthorities(athorities);
//            u.setCredentialsNonExpired(true);
//            u.setEnabled(true);
//            u.setPassword(new BCryptPasswordEncoder().encode("123"));
//            u.setUsername(username);
//            dao.save(u);
//
//            User u2 = new User();
//            u2.setAccountNonExpired(true);
//            u2.setAccountNonLocked(true);
//            athorities = new HashSet<>();
//            ga = new SimpleGrantedAuthority("ROLE_ADMIN");
//            athorities.add(ga);
//            u2.setAthorities(athorities);
//            u2.setCredentialsNonExpired(true);
//            u2.setEnabled(true);
//            u2.setPassword(new BCryptPasswordEncoder().encode("admin"));
//            u2.setUsername("admin");
//            dao.save(u2);

            User user = dao.findByUsername(username);
            return user;
        } catch (Exception ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public UserDao getDao() {
        return dao;
    }
}
