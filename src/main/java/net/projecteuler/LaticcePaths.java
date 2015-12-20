package net.projecteuler;

import java.util.concurrent.RecursiveTask;

/**
 * Created by vio on 10.10.2015.
 */
public class LaticcePaths {

    private static class LaticcePathTask extends RecursiveTask<Long> {
        private final static int BORDER = 21;
        private final int x, y;

        LaticcePathTask(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        protected Long compute() {
            if (x == BORDER && y == BORDER) {
                return (long) 1;
            } else if (x > BORDER || y > BORDER) {
                return (long) 0;
            } else {
                LaticcePathTask first = new LaticcePathTask(x + 1, y);
                LaticcePathTask second = new LaticcePathTask(x, y + 1);
                second.fork();
                return first.compute() + second.join();
            }
        }
    }


    public static void main(String[] args) {
        long[][] m = new long[LaticcePathTask.BORDER + 1][LaticcePathTask.BORDER + 1];
        m[0][1] = 1;
        for (int i = 1; i <= LaticcePathTask.BORDER; i++) {
            for (int j = 1; j <= LaticcePathTask.BORDER; j++) {
                m[i][j] = m[i - 1][j] + m[i][j - 1];
            }
        }
        for (int i = 1; i <= LaticcePathTask.BORDER; i++) {
            for (int j = 1; j <= LaticcePathTask.BORDER; j++) {
                System.out.print(m[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println(m[LaticcePathTask.BORDER][LaticcePathTask.BORDER]);
    }
}
