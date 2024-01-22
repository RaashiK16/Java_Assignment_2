import java.io.*;
import java.util.*;

class EmployeeManagementSystem {
    private Map<Integer, Employee> employees = new HashMap<>();
    private String logFile = "employee_log.txt";

    public void readFromFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("-");
                if (data.length == 4) {
                    Employee employee = new Employee(data[1], data[2], data[3]);
                    employee.setEmployeeId(Integer.parseInt(data[0]));
                    employees.put(employee.getEmployeeId(), employee);
                    logData(employee, "File");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void readFromConsole() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter employee name :");
        String name = sc.nextLine();
        System.out.print("Enter employee department :");
        String department = sc.nextLine();
        System.out.print("Enter employee manager :");
        String manager = sc.nextLine();
        System.out.println("Employee details stored successfully!");

        Employee employee = new Employee(name, department, manager);
        employee.setEmployeeId(generateEmployeeId());
        employees.put(employee.getEmployeeId(), employee);
        logData(employee, "Console");

        sc.close();
    }

    private void logData(Employee employee, String source) {
        try (FileWriter fw = new FileWriter(logFile, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {

            out.println(employee.getEmployeeId() + "-" +
                    employee.getEmployeeName() + "-" +
                    employee.getEmployeeDepartment() + "-" +
                    employee.getEmployeeManager() + "-" +
                    source);

        } catch (IOException e) {
            System.out.println("Error writing to log file: " + e.getMessage());
        }
    }

    private int generateEmployeeId() {
        Random rand = new Random();
        int id = rand.nextInt(90000) + 10000;
        if (employees.containsKey(id)) {
            return generateEmployeeId();
        }
        return id;
    }

    public void displayEmployees(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("-");
                if (data.length == 5) {
                    System.out.print("Employee ID - " + data[0] + ", ");
                    System.out.print("Name - " + data[1] + ", ");
                    System.out.print("Department - " + data[2] + ", ");
                    System.out.println("Manager - " + data[3]);
                }

            }
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static void main(String[] args) {

        EmployeeManagementSystem ems = new EmployeeManagementSystem();
        //REading first from a file
        ems.readFromFile("employees.txt");
        //Reading from the console
        ems.readFromConsole();
        //displaying the list of all employees
        System.out.println("\n\nHere are details of all the employees - ");
        ems.displayEmployees("employee_log.txt");
    }
}

class Employee {
    private int employeeId;
    private String employeeName;
    private String employeeDepartment;
    private String employeeManager;

    public Employee(String name, String department, String manager) {
        this.employeeName = name;
        this.employeeDepartment = department;
        this.employeeManager = manager;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getEmployeeDepartment() {
        return employeeDepartment;
    }

    public String getEmployeeManager() {
        return employeeManager;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", employeeName='" + employeeName + '\'' +
                ", employeeDepartment='" + employeeDepartment + '\'' +
                ", employeeManager='" + employeeManager + '\'' +
                '}';
    }
}