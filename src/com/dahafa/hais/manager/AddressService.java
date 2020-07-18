package com.dahafa.hais.manager;

import javax.ejb.Remote;

import com.dahafa.hais.CrudService;
import com.dahafa.hais.model.Address;


@Remote
public interface AddressService extends CrudService<Long, Address> {

}
