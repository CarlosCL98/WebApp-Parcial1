package edu.eci.pdsw.validator;

import static org.quicktheories.QuickTheory.qt;
import static org.quicktheories.generators.SourceDSL.*;

import org.junit.Test;

import edu.eci.pdsw.model.Employee;
import edu.eci.pdsw.model.SocialSecurityType;

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
		
		qt()
		  .forAll(EmployeeGenerator.empleados())
		  .check(empleado -> {		  
			  boolean result = true;
		  	if (!validator.validate(empleado).isPresent()) return true;
		  	if (empleado.getSalary() < 0) result = validator.validate(empleado).get() == ErrorType.NEGATIVE_SALARY;
		  	else if (empleado.getSalary() < 100) result = validator.validate(empleado).get() == ErrorType.INVALID_SALARY;
		  	else if (empleado.getSalary() > 50000) result = validator.validate(empleado).get() == ErrorType.INVALID_SALARY;
		  	else if (empleado.getSocialSecurityType() == SocialSecurityType.SISBEN) {
		  		if (empleado.getSalary() >= 1500) result = validator.validate(empleado).get() == ErrorType.INVALID_SISBEN_AFFILIATION;
		  	}
		  	else if (empleado.getSocialSecurityType() == SocialSecurityType.EPS) {
		  		if (empleado.getSalary() >= 10000) result = validator.validate(empleado).get() == ErrorType.INVALID_EPS_AFFILIATION;
		  	}
		  	else if (empleado.getSocialSecurityType() == SocialSecurityType.PREPAID) {
		  		if (empleado.getSalary() < 10000) result = validator.validate(empleado).get() == ErrorType.INVALID_PREPAID_AFFILIATION;
		  	}
		  	return result;					
		  });
	}
}
