package com.flat.app.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flat.app.entity.Flat;
import com.flat.app.exception.FlatNotFoundException;
import com.flat.app.exception.NoFlatsFoundException;
import com.flat.app.repository.FlatRepository;
import com.flat.app.service.FlatService;

@Service
public class FlatServiceImpl implements FlatService {

	@Autowired
	private FlatRepository flatRepository;

	@Override
	public List<Flat> getAllFlats() throws NoFlatsFoundException {
		List<Flat> flats = flatRepository.findAll();

		if (flats.size() > 0)
			return flats;

		throw new NoFlatsFoundException("No flats exist till now");
	}

	@Override
	public Flat createFlat(Flat flat) {
		Flat _flat = flatRepository.save(flat);
		return _flat;
	}

	@Override
	public Flat getFlatById(Long flatId) throws FlatNotFoundException {
		Flat flat = flatRepository.findById(flatId)
				.orElseThrow(() -> new FlatNotFoundException("No flat found with id: " + flatId));
		return flat;
	}

	@Override
	public List<Flat> getFlatByStoreyNumber(Long storeyNumber) {
		List<Flat> flats = flatRepository.findByStoreyNumber(storeyNumber);
		return flats;
	}

}
