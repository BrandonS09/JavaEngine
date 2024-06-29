package me.brandon.test;

import me.brandon.core.*;
import me.brandon.core.entity.Entity;
import me.brandon.core.entity.Model;
import me.brandon.core.entity.Texture;
import me.brandon.core.lighting.DirectionalLight;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import static me.brandon.core.util.Constants.CAMERA_STEP;
import static me.brandon.core.util.Constants.MOUSE_SENSITIVITY;

public class TestGame implements ILogic {
    private final RenderManager renderer;
    private final ObjectLoader loader;
    private final WindowManager window;
    private Entity entity;
    private Camera camera;
    private DirectionalLight directionalLight;
    private float lightAngle;
    Vector3f cameraInc;

    public TestGame() {
        renderer = new RenderManager();
        window = Launcher.getWindow();
        loader = new ObjectLoader();
        camera = new Camera();
        cameraInc = new Vector3f(0, 0, 0);
        lightAngle = -90;
    }
    @Override
    public void init() throws Exception {
        renderer.init();

        Model model = loader.loadOBJModel("/models/stanford-bunny.obj");
        model.setTexture(new Texture(loader.loadTexture("C:\\Users\\Brandon Shen\\Documents\\Engine\\JavaEngine\\textures\\grassblock.png")), 1f);
        entity = new Entity(model, new Vector3f(0,0,-5), new Vector3f(0,0,0), 1);

        float lightIntensity = 0.0f;
        Vector3f lightPosition = new Vector3f(-1, -10, 0);
        Vector3f lightColour = new Vector3f(1,1,1);

        directionalLight = new DirectionalLight(lightColour, lightPosition, lightIntensity);
    }

    @Override
    public void input() {
        cameraInc.set(0,0,0);
        if(window.isKeyPressed(GLFW.GLFW_KEY_W))
            cameraInc.z = -1;
        if(window.isKeyPressed(GLFW.GLFW_KEY_S))
            cameraInc.z = 1;
        if(window.isKeyPressed(GLFW.GLFW_KEY_A))
            cameraInc.x = -1;
        if(window.isKeyPressed(GLFW.GLFW_KEY_D))
            cameraInc.x = 1;
        if(window.isKeyPressed(GLFW.GLFW_KEY_Q))
            cameraInc.y = -.1f;
        if(window.isKeyPressed(GLFW.GLFW_KEY_E))
            cameraInc.y = .1f;
    }

    @Override
    public void update(float interval, MouseInput mouseInput) {
        camera.movePosition(cameraInc.x * CAMERA_STEP, cameraInc.y * CAMERA_STEP, cameraInc.z * CAMERA_STEP);

        if(mouseInput.isRightButtonPress()){
            Vector2f rotVec = mouseInput.getDisplVec();
            camera.moveRotation(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY, 0);
        }

        //entity.incRotation(0.0f, 0.0025f, 0.0f);
        lightAngle += 0.05f;

        if (lightAngle > 90){
            directionalLight.setIntensity(0);
            if (lightAngle >= 360)
                lightAngle = -90;
        } else if (lightAngle <= -80 || lightAngle >= 80){
            float factor = 1 - (Math.abs(lightAngle) - 80) / 10.0f;
            directionalLight.setIntensity(factor);
            directionalLight.getColour().y = Math.max(factor, 0.9f);
            directionalLight.getColour().z = Math.max(factor, 0.5f);
        } else {
            directionalLight.setIntensity(1);
            directionalLight.getColour().x = 1;
            directionalLight.getColour().y = 1;
            directionalLight.getColour().z = 1;
        }
        double angleRad = Math.toRadians(lightAngle);
        directionalLight.getColour().x = (float) Math.sin(angleRad);
        directionalLight.getColour().y = (float) Math.cos(angleRad);
    }

    @Override
    public void render() {
        if (window.isResize()){
            GL11.glViewport(0,0, window.getWidth(), window.getHeight());
            window.setResize(true);
        }
        renderer.render(entity, camera, directionalLight);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        loader.cleanup();
        window.cleanup();
        GLFW.glfwTerminate();
    }
}
