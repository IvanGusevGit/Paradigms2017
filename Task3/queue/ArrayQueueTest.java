package queue;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ArrayQueueTest {
    public static void main(String[] args) {
        testObjects();
    }

    public static void testObjects(){
        int testNumber = 0;
        int passed = 0;
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(new FileInputStream("tests.txt")))){
            String inputFile;
            while ((inputFile = fileReader.readLine()) != null){
                List<String> lines = Files.readAllLines(Paths.get(inputFile));
                String input = lines.get(0);
                String output = lines.get(1);
                testNumber += 1;
                if (test(testNumber, input, output)) {
                    passed += 1;
                }
            }
            System.out.println("========================");
            System.out.println("Passed: " + passed + " / " + testNumber);
        } catch (IOException e){
            System.out.println("Test files were't read correctly.");
        }
    }

    public static boolean test(int index, String input, String output){
        System.out.print("Running test" + index + ":");
        ArrayQueue arrayQueue = new ArrayQueue();
        ArrayQueueADT arrayQueueADT = new ArrayQueueADT();
        ArrayQueueModule.clear();
        StringBuilder answer1 = new StringBuilder();
        StringBuilder answer2 = new StringBuilder();
        StringBuilder answer3 = new StringBuilder();
        String[] arguments = input.split(" ");
        for (int i = 0; i < arguments.length; i++){
            if (arguments[i].equals("enqueue")) {
                arrayQueue.enqueue(arguments[i + 1]);
                ArrayQueueADT.enqueue(arrayQueueADT, arguments[i + 1]);
                ArrayQueueModule.enqueue(arguments[i + 1]);
                i++;
            }
            if (arguments[i].equals("element")) {
                answer1.append(arrayQueue.element()).append(' ');
                answer2.append(ArrayQueueADT.element(arrayQueueADT)).append(' ');
                answer3.append(ArrayQueueModule.element()).append(' ');
            }
            if (arguments[i].equals("dequeue")) {
                answer1.append(arrayQueue.dequeue()).append(' ');
                answer2.append(ArrayQueueADT.dequeue(arrayQueueADT)).append(' ');
                answer3.append(ArrayQueueModule.dequeue()).append(' ');
            }
            if (arguments[i].equals("size")) {
                answer1.append(arrayQueue.size()).append(' ');
                answer2.append(ArrayQueueADT.size(arrayQueueADT)).append(' ');
                answer3.append(ArrayQueueModule.size()).append(' ');
            }
            if (arguments[i].equals("isEmpty")) {
                answer1.append(arrayQueue.isEmpty()).append(' ');
                answer2.append(ArrayQueueADT.isEmpty(arrayQueueADT)).append(' ');
                answer3.append(ArrayQueueModule.isEmpty()).append(' ');
            }
            if (arguments[i].equals("clear")){
                arrayQueue.clear();
                ArrayQueueADT.clear(arrayQueueADT);
                ArrayQueueModule.clear();
            }
        }
        if (answer1.toString().equals(output) && answer2.toString().equals(output) && answer3.toString().equals(output)){
            System.out.println(" OK");
            return true;
        } else {
            System.out.println(" FAILED");
            return false;
        }
    }

}