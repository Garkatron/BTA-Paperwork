package deus.paperwork.entities.employee;

public enum EmployeeProgressBar {
    WORK("paperwork:gui/alert"),
    REST(""),
    EAT(""),
    SOCIAL(""),
    PATHFINDING(""),
    ;
    public final String path;
    EmployeeProgressBar(String path) { this.path = path; }
}
