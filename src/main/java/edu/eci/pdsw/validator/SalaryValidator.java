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
		
		if (employee.getSalary() < 0) {
			return Optional.of(ErrorType.NEGATIVE_SALARY);
		}
		else if (employee.getSalary() < 100 || employee.getSalary() > 50000) {
			return Optional.of(ErrorType.INVALID_SALARY);
		}
		else if (employee.getSocialSecurityType() == SocialSecurityType.SISBEN) {
			if (employee.getSalary() >= 1500) {
				return Optional.of(ErrorType.INVALID_SISBEN_AFFILIATION);
			}
		}
		else if (employee.getSocialSecurityType() == SocialSecurityType.EPS) {
			if (employee.getSalary() >= 10000) {
				return Optional.of(ErrorType.INVALID_SISBEN_AFFILIATION);
			}
		}
		else if (employee.getSocialSecurityType() == SocialSecurityType.PREPAID) {
			if (employee.getSalary() < 10000) {
				return Optional.of(ErrorType.INVALID_PREPAID_AFFILIATION);
			}
		}
		return Optional.empty();
	}
}
