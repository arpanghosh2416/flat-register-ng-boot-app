package com.flat.app.generator;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;

@SuppressWarnings("deprecation")
public class FlatIdGenerator implements IdentifierGenerator, Configurable {

	private static boolean markerFlat = true;
	private static Long totalFlatNumber = 6L;
	private static Long initialFlatNumber = 0L;
	private static Long initialStoreyNumber = 1L;

	public Long getFlatId() {
		if (markerFlat) {
			markerFlat = false;
			return 0L;
		}

		final Long flatId;
		if (initialFlatNumber != totalFlatNumber) {
			flatId = 100 * initialStoreyNumber + ++initialFlatNumber;
		} else {
			initialStoreyNumber++;
			initialFlatNumber = 0L;
			flatId = 100 * initialStoreyNumber + ++initialFlatNumber;
		}
		return flatId;
	}

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		return this.getFlatId();
	}

}
