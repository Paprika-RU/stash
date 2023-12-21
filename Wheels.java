//Пример агрегации
class Wheel {
    public void rotate() {
        System.out.println("Колесо вращается");
    }
}

class Bus {
    private List<Wheel> wheels;

    public Bus() {
        this.wheels = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            wheels.add(new Wheel()); // Создание объектов Wheel с последующим добавлением их в список
        }
    }

    public void rotateWheels() {
        for (Wheel wheel : wheels) {
            wheel.rotate(); // Вращение каждого колеса
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Bus bus = new Bus();
        Bus.rotateWheels(); // Вращение колес автобуса
    }
}
