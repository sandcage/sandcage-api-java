
package com.sandcage.api.service.put;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.sandcage.api.service.OutOfBoundsException;


/**
 *  Instructs the API to Crop the associated resource/image based on the
 *  provided coordinates.
 *  <p>
 *  All members associated with an instance of this class are mandatory. All must
 *  meet certain prerequisites. Some checks against the said prerequisites are 
 *  implemented herein. However, as these may be subject to change, please refer 
 *  to the SandCage API, service schedule-tasks, for further information.
 *
 *  @date       03/11/2016
 *  @version    0.2
 *  @see        <a href="https://www.sandcage.com/docs/0.2/schedule_tasks">schedule-tasks docs</a>
 */
public class Crop extends Task {

    private static final int MIN_COORDINATE = 1;

    private static final String ACTION = Crop.class.getSimpleName().toLowerCase();

    // MANDATORY: CONDITIONS -> MUST BE EQUAL TO OR GREATER THAN ONE AND LESS THAN x2
    private int x1;
    // MANDATORY: CONDITIONS -> MUST BE EQUAL TO OR GREATER THAN ONE AND LESS THAN y2
    private int y1;
    // MANDATORY: CONDITIONS -> MUST BE GREATER THAN x1 (BUT LESS THAN OR EQUAL TO IMAGE SIZE, ON THE X-AXIS)
    private int x2;
    // MANDATORY: CONDITIONS -> MUST BE GREATER THAN y1 (BUT LESS THAN OR EQUAL TO IMAGE SIZE, ON THE Y-AXIS)
    private int y2;


    /**
     *  Creates a {@link Crop} with the given coordinates.
     * 
     *  @param  x1  the x-axis start coordinates for the crop
     *  @param  y1  the y-axis start coordinates for the crop
     *  @param  x2  the x-axis end coordinates for the crop
     *  @param  y2  the y-axis end coordinates for the crop
     * 
     *  @throws     OutOfBoundsException    if any of the input values are not
     *                                      within the permissible bounds
     *  @throws     NullPointerException    if a null action is provided
     */
    @JsonCreator
    public Crop(int x1, int y1, int x2, int y2) 
            throws OutOfBoundsException, NullPointerException {
        super(ACTION);
        if(!(x1>=MIN_COORDINATE && y1>=MIN_COORDINATE && x1<x2 && y1<y2))
            throw new OutOfBoundsException("x1,y1,x2,y2", x1+","+y1+","+x2+","+y2, 
                "x1->("+MIN_COORDINATE+"<="+x1+"<"+x2+")=="+(x1>MIN_COORDINATE && x1<x2)+"; "+
                "y1->("+MIN_COORDINATE+"<="+y1+"<"+y2+")=="+(y1>MIN_COORDINATE && y1<y2)+")");
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    /**
     *  All coordinates related to the {@link Crop} operation.
     * 
     *  @return     all coordinates required to effect the {@link Crop} operation
     */
    public String getCoords() {
        return x1+","+y1+","+x2+","+y2;
    }
}