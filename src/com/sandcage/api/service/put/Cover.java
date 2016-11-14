
package com.sandcage.api.service.put;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sandcage.api.service.OutOfBoundsException;


/**
 *  Instructs the API to execute a Cover on the associated resource/image, based 
 *  on the provided coordinates.
 *  <p>
 *  Most members associated with an instance of this class are mandatory. All must
 *  meet certain prerequisites. Some checks against the said prerequisites are 
 *  implemented herein. However, as these may be subject to change, please refer 
 *  to the SandCage API, service schedule-tasks, for further information.
 * 
 *  @date       03/11/2016
 *  @version    0.2
 *  @see        <a href="https://www.sandcage.com/docs/0.2/schedule_tasks">schedule-tasks docs</a>
 */
public class Cover extends Task {

    public static final String COVER_X_LEFT = "left";
    public static final String COVER_X_CENTER = "center";
    public static final String COVER_X_RIGHT = "right";
    public static final String COVER_Y_TOP = "top";
    public static final String COVER_Y_MIDDLE = "middle";
    public static final String COVER_Y_BOTTOM = "bottom";

    private static final int MIN_COORDINATE = 0;

    private static final String ACTION = Cover.class.getSimpleName().toLowerCase();

    // CONDITIONAL: EITHER A width AND height, OR A ratio, MUST BE PRESENT IN THE 
    //              REQUEST; IF A width IS PROVIDED, THEN A HEIGHT MAY ALSO BE 
    //              PROVIDED, AND VICE-VERSA

    // MANDATORY: CONDITIONS -> 1..10000 (MUST BE LESS THAN OR EQUAL TO THE SOURCE IMAGE WIDTH)
    private int width;
    // MANDATORY: CONDITIONS -> 1..10000 (MUST BE LESS THAN OR EQUAL TO THE SOURCE IMAGE HEIGHT)
    private int height;
    // OPTIONAL; API DEFAULTS IF NO X/Y-AXIS COORDINATES ARE PROVIDED: middle,center 
    // SUPPORTED VALUES: Y-AXIS -> top,middle,bottom; X-AXIS -> left,center,right
    private String coverCoords;


    /**
     *  Creates a {@link Cover} with the given parametrics.
     * 
     *  @param  width   the width of the cover operation
     *  @param  height  the height of the cover operation
     *  @param  xCoord  the x-axis position at which to effect the cover operation
     *  @param  yCoord  the y-axis position at which to effect the cover operation
     * 
     *  @throws     OutOfBoundsException    if any of the input values are not
     *                                      within the permissible bounds
     *  @throws     NullPointerException    if a null action is provided
     */
    @JsonCreator
    public Cover(int width, int height, String xCoord, String yCoord) 
            throws OutOfBoundsException, NullPointerException {
        super(ACTION);
        if(width<=MIN_COORDINATE && width<=MIN_COORDINATE && ((xCoord!=null && yCoord==null) || (xCoord==null && yCoord!=null)))
            throw new OutOfBoundsException("width,height,yCoord,xCoord", width+","+height+","+yCoord+","+xCoord, 
                "width->("+width+">"+MIN_COORDINATE+")=="+(width>MIN_COORDINATE)+"; "+
                "height->("+height+">"+MIN_COORDINATE+")=="+(height>MIN_COORDINATE)+"; "+
                (xCoord==null&&yCoord!=null?"xCoord==null but yCoord=="+yCoord:"xCoord=="+xCoord+" but xCoord==null"));
        this.width = width;
        this.height = height;
        if(yCoord!=null && xCoord!=null)
            this.coverCoords = yCoord+","+xCoord;
    }

    /**
     *  The desired output width.
     * 
     *  @return     the output width
     */
    public int getWidth() {
        return this.width;
    }

    /**
     *  The desired output height.
     * 
     *  @return     the output height
     */
    public int getHeight() {
        return this.height;
    }

    /**
     *  The x- and y-axis position at which to effect the cover operation.
     * 
     *  @return     the position at which to effect the cover operation
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getCover() {
        return this.coverCoords;
    }
}