package ru.spbau.mit;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.System.out;

public class FileCopier {

    public static void main(String[] args) throws IOException, ArrayIndexOutOfBoundsException {
        copyContents(args[0], args[1]);
        workMatrix(4, args[2]);
        workMatrix(5, args[3]);
    }

    private static void copyContents(String fileFrom, String fileTo) throws IOException {
        BufferedInputStream fin = new BufferedInputStream(new FileInputStream(fileFrom));
        BufferedOutputStream fout = new BufferedOutputStream(new FileOutputStream(fileTo));

        byte buf[] = new byte[1024];

        for (int read_code = fin.read(buf); read_code != -1; read_code = fin.read(buf)) {
            out.println(buf);
            fout.write(buf, 0, read_code);
            fout.flush();
        }
        fout.close();
//        System.out.println("Unexpected IOException while reading line.");
//        throw new IOException(e);
    }

    private static void workMatrix(int N, String filename) throws IOException {
        int[][] a = readMatrix(N, filename);
        if (N % 2 == 1) {
            StringBuilder sb = new StringBuilder(filename);
            oddSpiralOutput(a, sb.append(".spiral").toString());
        }

        int [][]src = a.clone();
        a = transpose(a);
        sortMatrix(a);
        a = transpose(a);
        printMatrix(a, new StringBuilder(filename).append(".sorted").toString());
        printMatrix(src, new StringBuilder(filename).append(".source").toString());
    }

    private static void sortMatrix(int [][]a) {
        for (int i = a.length - 1; i >= 0; --i) {
            for (int j = 0; j < i; ++j) {
                if (a[j][0] > a[j+1][0]) {
                    int[] tmp = a[j];
                    a[j] = a[j+1];
                    a[j+1] = tmp;
                }
            }
        }
    }

    private static int[][] transpose(int [][]a) {
        int [][]b = new int[a.length][a[0].length];
        for (int i = 0; i < a.length; ++i) {
            for (int j = 0; j < a[0].length; ++j) {
                b[j][i] = a[i][j];
            }
        }
        return b;
    }

    private static int[][] readMatrix(int N, String filename) throws FileNotFoundException {
        Scanner s = new Scanner(new BufferedReader(new FileReader(filename)));
        int mas[][] = new int[N][N];
        for (int i =0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                mas[i][j] = s.nextInt();
            }
        }
        return mas;
    }

    private static void oddSpiralOutput(int a[][], String filename) throws IOException {
        int N = a.length;
        assert (a[0].length == N);

        ArrayList<Integer> arr = new ArrayList<>();
        final int center = N/2;
        for (int base = center, pass_len = 1; base >= 0; --base, pass_len = pass_len += 2) {
            final int lim = base + pass_len - 1;

            // horizontal left.
            for (int i = base, j = base; j <= lim; ++j) {
                arr.add(a[i][j]);
            }

            // vertical down.
            for (int i = base+1, j = lim; i < lim; ++i) {
                arr.add(a[i][j]);
            }

            // horizontal left.
            for (int i = lim, j = lim; j > base; --j) {
                arr.add(a[i][j]);
            }

            // vertical up.
            for (int i = lim, j = base; i > base; --i) {
                arr.add(a[i][j]);
            }
        }

        FileWriter w = new FileWriter(filename);
        for (Integer i : arr) {
            w.write(i.toString());
            w.write(" ");
        }
        w.close();
    }

    private static void printMatrix(int [][]a, String filename) throws IOException {
        FileWriter w = new FileWriter(filename);
        for (int i = 0; i < a.length; ++i) {
            for (int j = 0; j < a[0].length; ++j) {
                w.write(String.valueOf(a[i][j]));
                w.write('\t');
            }
            w.write("\n");
        }
        w.close();
    }
}
