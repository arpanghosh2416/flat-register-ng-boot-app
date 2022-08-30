package com.flat.app.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flat.app.entity.Flat;
import com.flat.app.entity.Owner;
import com.flat.app.exception.FlatNotFoundException;
import com.flat.app.exception.FlatRegisteredException;
import com.flat.app.exception.NoUsersFoundException;
import com.flat.app.repository.FlatRepository;
import com.flat.app.repository.OwnerRepository;
import com.flat.app.service.FlatService;
import com.flat.app.service.OwnerService;

@Service
public class OwnerServiceImpl implements OwnerService {

	@Autowired
	private FlatService flatService;

	@Autowired
	private FlatRepository flatRepository;

	@Autowired
	private OwnerRepository ownerRepository;

	@Override
	public List<Owner> getAllOwners() throws NoUsersFoundException {
		List<Owner> owners = ownerRepository.findAll();

		if (owners.size() > 0)
			return owners;

		throw new NoUsersFoundException("No owners exist till now");
	}

	@Override
	public Owner createOwner(Owner owner) throws FlatRegisteredException, FlatNotFoundException {
		List<Flat> flats = owner.getFlats();

		for (Flat flat : flats) {
			Flat _flat = flatService.getFlatById(flat.getFlatId());
			if (_flat.getStoreyNumber() != flat.getStoreyNumber())
				throw new FlatNotFoundException("Flat of id " + _flat.getFlatId() + " is not present in storey number "
						+ flat.getStoreyNumber());
			if ((_flat.getOwner() != null) && (_flat.getFlatId() != 0))
				throw new FlatRegisteredException("Flat is already registered");
		}

		owner.setFlats(null);
		Owner _owner = ownerRepository.save(owner);

		flats.forEach(e -> e.setOwner(_owner));
		flatRepository.saveAll(flats);

		_owner.setFlats(flats);
		return _owner;
	}

	@Override
	public Owner getOwnerById(Long ownerId) throws NoUsersFoundException {
		Owner owner = ownerRepository.findById(ownerId).orElse(null);

		if (owner == null)
			throw new NoUsersFoundException("No owner found with id: " + ownerId);

		return owner;
	}

	@Override
	public Owner getOwnerByFlatId(Long flatId) throws NoUsersFoundException {
		Owner owner = ownerRepository.findByFlatId(flatId).orElse(null);

		if (owner == null)
			throw new NoUsersFoundException("No owner found with this flat information");

		return owner;
	}

	@Override
	public Owner updateOwner(Long ownerId, Owner owner)
			throws FlatRegisteredException, FlatNotFoundException, NoUsersFoundException {
		Owner _owner = this.getOwnerById(ownerId);

		_owner.setFlats(owner.getFlats());
		return this.createOwner(_owner);
	}

	@Override
	public Owner getOwnerByOwnerEmail(String ownerEmail) {
		Owner owner = ownerRepository.findByOwnerEmail(ownerEmail).orElse(null);
		return owner;
	}

	@Override
	public List<Owner> getOwnerByOwnerName(String ownerName) {
		List<Owner> owners = ownerRepository.findByOwnerName(ownerName);
		return owners;
	}

}
