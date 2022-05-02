package LIAO;

import java.awt.*;
import java.util.*;
import javax.swing.*;

public class Pen {// usage:First create an object of pen, then call the method "draw".
    int x1, x2, y1, y2 = 0;
    Graphics graphics;
    JPanel jpanel = new JPanel();
    boolean drawInfinitly = true;
    public Pen(JPanel jpanel) {
        this.graphics = jpanel.getGraphics();
    }

    public void draw(Shape shape) {
        int size = 50;
        ArrayList<ArrayList<Double>> xList = new ArrayList<ArrayList<Double>>();
        ArrayList<ArrayList<Double>> yList = new ArrayList<ArrayList<Double>>();
        double originalX = 500;
        double originalY = 500;
        double nextX = 500;
        double nextY = 500;
        double nextAngle = 0;
        int originalPoint = 0;
        double originalAngle = 0;
        char nextPoint;
        while (!shape.shapesSet.isEmpty()) {
            Shape shapeFirst = shape.shapesSet.poll();
            if(!shape.pointOrder.isEmpty()) {
            	nextPoint = shape.pointOrder.poll();
            }
            else {
            	nextPoint = 'Z';
            }
            int len = shapeFirst.size();
            for(int i=0;i<len;i++){
                if(shapeFirst.getName(i) == 'A'){
                    originalPoint = i;
                }
            }
            ArrayList<Double> x = new ArrayList<>();
            ArrayList<Double> y = new ArrayList<>();
            x.add(originalX);
            y.add(originalY);
            originalAngle -= 180;
            for (int i = 0; i < len; i++) {
                originalAngle += 180;
                double length = shapeFirst.getLength(originalPoint) * size;
                double diffAngle = 45 * shapeFirst.getAngel(originalPoint);
                if(shapeFirst.getName(originalPoint) == nextPoint) {
                	nextX = originalX;
                	nextY = originalY;
                	nextAngle = originalAngle;
                	
                }
                originalAngle -= diffAngle;
                double diffX = length * Math.cos(Math.PI * originalAngle / 180);
                double diffY = length * Math.sin(Math.PI * originalAngle / 180);
                originalX += diffX;
                originalY += diffY;
                x.add(originalX);
                y.add(originalY);
                originalPoint++;
                if (originalPoint == len) {
                    originalPoint = 0;
                }
            }
            xList.add(x);
            yList.add(y);
            originalX = nextX;
            originalY = nextY;
            originalAngle = nextAngle;
        }
        drawLine(xList, yList);
    }

    public void drawLine(ArrayList<ArrayList<Double>> xList, ArrayList<ArrayList<Double>> yList) {
        int index = 0;
        drawInfinitly = true;
        while(drawInfinitly==true) {
            int count = 0;
            this.graphics.setColor(Color.black);
            ArrayList<Double> x = xList.get(index);
            ArrayList<Double> y = yList.get(index);
            int len = x.size();
            for (int i = 0; i < len-1; i++) {
                int x1 = (int) Math.round(x.get(i));
                int y1 = (int) Math.round(y.get(i));
                int x2 = (int) Math.round(x.get(i + 1));
                int y2 = (int) Math.round(y.get(i + 1));
                graphics.drawLine(x1, y1, x2, y2);
            }
            index++;
            if(index ==xList.size()){
                index =0;
            }
            count++;
            if(count == 1) {
            	this.graphics.setColor(Color.red);
            }
            if(count ==2) {
            	this.graphics.setColor(Color.blue);
            }
        }
    }

    public void stop(){
        drawInfinitly = false;
    }
}