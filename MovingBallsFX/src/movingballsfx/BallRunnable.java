/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package movingballsfx;

import javafx.scene.paint.Color;

/**
 *
 * @author Peter Boots
 */
public class BallRunnable implements Runnable {

    private Ball ball;
    private Monitor monitor;

    public BallRunnable(Ball ball, Monitor monitor) {
        this.ball = ball;
        this.monitor = monitor;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
//                ball.move();
                
                if (ball.getColor() == Color.RED) {
                    monitor.enterReader();
                    ball.move();
                    monitor.exitReader();
                }

                if (ball.getColor() == Color.BLUE) {
                    monitor.enterWriter();
                    ball.move();
                    monitor.exitWriter();
                }

                Thread.sleep(ball.getSpeed());

            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
