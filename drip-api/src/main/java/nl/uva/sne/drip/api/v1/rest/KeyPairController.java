/*
 * Copyright 2017 S. Koulouzis.
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
package nl.uva.sne.drip.api.v1.rest;

import com.webcohesion.enunciate.metadata.rs.ResponseCode;
import com.webcohesion.enunciate.metadata.rs.StatusCodes;
import nl.uva.sne.drip.drip.commons.data.v1.external.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import nl.uva.sne.drip.api.exception.KeyException;
import nl.uva.sne.drip.api.exception.NotFoundException;
import nl.uva.sne.drip.api.exception.NullKeyException;
import nl.uva.sne.drip.api.service.KeyPairService;
import nl.uva.sne.drip.api.service.UserService;
import nl.uva.sne.drip.drip.commons.data.v1.external.KeyPair;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * This controller is responsible for handling user public keys. These keys can
 * be used by the provisoner to allow the user to login to the VMs from the
 * machine the keys correspond to.
 *
 * @author S. Koulouzis
 */
@RestController
@RequestMapping("/user/v1.0/keys")
@Component
@StatusCodes({
    @ResponseCode(code = 401, condition = "Bad credentials")
})
public class KeyPairController {

    @Autowired
    private KeyPairService service;

//    curl -v -X POST -F "file=@.ssh/id_dsa.pub" localhost:8080/drip-api/user_key/upload
//    /**
//     * Uploads a public key (id_dsa.pub,id_rsa.pub)
//     *
//     * @param file the public key file
//     * @return the ID of the stored public key
//     */
//    @RequestMapping(value = "/upload/private", method = RequestMethod.POST)
//    @RolesAllowed({UserService.USER, UserService.ADMIN})
//    public @ResponseBody
//    String upload(@RequestParam("file") MultipartFile file) {
//        if (!file.isEmpty()) {
//            try {
//                String originalFileName = file.getOriginalFilename();
//                String name = System.currentTimeMillis() + "_" + originalFileName;
//                byte[] bytes = file.getBytes();
//                String key = new String(bytes, "UTF-8");
//                
//                Key upk = new Key();
//                upk.setKey(key);
//                upk.setName(name);
//                KeyPair pair = new KeyPair();
//                pair.setPrivateKey(upk);
//                service.save(pair);
//                
//                return pair.getId();
//            } catch (IOException | IllegalStateException ex) {
//                Logger.getLogger(KeyPairController.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (KeyException ex) {
//                Logger.getLogger(KeyPairController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        return null;
//    }
//    curl -H "Content-KeyType: application/json" -X POST -d  '{"key":"ssh-rsa AAAAB3NzaDWBqs75i849MytgwgQcRYMcsXIki0yeYTKABH6JqoiyFBHtYlyh/EV1t6cujb9LyNP4J5EN4fPbtwKYvxecd0LojSPxl4wjQlfrHyg6iKUYB7hVzGqACMvgYZHrtHPfrdEmOGPplPVPpoaX2j+u0BZ0yYhrWMKjzyYZKa68yy5N18+Gq+1p83HfUDwIU9wWaUYdgEvDujqF6b8p3z6LDx9Ob+RanSMZSt+b8eZRcd+F2Oy/gieJEJ8kc152VIOv8UY1xB3hVEwVnSRGgrAsa+9PChfF6efXUGWiKf8KBlWgBOYsSTsOY4ks9zkXMnbcTdC+o7xspOkyIcWjv us@u\n","name":"id_rsa.pub"}' localhost:8080/drip-api/user_key/
    /**
     * Posts the Key and stores it. The Key is a container for public pair
     * contents. The public pair contents are represented in the 'pair' field.
     * All new lines in the 'pair' field have to be replaced with the '\n'
     * character.
     *
     * @param pair. The Key
     * @return the ID of the Key
     */
    @RequestMapping(method = RequestMethod.POST)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    @StatusCodes({
        @ResponseCode(code = 400, condition = "Key can't be empty")
    })
    public @ResponseBody
    String postKeyPair(@RequestBody KeyPair pair) {
        if (pair.getPrivateKey() == null && pair.getPublicKey() == null) {
            throw new NullKeyException();
        }
        service.save(pair);
        return pair.getId();
    }

    /**
     * Gets the Key.
     *
     * @param id . The ID of the Key to return
     * @return The Key
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    KeyPair get(@PathVariable("id") String id) {
        KeyPair key = service.findOne(id);
        if (key == null) {
            throw new NotFoundException();
        }
        return key;
    }

    @RequestMapping(value = "/sample", method = RequestMethod.GET)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    KeyPair geta() throws Exception {
        try {
            KeyPair pair = new KeyPair();
            Key pk = new Key();
            Map<String, String> attributes = new HashMap<>();
            attributes.put("domain_name", "Virginia");
            pk.setAttributes(attributes);
            pk.setKey("-----BEGIN RSA PRIVATE KEY-----\\nMIIEogIBAAm6AALYxkJFNzD3bfVJ4+hMY5j0/kqM9CURLKXMlYuAysnvoG8wZKx9Bedefm\\neNSse4zTg798ZA2kDMZFIrwp1Asetj8DDu5fhG5DjyI3g6iJltS5zFQdMXneDlHXBX8cncSzNY\\nRx0NdjEMAe7YttvI8FNlxL0VnMFli/HB/ftzYMe5+AmkSROncVGHiwoiUpj+vtobCFOYtXsCf6ri\\nd4lgWA5wv6DZT/JKCYymiBqgSXu3ueFcEzw5SAukARWVjn1xccjZkokFfBbO/FpYY00TrUTBw9S6\\nD3iM+gj8RT6EKILOmhrt71D21S95WAWIT7h2YBsy1KAvMixhNf9VaQIDAQABAoIBAHhVYK3Xl3tr\\nN1Xm0ctJTQg3ijxhR2qsUBgGUokqezpdOoD2zbbX1GLr967U9pwxzUpELexexwiTvk\\nnLv8D7ui6qbRsmc4DSsWBRSophVIVFKQmftO8Xow6x+fuYJAYmsicM1KIYHBILtL+PSzV8anenWq\\nKQ3r0tfCiQhEzKEk4b1uT3SJWQyHE++JAhVkO7lIeb6S9Dg1jAaAeMnJ/NiMxTarpPRnxe6hsTsH\\ngG1iKWo+Skcl4SknOc+CMEfyDjG4FL7MGhKduahsO8vMUrgGsDD7EH3NiX/FweB8La6qpDYAwFpC\\nycrooyhiyzw8Wb5gGaYnmvr9l70CgYEAx74O8JleXaHpxEAmh4h7VbLmJ3mOylfBmOdzcHeedJQw\\nack2SAv65WBI9S9MEQ7J/vFuyw5HNk3C/mcWgzDQXSNIhHLvl/Z9sux/Qpm3SQWLz0RBxKV3dJ4r\\nwcAxzVA93+/L1Nee+VOKnlyRumvVa6+XLsLagpap2AVcTqlerMcCgYEAx3T2pXtqkCE9eU/ov22r\\npdaKjgHoGOUg1CMEfWi/Ch6sYIIRyrHz6dhy+yR1pXNgPbLWdrn8l88F3+IsmbaMupMgRmqwEC3G\\n9Y2FglGIVvRdZaagvRxLzRCcvcN4v6OYs9ST4o1xlv7Qxphld+0XDKv7VSCv/rASuK8BqlFL3E8C\\ngYArMXJRnRjG7qh6g9TRIjZphdI3XxX9s5Rt2D8iZvuhAhqmBZjzY4PR7kxYmO2+EpCjzNnEl0XW\\n/GHaWbiIjhnAykx4N9KP7gGom3O5lzwHUme1XnFKcO2wDjQwJbufRmba8iQF1srN577mF+Z7ha4V\\nJ1duCTzvWF1KFX6sk/uhKQKBgAcDFai7rgNjJ8YcCRKxyFcMM9LKPl6hr4XFtWKzTAQPEABUkkuN\\n9gVClsg9f+VRKRECOIf0Ae1UWeCFEwxUXp4wjfHrzkTDVztKvmbWdvSXorDwKrZ7SC7tZpVFSfly\\nxuuLjadpUZT9YFmbAfY1X5oSccOMYqORjRbxEB3svb4BAoGAGTgFuq9Zojh/KIqY8b4HpEfmh6CQ\\nhLVfD98Nqd6GDbxgvIM0v4mFXE92x2jn35Ia0JdFyh3B8Vkl7sqQZfxDFXI9O9pte2mPJxY9ICaY\\n55+X/SN1pd53BH+gaPZJy/R+Vpvs5MN48ho=\\n-----END RSA PRIVATE KEY-----\\n");
            pk.setType(Key.KeyType.PRIVATE);
            pair.setPrivateKey(pk);
            return pair;
        } catch (KeyException ex) {
            Logger.getLogger(KeyPairController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }


    /**
     * Gets the IDs of all the stored Key
     *
     * @return a list of all the IDs
     */
    @RequestMapping(value = "/ids", method = RequestMethod.GET)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    List<String> getIds() {
        List<KeyPair> all = service.findAll();
        List<String> ids = new ArrayList<>();
        for (KeyPair tr : all) {
            ids.add(tr.getId());
        }
        return ids;
    }

    /**
     * Deletes a Key
     *
     * @param id. The ID of the Key to deleted.
     * @return The ID of the deleted Key
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    public @ResponseBody
    String delete(@PathVariable("id") String id) {
        service.delete(service.findOne(id));
        return "Deleted: " + id;
    }

    @RequestMapping(value = "/all", method = RequestMethod.DELETE)
    @RolesAllowed({UserService.ADMIN})
    public @ResponseBody
    String deleteAll() {
        service.deleteAll();
        return "Done";
    }

}
