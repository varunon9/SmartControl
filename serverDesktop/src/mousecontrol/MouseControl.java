/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mousecontrol;

import java.awt.Robot;
import java.awt.event.InputEvent;

/**
 *
 * @author varun
 */
public class MouseControl {
    Robot robot;
    public MouseControl() {
        try {
            robot = new Robot();
        }
        catch(Exception e) {
        }
    }
    public void leftClick() {
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);  
    } 
    public void rightClick() {
        robot.mousePress(InputEvent.BUTTON3_MASK);
        robot.mouseRelease(InputEvent.BUTTON3_MASK);
    }
    public void mouseMove(int x, int y) {
        robot.mouseMove(x, y);
    }
    public void mouseWheel(int wheelAmount) {
        robot.mouseWheel(wheelAmount);
    }
}
