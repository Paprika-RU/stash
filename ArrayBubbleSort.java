import java.util.Arrays;
public class sortBabble {
    public static void main(String[] args) {
       
        int mas[] = new int[10];
        // Генерируем случайные числа и сохраняем их в массиве
        for (int i=0; i<mas.length; i++){
            mas[i] = (int) (Math.random()*10);
        }
        System.out.println("Неотсортированный массив:" + Arrays.toString(mas));
        int tmp;
        boolean statusSort = false;
        // Повторяем процесс сортировки до тех пор, пока массив не будет полностью отсортирован
        while(!statusSort){
            statusSort = true;
            // Сравниваем соседние элементы и меняем их местами при необходимости
            for(int i=0; i<mas.length-1; i++){
                if (mas[i]>mas[i+1]){
                    statusSort = false;
                    tmp = mas[i];
                    mas[i] = mas[i+1];
                    mas[i+1]=tmp;
                }
            }
        }
        System.out.println("Отсортированный массив:" + Arrays.toString(mas));
    }   
}
