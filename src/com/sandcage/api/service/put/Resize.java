
package com.sandcage.api.service.put;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sandcage.api.service.OutOfBoundsException;


/**
 *  Instructs the API to execute a Resize on the associated resource/image.
 *  <p>
 *  Members associated with an instance of this class are mutually exclusive and 
 *  conditional. At least one is mandatory. All must meet certain prerequisites. 
 *  Some checks against the said prerequisites are implemented herein. 
 *  <p>
 *  More specifically, if {@link #ratio scaling ratio} is provided, then the height
 *  and width dimensions of the image will be scaled according to the given value. 
 *  If solely either an output height or width value is provided, then the missing 
 *  dimension will be calculated, and the image scaled, based on the one value 
 *  provided. If both height and width are provided then the output image dimensions
 *  will reflect these values (and may involve stretching the image towards the
 *  more dominant dimension). Last, if values are provided for all fields (width,
 *  height, and ratio) or either the width or height and ratio fields, then only 
 *  those of the width (if a width and ratio were provided), height (if a height
 *  and ratio were provided), or width and height (if a width, height, and ratio were
 *  provided) will be considered in the image resize calculation.
 *  <p>
 *  As the above may be subject to change, please refer to the SandCage API, 
 *  service schedule-tasks, for further information.
 *
 *  @date       03/11/2016
 *  @version    0.2
 *  @see        <a href="https://www.sandcage.com/docs/0.2/schedule_tasks">schedule-tasks docs</a>
 */
public class Resize extends Task {

    private static final int MIN_IMAGE_DIMENSION = 1;
    private static final int MAX_IMAGE_DIMENSION = 10000;

    private static final float MIN_IMAGE_RATIO = 0.01f;
    private static final float MAX_IMAGE_RATIO = 99.99f;

    private static final String ACTION = Resize.class.getSimpleName().toLowerCase();

    // CONDITIONAL: EITHER A width AND height, OR A ratio, MUST BE PRESENT IN THE 
    //              REQUEST; IF A width IS PROVIDED, THEN A HEIGHT MAY ALSO BE 
    //              PROVIDED, AND VICE-VERSA

    // CONDITIONAL: CONDITION -> 1..10000
    private int width;
    // CONDITIONAL: CONDITION -> 1..10000
    private int height;
    // CONDITIONAL: CONDITION -> 0.01..99.99
    private float ratio;


    /**
     *  Creates a {@link Resize} based on a given ratio.
     * 
     *  @param  ratio       the scaling ratio
     * 
     *  @throws     OutOfBoundsException    if the input value was not within
     *                                      the permissible bounds
     *  @throws     NullPointerException    if a null action is provided
     */
    @JsonCreator
    public Resize(float ratio) 
            throws OutOfBoundsException, NullPointerException {
        super(ACTION);
        if(!(ratio>=MIN_IMAGE_RATIO && ratio<=MAX_IMAGE_RATIO))
            throw new OutOfBoundsException("resize_percent", String.valueOf(ratio), "("+MIN_IMAGE_RATIO+"<="+ratio+"<="+MAX_IMAGE_RATIO+")==false");
        this.ratio = ratio;
    }

    /**
     *  Creates a {@link Resize} based on the width and/or height values provided.
     * 
     *  @param  width       the resized image width
     *  @param  height      the resized image height
     * 
     *  @throws     OutOfBoundsException    if any of the input value was not 
     *                                      within the permissible bounds
     *  @throws     NullPointerException    if a null action is provided
     */
    @JsonCreator
    public Resize(int width, int height) 
            throws OutOfBoundsException, NullPointerException {
        super(ACTION);
        if(width<MIN_IMAGE_DIMENSION && height<MIN_IMAGE_DIMENSION && (width>MAX_IMAGE_DIMENSION || height>MAX_IMAGE_DIMENSION))
            if(width<MIN_IMAGE_DIMENSION && height<MIN_IMAGE_DIMENSION)
                throw new OutOfBoundsException("width, height", String.valueOf(width)+", "+String.valueOf(height));
            else if(width>MAX_IMAGE_DIMENSION && height>MAX_IMAGE_DIMENSION)
                throw new OutOfBoundsException("width, height", String.valueOf(width)+", "+String.valueOf(height),
                    "IMAGE DIMENSIONS->("+MIN_IMAGE_DIMENSION+"<="+width+"<="+MAX_IMAGE_DIMENSION+" && "+MIN_IMAGE_DIMENSION+"<="+height+"<="+MAX_IMAGE_DIMENSION+")==false");
            else if(width>MAX_IMAGE_DIMENSION)
                throw new OutOfBoundsException("width", String.valueOf(width), "width->("+MIN_IMAGE_DIMENSION+"<="+width+"<="+MAX_IMAGE_DIMENSION+")==false");
            else if(height>MAX_IMAGE_DIMENSION)
                throw new OutOfBoundsException("height", String.valueOf(height), "height->("+MIN_IMAGE_DIMENSION+"<="+height+"<="+MAX_IMAGE_DIMENSION+")==false");
        this.width = width;
        this.height = height;
    }

    /**
     *  The desired output width.
     * 
     *  @return     the output width
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public int getWidth() {
        return this.width;
    }

    /**
     *  The desired output height.
     * 
     *  @return     the output height
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public int getHeight() {
        return this.height;
    }

    /**
     *  The desired output ratio, with respect to the source image.
     * 
     *  @return     the output ratio
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public float getResize_percent() {
        return this.ratio;
    }
}