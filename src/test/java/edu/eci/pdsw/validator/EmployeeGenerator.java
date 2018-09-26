package edu.eci.pdsw.validator;

import org.quicktheories.core.Gen;
import org.quicktheories.generators.*;
import static org.quicktheories.generators.SourceDSL.*;

import edu.eci.pdsw.model.Employee;
import edu.eci.pdsw.model.SocialSecurityType;

public class EmployeeGenerator {

	public static Gen<Employee> empleados() {
		return personIds().zip(salaries(), tiposSeguridadSocial(),
				(personId, salariy, socialSecurityType) -> new Employee(personId, salariy, socialSecurityType));
	}

	private static Gen<Integer> personIds() {
		return integers().from(950).upToAndIncluding(100150);
	}

	private static Gen<Long> salaries() {
		return longs().from(-5).upToAndIncluding(50150);
	}

	private static Gen<SocialSecurityType> tiposSeguridadSocial() {
		Class e = SocialSecurityType.class;
		return Generate.enumValues(e);
	}
}
