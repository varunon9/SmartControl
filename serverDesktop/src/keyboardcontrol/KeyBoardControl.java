/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keyboardcontrol;

import java.awt.Robot;
import java.awt.event.KeyEvent;

/**
 *
 * @author varun
 */
public class KeyBoardControl {
    Robot robot;
    public KeyBoardControl() {
        try {
            robot = new Robot();
        } catch (Exception e) {
        }   
    }  
    public void typeKey(char ch) {
        robot.keyPress(KeyEvent.VK_0);
        robot.delay(10);
        robot.keyRelease(KeyEvent.VK_0);
    }
    public static void main(String args[]) {
        new KeyBoardControl().typeKey('1');
    }
}
