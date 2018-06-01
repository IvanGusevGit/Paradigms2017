package search;

public class BinarySearchSpan {

    //Pre: elements != null && elements[i] >= elements[i + 1] for i = leftBorder .. rightBorder && leftBorder <= rightBorder &&
    //     leftBorder >= 0 && rightBorder < elements.length && (priority == "left" || priority == "right")
    //Post: elements == elements' && elements[i] == elements'[i] for i = leftBorder .. rightBorder &&
    //      && leftBorder == leftBorder' && rightBorder == rightBorder' && value == value' && priority == priority'
    //      priority == "left": R = min(i: elements[i] == value i == leftBorder .. rightBorder)| elements[leftBorder] == value
    //                              insertionPoint   && leftBorder <= R <= rightBorder + 1     | elements[leftBorder] < value || elements[rightBorder] > value
    //                              null                        | elements[leftBorder] > value && elements[rightBorder] <= value
    //      priority == "right":R = max(i: elements[i] == value  i = leftBorder .. rightBorder)| elements[rightBorder] == value
    //                              insertionPoint - 1 && leftBorder <= R <= rightBorder + 1   | elements[leftBorder] < value || elements[rightBorder] > value
    //                              null                                                       | elements[leftBorder] >= value &&
    //                                                                                           && elements[rightBorder] < value
    //
    private static Integer checkBorders(int[] elements, int leftBorder, int rightBorder, int value, String priority){
        //Pre && constArguments = {elements[i] == elements'[i] for i = leftBorder .. rightBorder && elements == elements' &&
        //    && leftBorder == leftBorder' && rightBorder == rightBorder' && value == value' && priority == priority'}
        if (priority.equals("left")) {
            // Pre && constArguments && priority == "left"
            if (elements[leftBorder] <= value) {
                // Pre && constArguments && priority == "left" && elements[leftBorder] <= value =>
                //     => R = leftBorder = min(i: elements[i] == value)
                return leftBorder;
                // Post
            }
            if (elements[rightBorder] > value) {
                // Pre && constArguments && priority == "left" && elements[rightBorder] > value =>
                //     => R = rightBorder + 1 = insertionPoint
                return rightBorder + 1;
                // Post
            }
        } else {
            // Pre && constArguments && priority == "right"
            if (elements[leftBorder] < value) {
                // Pre && constArguments && priority == "right" && elements[leftBorder] < value =>
                //     => R = leftBorder - 1 = insertionPoint - 1
                return leftBorder - 1;
                // Post
            }
            if (elements[rightBorder] >= value) {
                // Pre && constArguments && priority == "right" && elements[rightBorder] >= value =>
                //     => R = rightBorder == (insertionPoint - 1|| max(i: elements[i] == value)| elements[rightBorder] == value)
                return rightBorder;
                // Post
            }
        }
        // Pre && constArguments &&
        //     priority == "left": elements[leftBorder] > value && elements[rightBorder] <= value ==> R = null
        //     priority == "right": elements[leftBorder] >= value && elements[rightBorder] < value ==> R = null
        return null;
        // Post
    }

    //Pre: elements != null && elements[i] >= elements[i + 1] for i = leftBorder .. rightBorder && leftBorder <= rightBorder &&
    //     leftBorder >= 0 && rightBorder < elements.length && (priority == "left" || priority == "right")
    //Post: Pre && value == value'&& priority == priority' && elements[i] == elements'[i] for i = leftBorder .. rightBorder &&
    //      && elements == elements' && R = leftAnswer = min(i: elements[i] == value && i = leftBorder .. rightBorder)| priority == "left"
    //                                      rightAnswer = max(i: elements[i] == value && i = leftBorder .. rightBorder) | priority == "right"
    //                                      insertAnswer = insertionPoint for segment[LeftBorder, rightBorder]|
    //                                          if such element does not exists
    public static int cycleSearch(int[] elements, int leftBorder, int rightBorder, int value, String priority) {
        //Pre && constArguments = {leftBorder == leftBorder' && rightBorder == rightBorder' && value == value' && priority == priority'
        //    && elements[i] == elements'[i]  for i = leftBorder .. rightBorder && elements == elements' &&
        //    && elements[i] >= elements[i + 1] for i = leftBorder .. rightBorder}
        Integer checkResult = checkBorders(elements, leftBorder, rightBorder, value, priority);
        //Pre && constArguments && (checkResult == null || checkResult == rightAnswer || checkResult == leftAnswer ||
        //    || checkResult == insertAnswer)
        if (checkResult != null) {
            //Pre && constArguments && (checkResult == leftAnswer || checkResult == rightAnswer)
            //    priority == "left" : checkResult == leftAnswer => R = checkResult
            //    priority == "right": checkResult == rightAnswer => R = checkResult
            return checkResult;
            // Post
        }

        //Pre && constArguments && checkResult == null =>
        //Pre && constArguments && ((elements[leftBorder] > value && elements[rightBorder] <= value && priority == "left") ||
        //    || (elements[leftBorder] >= value && elements[rightBorder] < value && priority == "right"))
        int left = leftBorder;
        int right = rightBorder;
        //Pre && constArguments && checkResult == null =>
        //Pre && left == leftBorder && right == rightBorder && Inv = {constArguments && ((elements[leftBorder] > value &&
        //    && elements[rightBorder] <= value && priority == "left") || (elements[left] >= value && elements[right] < value
        //    && priority == "right")) && left >= leftBorder && right <= rightBorder}
        while (right - left > 1) {
            // Pre && Inv
            int middle = (left + right) / 2;
            // Pre && Inv && middle = (left + right) / 2 && left == left' && right == right'
            if ((priority.equals("left") && elements[middle] <= value) || (priority.equals("right") && elements[middle] < value)) {
                //Pre && Inv && middle == (left + right) / 2 && left <= middle <= right && left == left' && right == right'
                //       priority == "left" && elements[middle] <= value ==> elements[left] > value >= elements[middle]
                //       priority == "right"&& elements[middle] < value  ==> elements[left] >= value > elements[middle]
                right = middle;
                //Pre && Inv && right == middle && left == left' && middle == middle'
            } else {
                //Pre && Inv && middle == (left + right) / 2 && left <= middle <= right && left == left' && right == right'
                //       priority == "left" && elements[middle] > value ==> elements[middle] > value >= elements[right]
                //       priority == "right"&& elements[middle] < value  ==> elements[middle] >= value > elements[right]
                left = middle;
                //Pre && Inv && right == right' && left == middle && middle == middle'
            }
            //Pre && Inv => Post
        }

        // Pre && Inv && right - left <= 1
        if (priority.equals("left")) {
            // Pre && Inv && (priority == "left") => R = leftAnswer = right
            return right;
            // Post
        } else {
            // Pre && Inv && (priority == "right") => R = rightAnswer = left
            return left;
            // Post
        }
    }

    //Pre: elements != null && elements[i] >= elements[i + 1] for i = leftBorder .. rightBorder && leftBorder <= rightBorder &&
    //     leftBorder >= 0 && rightBorder < elements.length && (priority == "left" || priority == "right")
    //Post: Pre && value == value'&& priority == priority' && elements[i] == elements'[i] for i = leftBorder .. rightBorder &&
    //      && elements == elements' && R = leftAnswer = min(i: elements[i] == value && i = leftBorder .. rightBorder) | priority == "left"
    //                                      rightAnswer = max(i: elements[i] == value && i = leftBorder .. rightBorder) | priority == "right"
    //                                      insertAnswer = insertionPoint for segment[LeftBorder, rightBorder] |
    //                                              if such element does not exist
    public static int recursiveSearch(int[] elements, int leftBorder, int rightBorder, int value, String priority) {
        //Pre && constArguments = {leftBorder == leftBorder' && rightBorder == rightBorder' && value == value' && priority == priority'
        //    && elements[i] == elements'[i]  for i = leftBorder .. rightBorder && elements == elements' &&
        //    && elements[i] >= elements[i + 1] for i = leftBorder .. rightBorder}
        Integer checkResult = checkBorders(elements, leftBorder, rightBorder, value, priority);
        //Pre && constArguments && (checkResult == null || checkResult == rightAnswer || checkResult == leftAnswer ||
        //    || checkResult == insertAnswer)
        if (checkResult != null) {
            // Pre && constArguments && (checkResult == leftAnswer || checkResult == rightAnswer) => R = checkResult
            return Integer.valueOf(checkResult);
            // Post
        }
        // Pre && constArguments && Inv = {(priority == "left" && elements[leftBorder] > value && elements[rightBorder] <= value) ||
        //     || (priority == "right" && elements[leftBorder] >= value && elements[rightBorder] < value)}
        if (rightBorder - leftBorder <= 1) {
            // Pre && constArguments && Inv && rightBorder - leftBorder <= 1
            if (priority.equals("left")){
                // Pre && constArguments && Inv && rightBorder - leftBorder <= 1 && priority == "left" =>
                //     => R = leftAnswer = rightBorder
                return rightBorder;
                // Post
            } else {
                // Pre && constArguments && Inv && rightBorder - leftBorder <= 1 && priority == "right" =>
                //     => R = rightAnswer = leftBorder
                return leftBorder;
                // Post
            }
        } else {
            // Pre && constArguments && Inv && rightBorder - leftBorder > 1
            int middle = (leftBorder + rightBorder) / 2;
            // Pre && constArguments && Inv && rightBorder - leftBorder > 1 && middle = (leftBorder + rightBorder) / 2 &&
            //     && leftBorder < middle < rightBorder && leftBorder < middle < rightBorder
            if ((priority.equals("left") && elements[middle] <= value) || (priority.equals("right") && elements[middle] < value)) {
                // Pre && constArguments && Inv && rightBorder - leftBorder > 1 && middle = (leftBorder + rightBorder) / 2 &&
                //     && leftBorder < middle < rightBorder
                //        priority == "left": elements[middle] <= value => elements[leftBorder] > value >= elements[middle] = >
                //                            => R = leftAnswer = recursiveSearch(elements, leftBorder, middle, value, priority)
                //        priority == "right": elements[middle] < value => elements[leftBorder] >= value > elements[middle] = >
                //                           => R = leftAnswer = recursiveSearch(elements, leftBorder, middle, value, priority)
                return recursiveSearch(elements, leftBorder, middle, value, priority);
                // Post
            } else {
                // Pre && constArguments && Inv && rightBorder - leftBorder > 1 && middle = (leftBorder + rightBorder) / 2 &&
                //     && leftBorder < middle < rightBorder
                //        priority == "left": elements[middle] > value => elements[middle] > value >= elements[rightBorder] = >
                //                            => R = leftAnswer = recursiveSearch(elements, middle, rightBorder, value, priority)
                //        priority == "right": elements[middle] >= value => elements[middle] >= value > elements[rightBorder] = >
                //                           => R = leftAnswer = recursiveSearch(elements, middle, rightBorder, value, priority)
                return recursiveSearch(elements, middle, rightBorder, value, priority);
                // Post
            }
        }
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("0 0");
            return;
        }

        try {
            int pattern = Integer.parseInt(args[0]);
            int[] elements = new int[args.length - 1];
            for (int i = 1; i < args.length; i++) {
                elements[i - 1] = Integer.parseInt(args[i]);
            }

            int leftBorder = recursiveSearch(elements, 0, elements.length - 1, pattern, "left");
            int rightBorder = recursiveSearch(elements, 0, elements.length - 1, pattern, "right");
            System.out.println(leftBorder + " " + (rightBorder - leftBorder + 1));
        } catch (NumberFormatException e) {
            System.out.println("Wrong format found.");
        }
    }
}