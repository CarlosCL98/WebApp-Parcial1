package edu.eci.pdsw.validator;

import static org.quicktheories.QuickTheory.qt;

import java.util.Optional;

import org.junit.Test;

import edu.eci.pdsw.model.SocialSecurityType;
import edu.eci.pdsw.validator.ErrorType;

/**
 * Test class for {@linkplain SalaryValidator} class
 */
public class SalaryValidatorTest {

	/**
	 * The class under test.
	 */
	private SalaryValidator validator = new SalaryValidator();

	/**
	 * {@inheritDoc}}
	 */
	@Test
	public void validateTest() {

		qt().forAll(EmployeeGenerator.empleados()).check(empleado -> {
			boolean result = false;
			Optional<ErrorType> op = validator.validate(empleado);
			if (op.isPresent()) {
				ErrorType e = op.get();
				long salary = empleado.getSalary();
				SocialSecurityType socialSecurityType = empleado.getSocialSecurityType();
				switch (e) {
				case NEGATIVE_SALARY:
					if (salary < 0)
						result = true;
					break;
				case INVALID_SALARY:
					if ((0 <= salary && salary < 100) || salary > 50000)
						result = true;
					break;
				case INVALID_SISBEN_AFFILIATION:
					if (salary >= 1500 && socialSecurityType == SocialSecurityType.SISBEN)
						result = true;
					break;
				case INVALID_EPS_AFFILIATION:
					if ((salary >= 10000 || salary < 1500) && socialSecurityType == SocialSecurityType.EPS)
						result = true;
					break;
				case INVALID_PREPAID_AFFILIATION:
					if (salary < 10000 && socialSecurityType == SocialSecurityType.PREPAID)
						result = true;
					break;
				}
			} else
				result = true;
			return result;
		});
	}
}
