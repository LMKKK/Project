package Role_0509;


public class emp_0509 {
    //基本信息
    private int id;
    private String name;
    private int phone;
    private int age;
    private String sex;
    private int dept;
    private int password;
    //考勤情况
    private int att_time;
    private String att_case;
    public void set_att_time_0509(int x){
        this.att_time=x;
    }
    public int getAtt_time(){
        return att_time;
    }
    public void set_att_case(String y){
        this.att_case=y;
    }
    public String getAtt_case(){
        return att_case;
    }
    //工资信息
    private int salary;
    public void set_salary_0509(int salary){
        this.salary=salary;
    }
    public int get_salary_0509(){
        return salary;
    }
    //评价等级
    private String eval;
    public void set_eval_0509(String eval){
        this.eval=eval;
    }
    public String get_eval_0509(){
        return eval;
    }

    public void set_id_0509(int id) {
        this.id = id;
    }

    public void set_name_0509(String name) {
        this.name = name;
    }

    public void set_phone_0509(int ph) {
        this.phone = ph;
    }

    public void set_age_0509(int age) {
        this.age = age;
    }

    public void set_sex_0509(String sex) {
        this.sex = sex;
    }
    public void set_dept_0509(int x){
        this.dept=x;
    }
    public int get_id_0509() {
        return id;
    }
    public int get_age_0509(){
        return age;
    }
    public String get_name_0509(){
        return name;
    }
    public String get_sex_0509(){
        return sex;
    }
    public int get_dept_0509(){
        return dept;
    }
    public int get_phone_0509(){
        return phone;
    }
}
