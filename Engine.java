//Пример композиции
class Engine {
    public void startEngine() {
        System.out.println("Запуск двигателя");
    }
}

class Bus {
    private Engine engine;

    public Bus() {
        this.engine = new Engine(); // Создание объекта Engine внутри класса Bus
    }

    public void startBus() {
        engine.startEngine(); // Использование функциональной возможности двигателя
    }
}

public class Main {
    public static void main(String[] args) {
        Bus bus = new Bus();
        bus.startBus(); // Запуск автобуса
    }
}
