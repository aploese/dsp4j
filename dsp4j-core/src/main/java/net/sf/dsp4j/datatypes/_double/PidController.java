/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.dsp4j.datatypes._double;

import net.sf.dsp4j.SampledBlock;

/**
 *
 * @author aploese
 */
public class PidController implements SampledBlock {

    private double kp;
    private double ki;
    private double kd;
    private double y;
    private double old_x;
    private double integral;
    private double derivative;
    private double dt;
    private double sampleRate;
    

    /*
     * This is basically whats done here....
     * 
     previous_error = 0
     integral = 0 
     start:
     error = setpoint - measured_value
     integral = integral + error*dt
     derivative = (error - previous_error)/dt
     output = Kp*error + Ki*integral + Kd*derivative
     previous_error = error
     wait(dt)
     goto start
     * 
     */
    public double setX(double x) {
        integral += x * dt;
        derivative = (x - old_x)/dt;
        y = kp * x + ki * integral + kd * derivative;
        return y;
    }

    @Override
    public void setSampleRate(double sampleRate) {
        this.sampleRate = sampleRate;
        this.dt = 1.0 / sampleRate;
    }

    @Override
    public double getSampleRate() {
        return sampleRate;
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the kp
     */
    public double getKp() {
        return kp;
    }

    /**
     * @param kp the kp to set
     */
    public void setKp(double kp) {
        this.kp = kp;
    }

    /**
     * @return the ki
     */
    public double getKi() {
        return ki;
    }

    /**
     * @param ki the ki to set
     */
    public void setKi(double ki) {
        this.ki = ki;
    }

    /**
     * @return the kd
     */
    public double getKd() {
        return kd;
    }

    /**
     * @param kd the kd to set
     */
    public void setKd(double kd) {
        this.kd = kd;
    }

    /**
     * @return the y
     */
    public double getY() {
        return y;
    }

    /**
     * @return the old_x
     */
    public double getOld_x() {
        return old_x;
    }

    /**
     * @return the integral
     */
    public double getIntegral() {
        return integral;
    }

    /**
     * @param integral the integral to set
     */
    public void setIntegral(double integral) {
        this.integral = integral;
    }

    /**
     * @return the derivative
     */
    public double getDerivative() {
        return derivative;
    }

    /**
     * @param derivative the derivative to set
     */
    public void setDerivative(double derivative) {
        this.derivative = derivative;
    }

    /**
     * @return the dt
     */
    public double getDt() {
        return dt;
    }
}
