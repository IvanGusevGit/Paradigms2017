package search;

public class BinarySearch {
    //Pre: leftBorder <= rightBorder && numbers[i] >= numbers[i + 1] for i = leftBorder .. rightBorder - 1 &&
    //     && leftBorder <= rightBorder
    //Post: R = min(i: numbers[i] <= value for i = leftBorder .. rightBorder, rightBorder + 1) &&
    //      && numbers[i] = numbers[i]' for i = leftBorder' .. rightBorder' && leftBorder <= rightBorder && value == value'
    public static int cycleSearch(int[] numbers, int leftBorder, int rightBorder, int value) {
        // leftBorder = const && rightBorder = const && value = const
        // numbers[i] >= numbers[i + 1] for i = leftBorder .. rightBorder - 1 &&
        // && numbers[i] == numbers[i]' for i = leftBorder .. rightBorder && leftBorder <= rightBorder
        int left = leftBorder;
        int right = rightBorder;
        // numbers[i] >= numbers[i + 1] for i = leftBorder .. rightBorder - 1 &&
        // && numbers[i] == numbers[i]' for i = leftBorder .. rightBorder && leftBorder <= rightBorder &&
        // && left == leftBorder && right == rightBorder
        // Pre: numbers[i] >= numbers[i + 1] for i = leftBorder .. rightBorder - 1 &&
        //      && numbers[i] == numbers[i]' for i = leftBorder .. rightBorder && leftBorder <= rightBorder &&
        //      && left == leftBorder && right == rightBorder && Cond == {numbers[right] > value}
        if (numbers[right] > value) {
            // Pre && Cond
            return right + 1;
            // Pre && Cond => right + 1 = min(i: numbers[i] <= value for i = leftBorder .. rightBorder, rightBorder + 1) =>
            // R = min(i: numbers[i] <= value for i = leftBorder .. rightBorder, rightBorder + 1) = right + 1 &&
            // && numbers[i] = numbers[i]' for i = leftBorder' .. rightBorder' && leftBorder <= rightBorder &&
            // && right == rightBorder && left == leftBorder
        }
        // Pre: numbers[i] >= numbers[i + 1] for i = leftBorder .. rightBorder - 1 &&
        //      && numbers[i] == numbers[i]' for i = leftBorder .. rightBorder && leftBorder <= rightBorder &&
        //      && left == leftBorder && right == rightBorder  && numbers[right] <= value && Cond == {numbers[left] <= value}
        if (numbers[left] <= value) {
            // Pre && Cond
            return left;
            // Pre && Cond => left = min(i: numbers[i] <= value for i = leftBorder .. rightBorder, rightBorder + 1) =>
            // R = min(i: numbers[i] <= value for i = leftBorder .. rightBorder, rightBorder + 1) = left &&
            // && numbers[i] = numbers[i]' for i = leftBorder' .. rightBorder' && leftBorder <= rightBorder &&
            // && right == rightBorder && left == leftBorder
        }
        // Inv = {numbers[i] == numbers[i]' for i = leftBorder .. rightBorder &&
        //        && numbers[i] >= numbers[i + 1] for i = leftBorder .. rightBorder - 1
        //        && left <= right && numbers[left] > value && numbers[right] <= value && value == value' &&
        //        && left >= leftBorder && right <= rightBorder}
        while (right - left > 1) {
            // Inv
            int middle = (right + left) / 2;
            // Inv && middle == (right + left) / 2
            // Inv && middle == (right + left) / 2 && left <= middle <= right
            if (numbers[middle] <= value){
                // Inv && middle == (right + left) / 2 && left <= middle <= right && Cond = {numbers[middle] <= value}
                right = middle;
                // Inv && cond && right == middle == (left + right) / 2 && left == left'
            }
            else {
                // Inv && middle == (right + left) / 2 && left <= middle <= right && numbers[middle] > value
                left = middle;
                // Inv && left == middle == (right + left) && right == right'
            }
        }
        //(Inv && right - left <= 1) => R == right
        return right;
    }

    //Pre: leftBorder <= rightBorder && numbers[i] >= numbers[i + 1] for i = leftBorder .. rightBorder - 1
    //Post: R = min(i: numbers[i] <= value for i = leftBorder .. rightBorder, rightBorder + 1) &&
    //    //      && numbers[i] = numbers[i]' for i = leftBorder' .. rightBorder' && leftBorder <= rightBorder && value == value'
    public static int recursiveSearch(int[] numbers, int leftBorder, int rightBorder, int value) {
        // leftBorder = const && rightBorder = const && value = const
        //Pre
        if (numbers[rightBorder] > value) {
            // Pre && numbers[rightBorder] > value
            return rightBorder + 1;
            // Pre && numbers[rightBorder] > value =>
            // Pre && Post && R = rightBorder + 1
        }
        //Pre
        if (numbers[leftBorder] <= value) {
            // Pre && numbers[leftBorder] <= value
            return leftBorder;
            // Pre && numbers[leftBorder] <= value =>
            // Pre && Post && R = leftBorder
        }
        // Pre && numbers[leftBorder] > value && numbers[rightBorder] <= value
        if (rightBorder - leftBorder <= 1) {
            // Pre && numbers[leftBorder] > value && numbers[right[Border] <= value && rightBorder - leftBorder < 1
            return rightBorder;
            // Pre && numbers[leftBorder] > value && numbers[right[Border] <= value && rightBorder - leftBorder < 1 =>
            // Pre && Post && R = rightBorder
        }
        else {
            // Pre && numbers[leftBorder] > value && numbers[rightBorder] <= value && leftBorder - rightBorder > 1
            int middle = (leftBorder + rightBorder) / 2;
            // Pre && numbers[leftBorder] > value && numbers[rightBorder] <= value && leftBorder - rightBorder > 1
            // && middle = (leftBorder + rightBorder) / 2 && leftBorder <= middle <= rightBorder
            if (numbers[middle] <= value) {
                // Pre && numbers[leftBorder] > value && numbers[rightBorder] <= value && leftBorder - rightBorder > 1
                // && middle = (leftBorder + rightBorder) / 2 && leftBorder <= middle <= rightBorder && numbers[middle] <= value
                return recursiveSearch(numbers, leftBorder, middle, value);
                // Pre && numbers[leftBorder] > value && numbers[rightBorder] <= value && leftBorder - rightBorder > 1
                // && middle = (leftBorder + rightBorder) / 2 && leftBorder <= middle <= rightBorder && numbers[middle] <= value =>
                // Post && R = recursiveSearch(numbers, leftBorder, middle, value)
            } else {
                // Pre && numbers[leftBorder] > value && numbers[rightBorder] <= value && leftBorder - rightBorder > 1
                // && middle = (leftBorder + rightBorder) / 2 && leftBorder <= middle <= rightBorder && numbers[middle] > value
                return recursiveSearch(numbers, middle, rightBorder, value);
                // Pre && numbers[leftBorder] > value && numbers[rightBorder] <= value && leftBorder - rightBorder > 1
                // && middle = (leftBorder + rightBorder) / 2 && leftBorder <= middle <= rightBorder && numbers[middle] > value =>
                // Post && R = recursiveSearch(numbers, leftBorder, middle, value)
            }
        }
    }

    public static void main(String[] args) {

    }
}
