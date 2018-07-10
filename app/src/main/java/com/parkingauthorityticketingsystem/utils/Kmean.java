package com.parkingauthorityticketingsystem.utils;

import com.parkingauthorityticketingsystem.entity.Shiftlog;

import java.util.ArrayList;
import java.util.Random;

/**
 * Kmean
 */
public class Kmean {
    private int k;// 分成多少簇
    private int m;// 迭代次数
    private int dataSetLength;// 数据集元素个数，即数据集的长度
    private ArrayList<Shiftlog> dataSet = new ArrayList<Shiftlog>();// 数据集链表
    private ArrayList<double[]> center;// 中心链表
    private ArrayList<ArrayList<Shiftlog>> cluster; // 簇
    private ArrayList<Double> jc;// 误差平方和，k越接近dataSetLength，误差越小
    private Random random;



    public void setDataSet(ArrayList<Shiftlog> dataSet) {
        this.dataSet = dataSet;
    }



    public ArrayList<ArrayList<Shiftlog>> getCluster() {
        return cluster;
    }

    public Kmean(int k) {
        if (k <= 0) {
            k = 1;
        }
        this.k = k;
    }

    /**
     * Initial
     */
    private void init() {
        m = 0;
        random = new Random();
        if (dataSet == null || dataSet.size() == 0) {
            initDataSet();
        }
        dataSetLength = dataSet.size();
        if (k > dataSetLength) {
            k = dataSetLength;
        }
        center = initCenters();
        cluster = initCluster();
        jc = new ArrayList<Double>();
    }


    private void initDataSet() {
        dataSet = new ArrayList<Shiftlog>();

    }

    private ArrayList<double[]> initCenters() {
        ArrayList<double[]> center = new ArrayList<double[]>();
        int[] randoms = new int[k];
        boolean flag;
        int temp = random.nextInt(dataSetLength);
        randoms[0] = temp;
        for (int i = 1; i < k; i++) {
            flag = true;
            while (flag) {
                temp = random.nextInt(dataSetLength);
                int j = 0;

                while (j < i) {
                    if (temp == randoms[j]) {
                        break;
                    }
                    j++;
                }
                if (j == i) {
                    flag = false;
                }
            }
            randoms[i] = temp;
        }



        // System.out.println();
        for (int i = 0; i < k; i++) {
            double[] ls = {dataSet.get(0).getX_coordinate(),dataSet.get(0).getY_coordinate()};
            center.add(ls);
        }
        return center;
    }

    private ArrayList<ArrayList<Shiftlog>> initCluster() {
        ArrayList<ArrayList<Shiftlog>> cluster = new ArrayList<ArrayList<Shiftlog>>();
        for (int i = 0; i < k; i++) {
            cluster.add(new ArrayList<Shiftlog>());
        }

        return cluster;
    }


    private double distance(Shiftlog element, double[] center) {
        double distance = 0.0f;
        double x = element.getX_coordinate() - center[0];
        double y = element.getY_coordinate() - center[1];
        double z = x * x + y * y;
        distance = (double) Math.sqrt(z);

        return distance;
    }


    private int minDistance(double[] distance) {
        double minDistance = distance[0];
        int minLocation = 0;
        for (int i = 1; i < distance.length; i++) {
            if (distance[i] < minDistance) {
                minDistance = distance[i];
                minLocation = i;
            } else if (distance[i] == minDistance) // 如果相等，随机返回一个位置
            {
                if (random.nextInt(10) < 5) {
                    minLocation = i;
                }
            }
        }

        return minLocation;
    }


    private void clusterSet() {
        double[] distance = new double[k];
        for (int i = 0; i < dataSetLength; i++) {
            for (int j = 0; j < k; j++) {
                distance[j] = distance(dataSet.get(i), center.get(j));
                // System.out.println("test2:"+"dataSet["+i+"],center["+j+"],distance="+distance[j]);

            }
            int minLocation = minDistance(distance);
            // System.out.println("test3:"+"dataSet["+i+"],minLocation="+minLocation);
            // System.out.println();

            cluster.get(minLocation).add(dataSet.get(i));// 核心，将当前元素放到最小距离中心相关的簇中

        }
    }


    private double errorSquare(Shiftlog element, double[] center) {
        double x = element.getX_coordinate() - center[0];
        double y = element.getY_coordinate() - center[1];

        double errSquare = x * x + y * y;

        return errSquare;
    }


    private void countRule() {
        double jcF = 0;
        for (int i = 0; i < cluster.size(); i++) {
            for (int j = 0; j < cluster.get(i).size(); j++) {
                jcF += errorSquare(cluster.get(i).get(j), center.get(i));

            }
        }
        jc.add(jcF);
    }

    private void setNewCenter() {
        for (int i = 0; i < k; i++) {
            int n = cluster.get(i).size();
            if (n != 0) {
                double[] newCenter = { 0, 0 };
                for (int j = 0; j < n; j++) {
                    newCenter[0] += cluster.get(i).get(j).getX_coordinate();
                    newCenter[1] += cluster.get(i).get(j).getY_coordinate();
                }
                // 设置一个平均值
                newCenter[0] = newCenter[0] / n;
                newCenter[1] = newCenter[1] / n;
                center.set(i, newCenter);
            }
        }
    }

    public void printDataArray(ArrayList<double[]> dataArray,
                               String dataArrayName) {
        for (int i = 0; i < dataArray.size(); i++) {
            System.out.println("print:" + dataArrayName + "[" + i + "]={"
                    + dataArray.get(i)[0] + "," + dataArray.get(i)[1] + "}");
        }
        System.out.println("===================================");
    }

    private void kmeans() {
        init();
        // printDataArray(dataSet,"initDataSet");
        // printDataArray(center,"initCenter");
        while (true) {
            clusterSet();
            // for(int i=0;i<cluster.size();i++)
            // {
            // printDataArray(cluster.get(i),"cluster["+i+"]");
            // }

            countRule();

            // System.out.println("count:"+"jc["+m+"]="+jc.get(m));


            if (m != 0) {
                if (jc.get(m) - jc.get(m - 1) == 0) {
                    break;
                }
            }

            setNewCenter();
            // printDataArray(center,"newCenter");
            m++;
            cluster.clear();
            cluster = initCluster();
        }

        // System.out.println("note:the times of repeat:m="+m);
    }

    public void execute() {
        long startTime = System.currentTimeMillis();
        System.out.println("kmeans begins");
        kmeans();
        long endTime = System.currentTimeMillis();
        System.out.println("kmeans running time=" + (endTime - startTime)
                + "ms");
        System.out.println("kmeans ends");
        System.out.println();
    }
    public ArrayList<double[]> getCenter() {
        return center;
    }
}
