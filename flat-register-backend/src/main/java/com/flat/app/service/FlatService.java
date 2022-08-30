package com.flat.app.service;

import java.util.List;

import com.flat.app.entity.Flat;
import com.flat.app.exception.FlatNotFoundException;
import com.flat.app.exception.NoFlatsFoundException;

public interface FlatService {

	public List<Flat> getAllFlats() throws NoFlatsFoundException;

	public Flat createFlat(Flat flat);

	public Flat getFlatById(Long flatId) throws FlatNotFoundException;

	public List<Flat> getFlatByStoreyNumber(Long storeyNumber);

}
