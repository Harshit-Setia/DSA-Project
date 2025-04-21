// Interface with method declarations only
public interface User {
    void setName(String name);
    void setAge(int age);
    void setPass(String pass);

    String getName();
    int getAge();
    String getPass();
}

// Student class implementing the interface
class Student implements User {
    private String name;
    private int age;
    private String pass;
    private String id;
    static int count=0;

    Student(){
        count++;
        this.id="S"+count;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public String getPass() {
        return this.pass;
    }
}
class Teacher implements User {
    private String name;
    private int age;
    private String pass;
    private String id;
    static int count=0;

    Teacher(){
        count++;
        this.id="T"+count;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public String getPass() {
        return this.pass;
    }
}
