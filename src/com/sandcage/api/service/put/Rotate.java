
package com.sandcage.api.service.put;

import com.sandcage.api.service.OutOfBoundsException;


/**
 *  Instructs the API to execute a Rotate on the associated resource/image.
 *  <p>
 *  The only member associated with an instance of this class is mandatory and
 *  must meet certain prerequisites. Checks against the said prerequisites are
 *  implemented herein. However, as these may be subject to change, please refer 
 *  to the SandCage API, service schedule-tasks, for further information.
 *
 *  @date       03/11/2016
 *  @version    0.2
 *  @see        <a href="https://www.sandcage.com/docs/0.2/schedule_tasks">schedule-tasks docs</a>
 */
public class Rotate extends Task {

    private static final int MOD = 90;

    private static final int MIN_ROTATION = 90;
    private static final int MAX_ROTATION = 270;

    private static final String ACTION = Rotate.class.getSimpleName().toLowerCase();

    // MANDATORY: CONDITIONS -> 90, 180, OR 270 (DEGREES OF CLOCKWISE ROTATION)
    private int degrees;


    /**
     *  Creates a {@link Rotate} with the given clockwise rotation.
     * 
     *  @param  degrees       the clockwise degrees to rotate the image
     * 
     *  @throws     OutOfBoundsException    if the input value was not within
     *                                      the permissible bounds
     *  @throws     NullPointerException    if a null action is provided
     */
    public Rotate(int degrees) 
            throws OutOfBoundsException, NullPointerException {
        super(ACTION);
        if(!(degrees>=MIN_ROTATION && degrees<=MAX_ROTATION && degrees%MOD==0))
            throw new OutOfBoundsException("degrees", String.valueOf(degrees), "degrees->("+degrees+"%"+MOD+"==0)==false");
        this.degrees = degrees;
    }

    /**
     *  The desired rotation, in positive clockwise degrees.
     * 
     *  @return     the degrees to rotate the image, clockwise
     */
    public int getDegrees() {
        return this.degrees;
    }
}