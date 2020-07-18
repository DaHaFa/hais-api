package com.dahafa.hais.manager;

import javax.ejb.Remote;

import com.dahafa.hais.CrudService;
import com.dahafa.hais.model.AuthorizationRole;


@Remote
public interface AuthorizationRoleService extends CrudService<Long, AuthorizationRole> {

}
