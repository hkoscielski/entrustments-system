package pl.edu.pwr.psi.entrustmentswebservice.common.util;

import java.time.LocalDateTime;

public final class DateUtil {

	private DateUtil() {
		// Constructor should be empty in order to prevent inheritance and objects creation outside class
	}

	public static int getCurrentStartAcademicYear() {
		LocalDateTime currentDateTime = LocalDateTime.now();
		int year = currentDateTime.getYear();
		LocalDateTime academicYearChangeDateTime = LocalDateTime.of(currentDateTime.getYear(), 7, 1, 0, 0);

		return currentDateTime.isBefore(academicYearChangeDateTime) ? year - 1 : year;
	}
}
