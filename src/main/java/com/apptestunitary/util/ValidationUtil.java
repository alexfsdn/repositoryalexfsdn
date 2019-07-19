package com.apptestunitary.util;

import java.sql.Timestamp;
import java.util.List;

public class ValidationUtil {

	public static boolean isEmpty(String valor) {
		if (valor == null || valor.isEmpty())
			return true;
		else
			return false;
	}

	public static boolean isNegative(Long valor) {
		if (valor <= 0) {
			return true;
		}
		return false;
	}

	public static boolean isNegative(List<Long> valores) {
		for (Long valor : valores) {
			if (isNegative(valor)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isValidTimestamp(Timestamp timestamp) {
		if (timestamp == null || timestamp.getTime() <= 0) {
			return false;
		}

		return true;
	}
}
