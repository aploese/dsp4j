/*
 * DSP4J - Java classes for dsp processing, https://github.com/aploese/dsp4j/
 * Copyright (C) ${project.inceptionYear}-2019, Arne Plöse and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package de.ibapl.dsp4j.datatypes._double;

import de.ibapl.dsp4j.SampledBlock;

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
