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
    private IMonitor monitor;

    public BallRunnable(Ball ball, IMonitor monitor) {
        this.ball = ball;
        this.monitor = monitor;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {

                if (ball.getColor() == Color.RED && ball.isEnteringCs()) {
                    monitor.enterReader();
                }
                if (ball.getColor() == Color.RED && ball.isLeavingCs()) {
                    monitor.exitReader();
                }

                if (ball.getColor() == Color.BLUE && ball.isEnteringCs()) {
                    monitor.enterWriter();
                }
                if (ball.getColor() == Color.BLUE && ball.isLeavingCs()) {

                    monitor.exitWriter();
                }

                ball.move();

                Thread.sleep(ball.getSpeed());

            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
