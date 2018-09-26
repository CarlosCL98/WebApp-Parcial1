package edu.eci.pdsw.validator;

import java.util.Optional;

import edu.eci.pdsw.model.Employee;
import edu.eci.pdsw.model.SocialSecurityType;

/**
 * Utility class to validate an employee's salary
 */
public class SalaryValidator implements EmployeeValidator {

	/**
	 * {@inheritDoc}}
	 */
	public Optional<ErrorType> validate(Employee employee) {

		Optional<ErrorType> op = Optional.empty();
		long salary = employee.getSalary();
		SocialSecurityType socialSecurityType = employee.getSocialSecurityType();

		if (salary < 0) {
			op = Optional.of(ErrorType.NEGATIVE_SALARY);
		} else if ((0 <= salary && salary < 100) || salary > 50000) {
			op = Optional.of(ErrorType.INVALID_SALARY);
		} else if (socialSecurityType == SocialSecurityType.SISBEN && salary >= 1500) {
			op = Optional.of(ErrorType.INVALID_SISBEN_AFFILIATION);
		} else if (socialSecurityType == SocialSecurityType.EPS && (salary >= 10000 || salary < 1500)) {
			op = Optional.of(ErrorType.INVALID_EPS_AFFILIATION);
		} else if (socialSecurityType == SocialSecurityType.PREPAID && salary < 10000) {
			op = Optional.of(ErrorType.INVALID_PREPAID_AFFILIATION);
		}
		return op;
	}
}
