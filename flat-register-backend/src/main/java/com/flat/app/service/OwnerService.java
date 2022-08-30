package com.flat.app.service;

import java.util.List;

import com.flat.app.entity.Owner;
import com.flat.app.exception.FlatNotFoundException;
import com.flat.app.exception.FlatRegisteredException;
import com.flat.app.exception.NoUsersFoundException;

public interface OwnerService {

	public List<Owner> getAllOwners() throws NoUsersFoundException;

	public Owner createOwner(Owner owner) throws FlatRegisteredException, FlatNotFoundException;

	public Owner getOwnerById(Long ownerId) throws NoUsersFoundException;

	public Owner getOwnerByFlatId(Long flatId) throws NoUsersFoundException;

	public Owner updateOwner(Long ownerId, Owner owner)
			throws FlatRegisteredException, FlatNotFoundException, NoUsersFoundException;

	public Owner getOwnerByOwnerEmail(String ownerEmail);

	public List<Owner> getOwnerByOwnerName(String ownerName);

}
