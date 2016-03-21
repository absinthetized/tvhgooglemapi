/**
 * Copyright (C) 2016 Matteo Nunziati <eng.matteo.nunziati@gmail.com>
 *
 * This file is part of the tvhgooglemapi project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * 		http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tvh.gmaildrafter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Matteo Nunziati
 * @email eng.matteo.nunziati@loglimassimo.it
 */
public class PasswordStoreManager {
    static String propsRoot;
    private List<PasswordStore> stores;
   
    public PasswordStoreManager() {
        propsRoot = System.getProperty("user.home") + "/.gmaildrafter/";
        stores = new ArrayList<>();
        
        /*loop over all gmaildrafter folders*/
        File[] stores_folders = new File(propsRoot).listFiles(File::isDirectory);
        
        for (File stores_folder : stores_folders) {
            PasswordStore PS = new PasswordStore(stores_folder.getName());
            stores.add(PS);
        }
    }
    
    public int get_store_num() {
        return stores.size();
    }
    
    public PasswordStore get_store(int id) {
        if (id >= stores.size()) {
            return null;
        }
        
        return stores.get(id);
    }
    
    public PasswordStore get_store(String store_name) {  
        for (int i=0; i<stores.size(); i++) {
            if(stores.get(i).get_nick().equals(store_name)) {
                return stores.get(i);
            }
        }
        
        return null;
    }
    
    public int get_store_id(String store_name) {
        int id = -1;
        
        for (int i=0; i<stores.size(); i++) {
            if(stores.get(i).get_nick().equals(store_name)) {
                id = i;
                break;
            }
        }
        
        return id;
    }
    
    public PasswordStore add(String nick, Credentials credentials) {
        PasswordStore PS = new PasswordStore(nick);
        stores.add(PS);
        PS.storeLogin(credentials);
        return PS;
    }
    
    public void delete(int id) {
        stores.get(id).deleteStoredLogin();
        stores.remove(id);
    }
    
    public PasswordStore modify(int id, Credentials credentials) {
        PasswordStore store = stores.get(id);
        store.storeLogin(credentials);
        return stores.get(id);
    }
}
