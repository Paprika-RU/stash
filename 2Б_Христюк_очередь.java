// Класс для представления очереди (queue)
class Queue
{
    private int[] arr;      // массив для хранения элементов queue
    private int front;      // front указывает на передний элемент в queue
    private int rear;       // задняя часть указывает на последний элемент в queue
    private int capacity;   // максимальная емкость queue
    private int count;      // текущий размер queue
 
    // Конструктор для инициализации queue
    Queue(int size)
    {
        arr = new int[size];
        capacity = size;
        front = 0;
        rear = -1;
        count = 0;
    }
 
    // Вспомогательная функция для удаления переднего элемента из очереди
    public int dequeue()
    {
        // проверка на опустошение queue
        if (isEmpty())
        {
            System.out.println("Underflow\nProgram Terminated");
            System.exit(-1);
        }
 
        int x = arr[front];
 
        System.out.println("Removing " + x);
 
        front = (front + 1) % capacity;
        count--;
 
        return x;
    }
 
    // Вспомогательная функция для добавления элемента в queue
    public void enqueue(int item)
    {
        // проверка на переполнение queue
        if (isFull())
        {
            System.out.println("Overflow\nProgram Terminated");
            System.exit(-1);
        }
 
        System.out.println("Inserting " + item);
 
        rear = (rear + 1) % capacity;
        arr[rear] = item;
        count++;
    }
 
    // Вспомогательная функция для возврата первого элемента queue
    public int peek()
    {
        if (isEmpty())
        {
            System.out.println("Underflow\nProgram Terminated");
            System.exit(-1);
        }
        return arr[front];
    }
 
    // Вспомогательная функция для возврата размера queue
    public int size() {
        return count;
    }
 
    // Вспомогательная функция для проверки, пуста ли queue или нет
    public boolean isEmpty() {
        return (size() == 0);
    }
 
    // Вспомогательная функция для проверки того, заполнена ли queue или нет
    public boolean isFull() {
        return (size() == capacity);
    }
}
 
class Main
{
    public static void main (String[] args)
    {
        // создаем queue емкостью 5
        Queue q = new Queue(5);
 
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
 
        System.out.println("The front element is " + q.peek());
        q.dequeue();
        System.out.println("The front element is " + q.peek());
 
        System.out.println("The queue size is " + q.size());
 
        q.dequeue();
        q.dequeue();
 
        if (q.isEmpty()) {
            System.out.println("The queue is empty");
        }
        else {
            System.out.println("The queue is not empty");
        }
    }
}
