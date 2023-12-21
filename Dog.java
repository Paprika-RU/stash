public class Dog {
    private String name;
    private int age;

    public Dog(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() { // геттер для получения имени
        return name;
    }

    public void setName(String name) { // сеттер для установки имени
        this.name = name;
    }

    public int getAge() { // геттер для получения возраста
        return age;
    }

    public void setAge(int age) { // сеттер для установки возраста
        if (age > 0 && age <= 16) {
            this.age = age;
        } else {
            System.out.println("Возраст задан некорректно");
        }
    }
}
