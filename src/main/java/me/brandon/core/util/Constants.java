package me.brandon.core.util;

import org.joml.Vector3f;
import org.joml.Vector4f;

public class Constants {
    public static final String TITLE = "JAVA ENGINE";
    public static final float Z_NEAR = 0.01f;
    public static final float Z_FAR = 1000f;

    public static final float MOUSE_SENSITIVITY = 0.2f;
    public static final float CAMERA_STEP = 0.05f;

    public static final Vector4f DEFAULT_COLOR = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
    public static final Vector3f AMBIENT_LIGHT = new Vector3f(0.3f, 0.3f, 0.3f);
}
