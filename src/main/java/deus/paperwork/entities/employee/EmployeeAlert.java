package deus.paperwork.entities.employee;

public enum EmployeeAlert {
	GENERIC("paperwork:gui/icons/employe/alerts/generic"),
	MONSTER("paperwork:gui/icons/employe/alerts/monster"),
	DANGEROUS_ENV("paperwork:gui/icons/employe/alerts/dangerous_env"),
	NO_HOME("paperwork:gui/icons/employe/alerts/no_home"),
	NO_WORKPLACE("paperwork:gui/icons/employe/alerts/no_workplace"),
	NO_FOOD_PLACE("paperwork:gui/icons/employe/alerts/no_food_place"),
	NO_REST_PLACE("paperwork:gui/icons/employe/alerts/no_rest_place"),
	BAD_WORK_ENV("paperwork:gui/icons/employe/alerts/bad_work_env"),
	GREETING_BOSS("paperwork:gui/icons/employe/alerts/greeting_boss"),
	GREETING("paperwork:gui/icons/employe/alerts/greeting"),
	;
	public final String path;
	EmployeeAlert(String path) { this.path = path; }
}
