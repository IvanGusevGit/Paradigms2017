package queue;

import java.io.*;
import java.util.Random;

public class TestGenrator {
    public static void main(String[] args) throws IOException{
        ArrayQueue queue = new ArrayQueue();
        String[] commands = {"enqueue", "element", "dequeue", "size", "isEmpty", "clear"};
        StringBuilder input = new StringBuilder();
        StringBuilder output = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < 40; i++){
            int index = rnd.nextInt(6);
            if (index == 0){
                int value = rnd.nextInt(100);
                queue.enqueue(value);
                input.append("enqueue " + value + " ");
            }
            if (index == 1 && queue.size() > 0){
                int value = (Integer)queue.element();
                input.append("element ");
                output.append(value + " ");
            }
            if (index == 2 && queue.size() > 0){
                int value = (Integer)queue.dequeue();
                input.append("dequeue ");
                output.append(value + " ");
            }
            if (index == 3) {
                input.append("size ");
                output.append(queue.size() + " ");
            }
            if (index == 4){
                input.append("isEmpty ");
                output.append(queue.isEmpty() + " ");
            }
            if (index == 5){
                queue.clear();
                input.append("clear ");
            }
        }
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("test10.txt")));
        out.write(input.toString());
        out.newLine();
        out.write(output.toString());
        out.close();
    }
}
