https://github.com/quil/quil/wiki/List-of-available-functions-in-ClojureScript

Math
    Calculation
        abs() as quil.core/abs
        ceil() as quil.core/ceil
        constrain() as quil.core/constrain
        dist() as quil.core/dist
        exp() as quil.core/exp
        floor() as quil.core/floor
        lerp() as quil.core/lerp
        log() as quil.core/log
        mag() as quil.core/mag
        map() as quil.core/map-range
        norm() as quil.core/norm
        pow() as quil.core/pow
        round() as quil.core/round
        sq() as quil.core/sq
        sqrt() as quil.core/sqrt
    Trigonometry
        acos() as quil.core/acos
        asin() as quil.core/asin
        atan() as quil.core/atan
        atan2() as quil.core/atan2
        cos() as quil.core/cos
        degrees() as quil.core/degrees
        radians() as quil.core/radians
        sin() as quil.core/sin
        tan() as quil.core/tan
    Random
        noise() as quil.core/noise
        noiseDetail() as quil.core/noise-detail
        noiseSeed() as quil.core/noise-seed
        random() as quil.core/random
        randomSeed() as quil.core/random-seed
    Constants
        HALF_PI (1.57079...) as quil.core/HALF-PI
        PI (3.14159...) as quil.core/PI
        QUARTER_PI (0.78539...) as quil.core/QUARTER-PI
        TWO_PI (6.28318...) as quil.core/TWO-PI
        DEG-TO-RAD as (/ PI (float 180.0)))
        RAD-TO-DEG as (/ (float 180.0) PI))
Structure
    draw() as quil.core/draw
    exit() as quil.core/exit
    loop() as quil.core/start-loop
    noLoop() as quil.core/no-loop
    popStyle() as quil.core/pop-style
    redraw() as quil.core/redraw
    setup() as :setup parameter of quil.core/defsketch
    size() as :size parameter of quil.core/defsketch
Environment
    cursor() as quil.core/cursor
    focused as quil.core/focused
    frameCount as quil.core/frame-count
    frameRate() as quil.core/frame-rate
    frameRate as quil.core/current-frame-rate
    height as quil.core/height
    noCursor() as quil.core/no-cursor
    width as quil.core/width
Shape
    2D Primitives
        arc() as quil.core/arc
        ellipse() as quil.core/ellipse
        line() as quil.core/line
        point() as quil.core/point
        quad() as quil.core/quad
        rect() as quil.core/rect
        triangle() as quil.core/triangle
    Curves
        bezier() as quil.core/bezier
        bezierDetail() as quil.core/bezier-detail
        bezierPoint() as quil.core/bezier-point
        bezierTangent() as quil.core/bezier-tangent
        curve() as quil.core/curve
        curveDetail() as quil.core/curve-detail
        curvePoint() as quil.core/curve-point
        curveTangent() as quil.core/curve-tangent
        curveTightness() as quil.core/curve-tightness
    3D Primitives
        box() as quil.core/box
        sphere() as quil.core/sphere
        sphereDetail() as quil.core/sphere-detail
    Attributes
        ellipseMode() as quil.core/ellipse-mode
        noSmooth() as quil.core/no-smooth
        rectMode() as quil.core/rect-mode
        smooth() as quil.core/smooth
        strokeCap() as quil.core/stroke-cap
        strokeJoin() as quil.core/stroke-join
        strokeWeight() as quil.core/stroke-weight
    Vertex
        beginShape() as quil.core/begin-shape
        bezierVertex() as quil.core/bezier-vertex
        curveVertex() as quil.core/curve-vertex
        endShape() as quil.core/end-shape
        texture() as quil.core/texture
        textureMode() as quil.core/texture-mode
        vertex() as quil.core/vertex
Loading & Displaying
    loadShape() as quil.core/load-shape
    shape() as quil.core/shape
    shapeMode() as quil.core/shape-mode
Input
    Mouse
        mouseButton as quil.core/mouse-button
        mouseClicked() as :mouse-clicked parameter of defsketch
        mouseDragged() as :mouse-dragged parameter of defsketch
        mouseMoved() as :mouse-moved parameter of defsketch
        mouseOut() as :mouse-exited parameter of defsketch
        mouseOver() as :mouse-entered parameter of defsketch
        mousePressed() as :mouse-pressed parameter of defsketch
        mousePressed as quil.core/mouse-pressed?
        mouseReleased() as :mouse-released parameter of defsketch
        mouseX as quil.core/mouse-x
        mouseY as quil.core/mouse-y
        pmouseX as quil.core/pmouse-x
        pmouseY as quil.core/pmouse-y
    Keyboard
        key as quil.core/raw-key
        keyCode as quil.core/key-code
        keyPressed() as :key-pressed parameter of defsketch
        keyPressed as quil.core/key-pressed?
        keyReleased() as :key-released parameter of defsketch
        keyTyped() as :key-typed parameter of defsketch
    Time & Date
        day() as quil.core/day
        hour() as quil.core/hour
        millis() as quil.core/millis
        minute() as quil.core/minute
        month() as quil.core/month
        second() as quil.core/second
        year() as quil.core/year
Output
    Text Area
        print() as quil.core/prc-print
        println() as quil.core/prc-println
    Transform
        applyMatrix() as quil.core/apply-matrix
        popMatrix() as quil.core/pop-matrix
        printMatrix() as quil.core/print-matrix
        pushMatrix() as quil.core/push-matrix
        resetMatrix() as `quil.core/reset-matrix
        rotate() as quil.core/rotate
        rotateX() as quil.core/rotate-x
        rotateY() as quil.core/rotate-y
        rotateZ() as quil.core/rotate-z
        scale() as quil.core/scale
        translate() as quil.core/translate
Lights, Camera
    Lights
        ambientLight() as quil.core/ambient-light
        directionalLight() as quil.core/directional-light
        lightFalloff() as quil.core/light-falloff
        lightSpecular() as quil.core/light-specular
        lights() as quil.core/lights
        noLights() as quil.core/no-lights
        normal() as quil.core/normal
        pointLight() as quil.core/point-light
        spotLight() as quil.core/spot-light
    Camera
        beginCamera() as quil.core/begin-camera
        camera() as quil.core/camera
        endCamera() as quil.core/end-camera
        frustum() as quil.core/frustum
        ortho() as quil.core/ortho
        perspective() as quil.core/perspective
        printCamera() as quil.core/print-camera
        printProjection() as quil.core/print-projection
Coordinates
    modelX() as quil.core/model-x
    modelY() as quil.core/model-y
    modelZ() as quil.core/model-z
    screenX() as quil.core/screen-x
    screenY() as quil.core/screen-y
    screenZ() as quil.core/screen-z
Material Properties
    ambient() as quil.core/ambient
    emissive() as quil.core/emissive
    shininess() as quil.core/shininess
    specular() as quil.core/specular
Color
    Setting
        background() as quil.core/background
        colorMode() as quil.core/color-mode
        fill() as quil.core/fill
        noFill() as quil.core/no-fill
        noStroke() as quil.core/no-stroke
        stroke() as quil.core/stroke
    Creating & Reading
        alpha() as quil.core/alpha
        blendColor() as quil.core/blend-color
        blue() as quil.core/blue
        brightness() as quil.core/brightness
        color() as quil.core/color
        green() as quil.core/green
        hue() as quil.core/hue
        lerpColor() as quil.core/lerp-color
        red() as quil.core/red
        saturation() as quil.core/saturation
Image
    PImage
        ```createImage()
    Loading & Displaying
        image() as quil.core/image
        imageMode() as quil.core/image-mode
        loadImage() as quil.core/load-image
        noTint() as quil.core/no-tint
        requestImage() as quil.core/request-image
        tint() as quil.core/tint
    Pixels
        blend() as quil.core/blend
        copy() as quil.core/copy
        filter() as quil.core/filter
        get() as quil.core/get-pixel
        loadPixels() as quil.core/load-pixels
        set() as quil.core/set-pixel
        updatePixels() as quil.core/update-pixels
    Rendering
        createGraphics() as quil.core/create-graphics
        hint() as quil.core/hint
Typography
    Loading & Displaying
        createFont() as quil.core/create-font
        loadFont() as quil.core/load-font
        text() as quil.core/text
        textFont() as quil.core/text-font
    Attributes
        textAlign() as quil.core/text-align
        textLeading() as quil.core/text-leading
        textMode() as quil.core/text-mode
        textSize() as quil.core/text-size
        textWidth() as quil.core/text-width
    Metrics
        textAscent() as quil.core/text-ascent
        textDescent() as quil.core/text-descent